# Code style

kempt formats every file on commit: ktfmt with the kotlinlang style, Apache license headers and whitespace. Run `mise run format` to format by hand. The rest of this guide covers what a formatter cannot.

## Naming and comments

- Names say what a thing is or does; small functions and expressive types replace comments.
- Comment only a non-obvious *why* that the code cannot express. Do not restate a name in a doc comment and do not explain configuration in comments. Rationale belongs in the commit message or the pull request.
- Import types; do not write fully qualified names inline.
- Never write the em dash character in code, comments or docs.
- Code, tests and commit messages describe what changed, not the plan it came from.

## Feature files

Features use MVI. `feature/<name>/impl` contains:

| File | Role |
|---|---|
| `<Name>Contract.kt` | `UiState` (`@Immutable`), `Intent` (sealed interface), `Effect` (sealed interface) |
| `<Name>ViewModel.kt` | `state: StateFlow<UiState>`, `effects: Flow<Effect>`, `onIntent(intent)` |
| `<Name>Route.kt` | Collects state and effects with lifecycle and gets the view model via `metroViewModel()` |
| `<Name>Screen.kt` | Stateless; takes `state` and `onIntent`; previewed and screenshot-tested |
| `navigation/<Name>EntryProvider.kt` | `EntryProviderScope<NavKey>.<name>Entry(...)` |
| `content/` | Sections of the screen |

- Navigation callbacks are lambdas passed in from `app/shared`; a feature never knows who hosts it.
- Previews live in `androidMain`.
- View models expose state with `stateIn(viewModelScope, WhileSubscribed(5_000), initial)`.

## Domain and use cases

- Business rules live in `domain/<concept>` as plain Kotlin and are unit-tested.
- A use case exists only when it combines repositories, enforces a business rule, or holds a transformation two view models share. Otherwise inject the repository interface. A use case that only forwards one repository call hides nothing and is not added.
- Time comes from an injected `Clock`, never `Clock.System` directly, so tests can control it.
- Coroutine dispatchers come from injection (`@Dispatcher(JalvioDispatcher.IO)`), and long-lived work runs in the injected `@ApplicationScope` scope.

## Error handling

Expected failures are values, modelled with arrow-core (`Either`, `Raise`, `ensure`) and arrow-resilience `Schedule`:

- Each concept has a sealed error type in its domain module. No shared catch-all error and no `Throwable` in signatures.
- Room reads return `Flow<T>`. One-shot operations return `Either<E, T>`. Polling flows emit `Either<E, T>` per tick.
- `data/*` and `api/*` catch exceptions at their edge with Arrow's `catch`, which rethrows `CancellationException`. Exceptions never leave a repository.
- `api/*` returns `ApiError` from `core:network` (`Unreachable`, `Malformed`, or `Http` with the status and the decoded RFC 9457 `Problem`), and `Fetched` for ETag-cached resources. `data/*` maps `ApiError` to the concept's error, because what a status means depends on the endpoint.
- View models fold results into `UiState` or `Effect`. Arrow types never reach Compose code.
- Unexpected failures go to `Monitor.recordError`. Sync work reports them by returning `SyncOutcome.Failed` from its `Syncable`, and `core:sync` records them.

## Dependency injection

Metro, configured by `jalvio.metro` with `generateContributionProviders = true`:

- Implementations are `internal`, annotated `@Inject` and `@ContributesBinding(AppScope::class)`, and bound to a public interface.
- A replacement for a default implementation declares `replaces = [Default::class]`.
- Provider groups are `@ContributesTo(AppScope::class) @BindingContainer object`s with `@Provides` functions.
- Do not inject a contributed `internal` implementation type directly; inject its interface, or construct the implementation yourself when wrapping it.
- Graph accessors go on `JalvioGraph` in `app/shared`; host graphs add only platform inputs.

## Build files

- Module build files declare plugins, namespace and dependencies only; shared configuration belongs in a convention plugin.
- `implementation` by default; `api` only for types in public signatures.
- Dependencies come from `gradle/libs.versions.toml`; no inline coordinates or versions.
