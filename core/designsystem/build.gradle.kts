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
    alias(libs.plugins.jalvio.multiplatform.library)
    alias(libs.plugins.jalvio.multiplatform.library.compose)
    alias(libs.plugins.jalvio.robolectric)
    alias(libs.plugins.roborazzi)
}

kotlin {
    android {
        namespace = "dev.lscythe.app.jalvio.core.designsystem"
        androidResources.enable = true
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.multiplatform.runtime)
            api(libs.compose.multiplatform.material3)
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.resources)
            implementation(libs.materialKolor.material3)
            implementation(project(":core:resources"))
        }
        androidMain.dependencies { implementation(libs.compose.multiplatform.ui.tooling.preview) }
        commonTest.dependencies { implementation(project(":core:testing")) }
        getByName("androidHostTest").dependencies {
            implementation(project(":core:testing-screenshot"))
            implementation(libs.androidx.compose.ui.testJunit4)
            implementation(libs.androidx.compose.ui.testManifest)
        }
    }
}

compose.resources {
    packageOfResClass = "dev.lscythe.app.jalvio.core.designsystem.generated.resources"
}
