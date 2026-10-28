/*
 * Copyright 2026 Lscythe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    alias(libs.plugins.jalvio.android.application)
    alias(libs.plugins.jalvio.android.application.flavors)
    alias(libs.plugins.jalvio.metro)
    alias(libs.plugins.jalvio.robolectric)
    alias(libs.plugins.gms)
}

val sentryDsn = providers.gradleProperty("sentryDsn").orElse("")

android {
    namespace = "dev.lscythe.app.jalvio"

    defaultConfig {
        applicationId = "dev.lscythe.app.jalvio"
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "SENTRY_DSN", "\"${sentryDsn.get()}\"")
    }

    buildFeatures { buildConfig = true }
}

dependencies {
    implementation(project(":app:shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)

    "playstoreImplementation"(project(":core:analytics-firebase"))
    "playstoreImplementation"(project(":core:monitoring-sentry"))
    "fossFullImplementation"(project(":core:monitoring-sentry"))

    testImplementation(libs.kotest.assertions.core)
}

dependencyGuard {
    configuration("playstoreReleaseRuntimeClasspath")
    configuration("fossFullReleaseRuntimeClasspath")
    configuration("fossReleaseRuntimeClasspath")
}
