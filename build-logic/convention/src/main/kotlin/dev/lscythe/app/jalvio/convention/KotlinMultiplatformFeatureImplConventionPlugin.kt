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
package dev.lscythe.app.jalvio.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            pluginManager.apply("jalvio.multiplatform.library")
            pluginManager.apply("jalvio.multiplatform.library.compose")
            pluginManager.apply("jalvio.metro")
            pluginManager.apply("jalvio.robolectric")
            pluginManager.apply("io.github.takahirom.roborazzi")

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    implementation(project(":core:designsystem"))
                    implementation(project(":core:navigation"))
                    implementation(project(":core:ui"))
                    implementation(libs.compose.multiplatform.material3)
                    implementation(libs.androidx.lifecycle.runtimeCompose)
                    implementation(libs.androidx.lifecycle.viewModelCompose)
                    implementation(libs.metro.viewmodel)
                    implementation(libs.metro.viewmodel.compose)
                }
                sourceSets.commonTest.dependencies { implementation(project(":core:testing")) }
                sourceSets.getByName("androidHostTest").dependencies {
                    implementation(project(":core:testing-screenshot"))
                    implementation(libs.androidx.compose.ui.testJunit4)
                    implementation(libs.androidx.compose.ui.testManifest)
                }
            }
        }
}
