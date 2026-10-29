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

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val ROBOLECTRIC_JDK_ACCESS = "--add-exports=java.base/jdk.internal.access=ALL-UNNAMED"
private const val ROBOLECTRIC_NATIVE_ACCESS = "--enable-native-access=ALL-UNNAMED"

class RobolectricConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets.getByName("androidHostTest").dependencies {
                        implementation(libs.robolectric)
                        implementation(libs.junit.vintage.engine)
                    }
                }
            }
            pluginManager.withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> {
                    testOptions.unitTests.isIncludeAndroidResources = true
                }
                dependencies {
                    "testImplementation"(libs.robolectric)
                    "testImplementation"(libs.junit.vintage.engine)
                    "testRuntimeOnly"(libs.junit.platform.launcher)
                }
                tasks.withType<Test>().configureEach { useJUnitPlatform() }
            }
            tasks.withType<Test>().configureEach {
                jvmArgs(ROBOLECTRIC_JDK_ACCESS, ROBOLECTRIC_NATIVE_ACCESS)
            }
        }
}
