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

import androidx.room3.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("androidx.room3")

            extensions.configure<RoomExtension> { schemaDirectory("$projectDir/schemas") }

            extensions.configure<KotlinMultiplatformExtension> {
                compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") }
                sourceSets.commonMain.dependencies {
                    api(libs.androidx.room3.runtime)
                    implementation(libs.androidx.sqlite.bundled)
                }
            }

            dependencies {
                add("kspAndroid", libs.androidx.room3.compiler)
                add("kspIosArm64", libs.androidx.room3.compiler)
                add("kspIosSimulatorArm64", libs.androidx.room3.compiler)
            }
        }
}
