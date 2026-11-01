# Architecture

Jalvio is a Kotlin Multiplatform app for Android (`android`) and iOS (`iosArm64`, `iosSimulatorArm64`), with no desktop or web target. The static network lives on the device and every static screen is computed locally; only live data and place search need the network.

## Layers

```text
app/
├── android/          Android host: application, activity, flavors, per-variant graph
├── ios/              Static JalvioApp framework and the SwiftUI host
└── shared/           JalvioGraph, JalvioApp root composable, endpoint bindings
feature/<name>/api/   NavKey only
feature/<name>/impl/  Screens, view models, entry provider
domain/<concept>/     Models, repository interfaces, business rules (pure Kotlin)
data/<concept>/       Repository implementations, mapping, storage, refresh
api/<resource>/       Ktor clients, endpoints and DTOs for one jalvio-service resource group
core/                 Cross-cutting infrastructure
build-logic/          Convention plugins
```

Domain and data modules are named after business concepts (`catalog`, `live`, `places`, `ride`, `library`, `settings`), never after screens, so any feature can use any concept without depending on another feature. A module with a single consumer is still a top-level module.

## Dependency rules

| Module | May depend on |
|---|---|
| `feature/x/impl` | `feature/*/api`, `domain/*`, `core:{ui,designsystem,navigation,resources,common,permissions,location,map,analytics}` |
| `feature/x/api` | `core:navigation` |
| `domain/x` | `domain/*`, `core:common` |
| `data/x` | its own `domain/x`, `api/*`, `core:{network,database,datastore,location,sync,common}` |
| `api/x` | `core:network` |
| `core:*` | `core:*` |
| `app/*` | anything |

`checkModuleBoundaries` enforces the table. Each convention plugin applies `jalvio.module.boundaries`, which registers the task for that module. The task reads the module's project dependencies per configuration and fails with one line per violation:

```text
:feature:stop:impl -> :data:catalog (commonMainImplementation): feature impl may depend only on feature api, domain and selected core modules
```

Test configurations (any configuration whose name contains `Test`) and a module's references to itself are ignored, so `core:testing` and `core:testing-screenshot` are available to every test source set. The check runs in `mise run check`, the `pre-push` hook and CI. The rules live in `build-logic/convention/src/main/kotlin/dev/lscythe/app/jalvio/convention/boundary/ModuleBoundaryRules.kt`; a new kind of module is added there first.

## Dependency injection

Metro wires everything at compile time:

- Implementations are `internal` and contributed with `@ContributesBinding(AppScope::class)` to a public interface.
- Flavor-only modules replace defaults with `replaces = [...]`: `FirebaseAnalyticsHelper` replaces `NoOpAnalyticsHelper` and `SentryMonitor` replaces `KermitMonitor`.
- Consent works the same way per flavor. `app/shared` provides `ConsentOptions.None` from `ConsentOptionsBindings` and a `NoOpConsentApplier`; the Play Store and FOSS Full source sets replace both with their own `ConsentOptions` and a `ConsentApplier` that turns Firebase collection and Sentry on or off. FOSS keeps the defaults, so it offers nothing and the privacy step is skipped.
- Startup monitoring goes through `ConsentEnforcer`. Each host calls `consentEnforcer.start()` at launch; it follows the saved `PrivacyConsent` and hands every change to the `ConsentApplier`, so nothing is collected before the rider's choice is known.
- `app/shared` declares `JalvioGraph` (the accessors) and the hosts declare the real graphs, `AndroidAppGraph` and `IosAppGraph`. Metro only sees contributions on the classpath where a graph is compiled, so compiling it per Android variant lets each distribution resolve its own replacements. For the same reason `app/shared` carries every `api/*`, `data/*`, `feature/*/impl` and binding-providing `core:*` module as `api`: both hosts then aggregate the same bindings, and a new module is added there once.

## Sync

Data that the device keeps and refreshes is synced through `core:sync`. A data module says only how to sync, by contributing a `Syncable` into the Metro set; it never schedules work itself. `SyncEngine` runs every `Syncable` in parallel, lets overlapping requests share one run, and records `SyncOutcome.Failed` to `Monitor`.

When sync runs is the engine's job:

- **App launch** calls `SyncEngine.requestSync()`. On Android this enqueues unique one-time WorkManager work that needs a network connection, backs off on retry, and is expedited from Android 12 (earlier versions would need a foreground notification). On iOS it runs in the application scope and retries when `NetworkMonitor.isOnline` turns true, backing off from 30 seconds for at most five retries. On both, a new request replaces one still waiting to retry, so a retry button syncs right away.
- **Daily**, a WorkManager periodic job on Android and a `BGAppRefreshTask` on iOS run the engine.
- **Retry buttons** go through a concept's own interface, such as `CatalogSync.retry()`, because features cannot depend on `core:sync`.

## Design system

The design follows Material 3 Expressive, so features use its stock components as they are and import `material3` directly. `core:designsystem` never wraps them. It holds:

- The theme: `JalvioTheme(ThemeSettings)` builds the color scheme with MaterialKolor (or the phone's dynamic colors on Android 12 and later) and sets the Roboto Flex typography, shapes and motion.
- Tokens Material has no slot for: `JalvioTheme.colors` (crowd and info meaning colors), `RouteTone`, `JalvioShapes`, `JalvioElevation` and the `JalvioIcons` pack.
- Components Material lacks, and only those.

Screens target phones in portrait for now. They keep `JalvioLayout.screenMargin` (16 dp) at the sides and must not break or clip when the phone rotates, but landscape, foldable and tablet layouts wait for their designs, and with them window size classes.

Previews use `@JalvioPreviews`, which includes a 320 dp phone at font scale 2. `core:testing-screenshot` does not depend on the design system, so screenshot tests that need the Jalvio look wrap their content in `JalvioTheme(settings)` themselves. Code and copy use US spelling, such as "color".

## Data flow

Static network:

```text
UI ← ViewModel ← CatalogRepository: Flow<T> ← catalog.db (Room, data:catalog)
                                                ↑ one transaction swap
     CatalogSyncable ← api/catalog: GET /v1/regions (If-None-Match)
                                    GET /v1/regions/{region}/catalog
                                    GET /v1/regions/{region}/catalog/shapes
                                                ↑
     SyncEngine ← triggers: app launch, daily background task, retry
```

- The region list's `catalog.version` decides whether the catalog downloads; the catalog's `feedVersion` decides whether the shapes do. A failed or cancelled refresh leaves the stored catalog as it was. Switching region clears the old region's catalog first, and the switch finishes even if the screen that asked for it goes away.
- The region is chosen automatically: the stored choice while it is still listed, the only region, or the one containing the rider's last known location. Otherwise `CatalogSync.status` asks with `ChooseRegion`. The choice lives in DataStore, so it survives a `catalog.db` reset.
- `catalog.db` falls back destructively on a schema change; the next sync downloads again. User data stays in `jalvio.db`.

Live data:

```text
UI ← ViewModel.stateIn(WhileSubscribed(5_000)) ← LiveRepository: Flow<Either<LiveError, T>>
                                                   ↑ poll every 10 to 15 s while collected
                                                   ↑ or WebSocket /v1/live
```

- Live data is never stored. Polling stops when no screen collects the flow.
- When live data is missing or stale, screens fall back to scheduled estimates from the catalog.
- Place search is online only, and falls back to stop and route search when offline, rate limited or unavailable.
- Saved places, recent searches and ride history are Room tables; settings are DataStore preferences. All of it stays on the device.

Errors are values: each concept has a sealed error type, repositories return `Flow<T>` or `Either<E, T>`, and view models fold results into UI state (see [code-style.md](code-style.md#error-handling)).

## Configuration

Endpoints are committed public configuration in `config/endpoints.properties` and reach the code as the generated `JalvioEndpoints` object, bound into the graph as `ApiBaseUrl` and `MapStyleUrl`. They are not secrets: every request reveals them and F-Droid builds from the public source. Real secrets (signing keys, tokens) live in CI secrets or `local.properties`.

Android has one flavor dimension, `distribution`: `playstore` (Sentry and Firebase Analytics), `fossFull` (Sentry) and `foss` (neither). `checkFossClasspath` fails if the resolved `foss` release classpath contains Firebase, Google Play Services or Sentry, and dependency-guard baselines in `app/android/dependencies/` record all three release classpaths.
