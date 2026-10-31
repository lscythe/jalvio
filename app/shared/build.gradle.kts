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
    alias(libs.plugins.jalvio.metro)
    alias(libs.plugins.jalvio.endpoints)
}

kotlin {
    android { namespace = "dev.lscythe.app.jalvio.shared" }

    sourceSets {
        commonMain.dependencies {
            api(project(":api:catalog"))
            api(project(":core:analytics"))
            api(project(":core:common"))
            api(project(":core:database"))
            api(project(":core:datastore"))
            api(project(":core:location"))
            api(project(":core:map"))
            api(project(":core:monitoring"))
            api(project(":core:network"))
            api(project(":core:sync"))
            api(project(":data:catalog"))
            api(project(":domain:catalog"))
            api(libs.compose.multiplatform.runtime)
            implementation(project(":core:designsystem"))
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.material3)
        }
    }
}
