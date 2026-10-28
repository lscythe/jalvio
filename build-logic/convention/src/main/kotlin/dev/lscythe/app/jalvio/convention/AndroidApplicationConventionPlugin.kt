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
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply("com.dropbox.dependency-guard")
            pluginManager.apply(ModuleBoundariesConventionPlugin::class.java)

            val minifyWithR8 =
                providers.gradleProperty("minifyWithR8").map(String::toBoolean).orElse(true)

            extensions.configure<ApplicationExtension> {
                compileSdk { version = release(COMPILE_SDK) { minorApiLevel = COMPILE_SDK_MINOR } }
                defaultConfig {
                    minSdk = MIN_SDK
                    targetSdk = COMPILE_SDK
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_25
                    targetCompatibility = JavaVersion.VERSION_25
                }
                buildFeatures { compose = true }
                lint { checkDependencies = true }
                buildTypes {
                    debug { applicationIdSuffix = ".debug" }
                    release {
                        isMinifyEnabled = minifyWithR8.get()
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
                    }
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions { jvmTarget.set(JVM_TARGET) }
            }

            configureComposeCompiler()
            configureKover()
        }
    }
}
