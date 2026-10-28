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
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins { `kotlin-dsl` }

group = "dev.lscythe.app.jalvio.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

kotlin { compilerOptions { jvmTarget = JvmTarget.JVM_25 } }

dependencies {
    implementation(files(libs::class.java.superclass.protectionDomain.codeSource.location))
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.multiplatform.library.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.metro.gradlePlugin)

    testImplementation(gradleTestKit())
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.runner.junit5)
}

tasks {
    test { useJUnitPlatform() }

    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("multiplatformLibrary") {
            id = "jalvio.multiplatform.library"
            implementationClass =
                "dev.lscythe.app.jalvio.convention.KotlinMultiplatformLibraryConventionPlugin"
        }
        register("multiplatformLibraryCompose") {
            id = "jalvio.multiplatform.library.compose"
            implementationClass =
                "dev.lscythe.app.jalvio.convention.KotlinMultiplatformLibraryComposeConventionPlugin"
        }
        register("moduleBoundaries") {
            id = "jalvio.module.boundaries"
            implementationClass =
                "dev.lscythe.app.jalvio.convention.ModuleBoundariesConventionPlugin"
        }
        register("endpoints") {
            id = "jalvio.endpoints"
            implementationClass = "dev.lscythe.app.jalvio.convention.EndpointsConventionPlugin"
        }
        register("androidApplication") {
            id = "jalvio.android.application"
            implementationClass =
                "dev.lscythe.app.jalvio.convention.AndroidApplicationConventionPlugin"
        }
        register("androidApplicationFlavors") {
            id = "jalvio.android.application.flavors"
            implementationClass =
                "dev.lscythe.app.jalvio.convention.AndroidApplicationFlavorsConventionPlugin"
        }
        register("metro") {
            id = "jalvio.metro"
            implementationClass = "dev.lscythe.app.jalvio.convention.MetroConventionPlugin"
        }
        register("root") {
            id = "jalvio.root"
            implementationClass = "dev.lscythe.app.jalvio.convention.RootPlugin"
        }
    }
}
