# Jalvio

[![CI](https://github.com/lscythe/jalvio/actions/workflows/ci.yml/badge.svg?branch=develop)](https://github.com/lscythe/jalvio/actions/workflows/ci.yml)
[![CodeQL](https://github.com/lscythe/jalvio/actions/workflows/codeql.yml/badge.svg?branch=develop)](https://github.com/lscythe/jalvio/actions/workflows/codeql.yml)
[![codecov](https://codecov.io/gh/lscythe/jalvio/branch/develop/graph/badge.svg)](https://codecov.io/gh/lscythe/jalvio)
[![License](https://img.shields.io/github/license/lscythe/jalvio)](LICENSE)

Jalvio is an offline-first public transport app for Jakarta's BRT network, built with Kotlin and Compose Multiplatform for Android and iOS.

The static network (stops, routes, schedules and route shapes) is downloaded once and kept on the device, so search, route browsing and the map keep working without a connection. Live data such as arrivals is fetched only while a screen shows it.

> [!NOTE]
> Jalvio is in early development. The architectural foundation is being built and there is no public release yet.

## Distributions

| Distribution | Crash reporting | Analytics |
|---|---|---|
| `playstore` | Sentry | Firebase Analytics |
| `fossFull` | Sentry | None |
| `foss` | None | None |

The `checkFossClasspath` task fails if the `foss` release classpath contains Firebase, Google Play Services or Sentry.

## Tech stack

- Kotlin Multiplatform and Compose Multiplatform with Material 3 Expressive
- Navigation 3
- Metro for dependency injection
- Room 3 with the bundled SQLite driver, and DataStore
- Ktor, with WebSocket updates for live data
- Arrow for typed errors and retry schedules
- MapLibre Compose for vector maps
- Kotest, Turbine and Roborazzi for tests, Kover for coverage

## Project structure

```text
app/
├── android/    Android application host
├── ios/        iOS application host
└── shared/     Root composition, navigation and DI graph
feature/<name>/
├── api/        Navigation key
└── impl/       Screens and view models
domain/<concept>/   Pure Kotlin business rules
data/<concept>/     Repositories, storage and sync
api/<resource>/     Network clients for the Jalvio API
core/               Shared infrastructure
build-logic/        Gradle convention plugins
```

Modules are named after business concepts, not layers. The dependency rules between them (for example, features never depend on `data/*`) are enforced by `./gradlew checkModuleBoundaries`.

## Development

### Prerequisites

- [mise](https://mise.jdx.dev), which installs JDK 25, hk, kempt and Python
- Android SDK for Android builds
- Xcode with an iOS Simulator runtime for iOS builds

### Setup

```sh
mise install
hk install --mise
```

`hk install` registers the git hooks:

- `pre-commit` formats Kotlin with kempt and adds license headers
- `commit-msg` validates the commit header
- `pre-push` runs the module boundary check and `mise run test`

### Common tasks

```sh
mise run check      # boundaries, translations, foss classpath, dependency guard, lint
mise run test       # tooling, build-logic, Android host and app unit tests
mise run test-ios   # iOS simulator tests (needs Xcode)
mise run build      # debug APKs for every distribution
mise run build-ios  # iOS simulator app (needs Xcode)
mise run format     # format sources
```

### Endpoints

API and map style URLs are public configuration in [`config/endpoints.properties`](config/endpoints.properties). Override any value for a local build with a Gradle property, for example:

```sh
./gradlew :app:android:assembleDebug -Pjalvio.api.baseUrl.debug=http://10.0.2.2:8080/
```

## Contributing

See [CONTRIBUTING.md](.github/CONTRIBUTING.md) for the branch flow, commit format and pull request rules. Everyone taking part is expected to follow the [code of conduct](.github/CODE_OF_CONDUCT.md). Report vulnerabilities as described in the [security policy](.github/SECURITY.md).

## License

Jalvio is licensed under the [Apache License 2.0](LICENSE).
