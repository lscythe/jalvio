# Testing

Tests are written first: a failing test, then the code that makes it pass, then the refactor.

## What to test

Test the code Jalvio writes: business rules, mappers, serializers and Room type converters, validation, error mapping, view model state, SQL with real logic, and the DI wiring each distribution gets. Do not re-test library behaviour such as a DAO upsert, DataStore persistence or Ktor's JSON setup. When a component is mostly a library call, extract the decision Jalvio makes into a function and test that.

## Test types

| Kind | Location | Style | Runs with |
|---|---|---|---|
| Shared logic | `src/commonTest` | Kotest `BehaviorSpec` | `testAndroidHostTest` (JVM) and `iosSimulatorArm64Test` |
| Android framework code | `src/androidHostTest` | JUnit 4 `@RunWith(RobolectricTestRunner::class)` with Kotest assertions | `testAndroidHostTest` |
| Screenshots | `src/androidHostTest` | JUnit 4 + Robolectric + Roborazzi via `captureScreenshot` | `recordRoborazziAndroidHostTest`, `verifyRoborazziAndroidHostTest` |
| iOS-only code | `src/iosTest` | Kotest `BehaviorSpec` | `iosSimulatorArm64Test` |
| Android graph per distribution | `app/android/src/test<Flavor>` | JUnit 4 + Robolectric | `:app:android:test<Flavor>DebugUnitTest` |
| Convention plugins | `build-logic/convention/src/test` | Kotest on JUnit 5, Gradle TestKit for functional tests | `./gradlew -p build-logic :convention:test` |
| Tooling scripts | `.github/scripts/test_*.py` | `unittest` | `python3 -m unittest discover -s .github/scripts -p 'test_*.py'` |

Kotest specs do not run under the Robolectric runner, so anything that needs Robolectric is a JUnit 4 class on the vintage engine, which the `jalvio.robolectric` plugin adds. The plugin also passes `--add-exports=java.base/jdk.internal.access=ALL-UNNAMED`, which Robolectric needs on JDK 25.

## Tools

- `kotlinx-coroutines-test`, and `MainDispatcherExtension` from `core:testing` to replace `Dispatchers.Main` in a spec.
- `TestClock` from `core:testing` for staleness, polling and schedule estimates; call `advanceBy` instead of waiting.
- Turbine for flows.
- Fakes over mocks in common code, kept in `core:testing` or the module's test source set. MockK only in Android-only tests.
- `readResource(path)` from `core:testing` reads files from `src/commonTest/resources` on both platforms; the library convention copies them next to the iOS test binary.
- Ktor `MockEngine` with fixtures copied from the jalvio-service contract, including `304`, problem+json errors and WebSocket frames.

## Screenshots

`captureScreenshot(name, darkTheme) { ... }` from `core:testing-screenshot` wraps content in the default `MaterialTheme` and a `Surface` and writes `src/androidHostTest/screenshots/<name>.png`. Screenshot tests run under Robolectric with `@GraphicsMode(GraphicsMode.Mode.NATIVE)`. Every `Screen` gets light and dark screenshots for each of its states.

Content with an infinite animation, such as a blinking dot or a progress indicator, never lets Compose go idle, so `captureScreenshot { ... }` keeps advancing frames and the test stalls. Render it with `createComposeRule()`, set `mainClock.autoAdvance = false`, and capture with `composeRule.onRoot().captureScreenshot(name)`; `mainClock.advanceTimeBy` then captures later frames.

Every component and every screen also goes through the phone matrix: `Phone.Small` (320 x 640 dp), `Medium` (360 x 800) and `Large` (430 x 932) at font scale 1, plus `SmallLargeFont` (320 x 640 dp at font scale 2). Run the test class with `ParameterizedRobolectricTestRunner` and `@Config(qualifiers = LargestPhoneWindow)`, take a `Phone` in the constructor, return `Phone.parameters()` from the `@Parameters` function, and capture with `composeRule.capturePhoneScreenshot(name, phone) { ... }` (or `captureOnPhone` with your own theme). The helper lays the content out in a box the size of the phone, applies the font scale through `LocalDensity`, pauses the clock so animations stay on their first frame, and writes `<name>_small`, `_medium`, `_large` and `_small_font2x`. Use it instead of `captureRoboImage` with Roborazzi's size options, which captured blank images at random. The small phone at font scale 2 is the case that breaks layouts, so check it first. Measure layout in tests with `@GraphicsMode(GraphicsMode.Mode.NATIVE)`: the legacy mode measures text with fake metrics.

Dialogs and modal sheets open their own window, which `captureRoboImage` does not see. Show them from `createComposeRule()` and capture the whole screen with `captureScreenScreenshot(name)`.

```sh
mise run screenshots                            # record baselines
./gradlew verifyRoborazziAndroidHostTest        # compare against them
```

Commit recorded baselines with the change that caused them.

## Commands

```sh
mise run test       # tooling, build-logic, Android host and app unit tests
mise run test-ios   # iOS simulator tests (needs Xcode)
./gradlew koverXmlReport
```

The `pre-push` hook runs `mise run test`. CI runs both test tasks and uploads Kover reports to Codecov.

## Coverage

Coverage counts only the code these rules say to test. `build-logic/convention/src/main/kotlin/dev/lscythe/app/jalvio/convention/Kover.kt` excludes the rest from every module: generated code (resources, Room implementations, Metro factories, the `JalvioIcons` pack), DI wiring and no-op implementations, the Room schema, app entry points, `core:testing` helpers, and platform adapters that only hand calls to an SDK. A new adapter goes on that list once its decisions are extracted into tested functions.
