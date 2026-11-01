# Adding a module

Every module is a Kotlin Multiplatform module with an Android target and the two iOS targets, set up by a convention plugin from `build-logic`. A module's build file declares its plugins, its namespace and its dependencies, and nothing else.

## Common steps

1. Create the directory and `build.gradle.kts`.
2. Add the module to `settings.gradle.kts`, in alphabetical order inside the `include(...)` call for its top-level directory. There is one `include` per top-level directory:

   ```kotlin
   include(
       ":core:analytics",
       ":core:common",
       // ...
   )
   ```

3. Set the Android namespace to `dev.lscythe.app.jalvio.` followed by the module path with colons and dashes turned into dots, and use the same root package for sources. `:core:monitoring-sentry` becomes `dev.lscythe.app.jalvio.core.monitoring.sentry`.
4. Use `implementation` by default. Use `api` only for a dependency whose types appear in the module's public signatures. Depend on `core:common` only where the module uses it.
5. Run `./gradlew checkModuleBoundaries` to confirm the new dependencies follow the [rules](architecture.md#dependency-rules).

kempt adds the license header to new files on commit; do not write it by hand.

## Feature

Two modules: `feature/<name>/api` and `feature/<name>/impl`. Other features depend only on the small `api` module, never on another feature's `impl`.

`feature/<name>/api/build.gradle.kts`:

```kotlin
plugins { alias(libs.plugins.jalvio.multiplatform.feature.api) }

kotlin { android { namespace = "dev.lscythe.app.jalvio.feature.<name>.api" } }
```

The plugin adds Kotlin serialization and `core:navigation`. The module holds one `@Serializable` `NavKey` and its arguments.

`feature/<name>/impl/build.gradle.kts`:

```kotlin
plugins { alias(libs.plugins.jalvio.multiplatform.feature.impl) }

kotlin {
    android { namespace = "dev.lscythe.app.jalvio.feature.<name>.impl" }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":feature:<name>:api"))
            implementation(project(":domain:<concept>"))
            implementation(project(":core:resources"))
        }
    }
}
```

The plugin adds Compose, Metro, `core:designsystem`, `core:navigation`, `core:ui`, Material 3, lifecycle and Metro view model support, `core:testing` to `commonTest`, and Robolectric, Roborazzi and `core:testing-screenshot` to `androidHostTest`.

- Files follow the MVI layout in [code-style.md](code-style.md#feature-files).
- Strings live in `core:resources`, which the plugin does not add; declare it as shown when the feature shows text.
- The view model is contributed with `@ViewModelKey` and `@ContributesIntoMap(AppScope::class)`. A view model that keeps state in a `SavedStateHandle` is `@AssistedInject` instead, with a nested `@AssistedFactory` that implements `ViewModelAssistedFactory`, carries `@ViewModelAssistedFactoryKey` and `@ContributesIntoMap(AppScope::class)`, and builds the handle with `extras.createSavedStateHandle()`. The route then calls `assistedMetroViewModel()`.
- Add the `impl` to `app/shared` as `api` and wire its entry provider and navigation callbacks there.
- Tests: view model behaviour in `commonTest`, screenshots of the `Screen` in `androidHostTest`.

## Domain

`domain/<concept>/build.gradle.kts`:

```kotlin
plugins { alias(libs.plugins.jalvio.multiplatform.library) }

kotlin { android { namespace = "dev.lscythe.app.jalvio.domain.<concept>" } }
```

- Pure Kotlin: models, repository interfaces, sealed `<Concept>Error`, business rules and use cases that hold logic (see [code-style.md](code-style.md#domain-and-use-cases)).
- May depend on other `domain/*` modules and `core:common`.
- No DI contributions; implementations live in `data/<concept>`.
- Tests: every business rule in `commonTest`.

## Data

`data/<concept>/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.jalvio.multiplatform.library)
    alias(libs.plugins.jalvio.metro)
}

kotlin {
    android { namespace = "dev.lscythe.app.jalvio.data.<concept>" }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain:<concept>"))
            implementation(project(":api:<resource>"))
            implementation(project(":core:database"))
        }
    }
}
```

- Repository implementations are `internal` and contributed with `@ContributesBinding(AppScope::class)` to the interface from `domain/<concept>`.
- Exceptions are caught at this edge with Arrow's `catch` and mapped to the concept's error.
- Tests: mapping, refresh logic and SQL with real logic in `commonTest`, against fakes from the test source set.

## Api

`api/<resource>/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.jalvio.multiplatform.library)
    alias(libs.plugins.jalvio.metro)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android { namespace = "dev.lscythe.app.jalvio.api.<resource>" }

    sourceSets { commonMain.dependencies { implementation(project(":core:network")) } }
}
```

- Ktor client functions and `@Serializable` DTOs for one jalvio-service resource group; contributions are `internal`.
- Tests: Ktor `MockEngine` with JSON fixtures copied from the jalvio-service contract into `src/commonTest/resources`, read with `readResource` from `core:testing`.

## Core

`core/<name>/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.jalvio.multiplatform.library)
    // alias(libs.plugins.jalvio.multiplatform.library.compose) for Compose code
    // alias(libs.plugins.jalvio.metro) for DI contributions
    // alias(libs.plugins.jalvio.robolectric) for Android framework tests
}

kotlin { android { namespace = "dev.lscythe.app.jalvio.core.<name>" } }
```

- Depends only on other `core:*` modules.
- Platform code goes in `androidMain` and `iosMain` behind a common interface.
- Provider groups are `@ContributesTo(AppScope::class) @BindingContainer object`s; per-platform providers live in `androidMain` and `iosMain`, as in `core:database` and `core:datastore`.
- A module only one distribution needs (like `core:monitoring-sentry`) is added to `app/android` with `<flavor>Implementation` and replaces the default binding.
