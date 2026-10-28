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

import dev.lscythe.app.jalvio.convention.endpoints.GenerateEndpointsTask
import dev.lscythe.app.jalvio.convention.endpoints.OVERRIDE_PREFIX
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class EndpointsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val generate =
            target.tasks.register("generateJalvioEndpoints", GenerateEndpointsTask::class.java) {
                group = "build"
                description = "Generates JalvioEndpoints from config/endpoints.properties."
                propertiesFile.set(target.isolated.rootProject.projectDirectory.file(CONFIG_PATH))
                overrides.set(target.providers.gradlePropertiesPrefixedBy(OVERRIDE_PREFIX))
                packageName.set("dev.lscythe.app.jalvio.config")
                outputDirectory.set(target.layout.buildDirectory.dir("generated/jalvio/endpoints"))
            }

        target.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) {
                sourceSets
                    .getByName("commonMain")
                    .kotlin
                    .srcDir(generate.flatMap { it.outputDirectory })
            }
        }
    }

    private companion object {
        const val CONFIG_PATH = "config/endpoints.properties"
    }
}
