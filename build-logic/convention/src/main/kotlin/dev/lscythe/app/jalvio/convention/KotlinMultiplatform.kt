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

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

internal fun Project.configureMultiplatformLibrary() {
    extensions.configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()

        extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
            compileSdk { version = release(COMPILE_SDK) { minorApiLevel = COMPILE_SDK_MINOR } }
            minSdk = MIN_SDK
            compilerOptions { jvmTarget = JVM_TARGET }

            withHostTestBuilder {}.configure { isIncludeAndroidResources = true }
        }
        iosArm64()
        iosSimulatorArm64()

        compilerOptions { allWarningsAsErrors = warningsAsErrors() }

        sourceSets.commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotest.framework.engine)
            implementation(libs.kotest.assertions.core)
            implementation(libs.kotest.property)
        }
        sourceSets.getByName("androidHostTest").dependencies {
            implementation(libs.kotest.runner.junit5)
        }
    }

    tasks.withType<Test>().configureEach { useJUnitPlatform() }

    configureIosTestResources()
}

private fun Project.configureIosTestResources() {
    extensions.configure<KotlinMultiplatformExtension> {
        targets.withType<KotlinNativeTarget>().configureEach {
            val targetName = name
            val testExecutable = binaries.getTest(NativeBuildType.DEBUG)
            val copyTestResources =
                tasks.register<Copy>(
                    "copy${targetName.replaceFirstChar(Char::uppercase)}TestResources"
                ) {
                    from(layout.projectDirectory.dir("src/commonTest/resources"))
                    into(testExecutable.outputDirectory)
                    dependsOn(testExecutable.linkTaskProvider)
                }
            tasks.withType<KotlinNativeTest>().configureEach {
                if (name == "${targetName}Test") dependsOn(copyTestResources)
            }
        }
    }
}

private fun Project.warningsAsErrors() =
    providers.gradleProperty("warningsAsErrors").map(String::toBoolean).orElse(false)
