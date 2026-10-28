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

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import java.io.File
import java.nio.file.Files
import org.gradle.testkit.runner.GradleRunner

class ModuleBoundariesPluginFunctionalTest :
    BehaviorSpec({
        fun buildWith(dependencies: Map<String, String>): GradleRunner {
            val root = Files.createTempDirectory("module-boundaries").toFile()
            val modules = setOf(":feature:home:impl", ":data:catalog", ":domain:catalog")
            File(root, "settings.gradle.kts")
                .writeText(modules.joinToString("\n") { "include(\"$it\")" })
            modules.forEach { module ->
                val dir = File(root, module.removePrefix(":").replace(':', '/')).apply { mkdirs() }
                val dependency =
                    dependencies[module]?.let {
                        "dependencies { implementation(project(\"$it\")) }"
                    }
                File(dir, "build.gradle.kts")
                    .writeText(
                        """
                        plugins {
                            id("java-library")
                            id("jalvio.module.boundaries")
                        }
                        ${dependency.orEmpty()}
                        """
                            .trimIndent()
                    )
            }
            return GradleRunner.create()
                .withProjectDir(root)
                .withPluginClasspath()
                .withArguments("checkModuleBoundaries", "--stacktrace")
        }

        Given("a feature implementation depending on a data module") {
            Then("the check fails naming the edge and configuration") {
                val result =
                    buildWith(mapOf(":feature:home:impl" to ":data:catalog")).buildAndFail()
                result.output shouldContain ":feature:home:impl -> :data:catalog (implementation)"
            }
        }
        Given("a data module depending on its domain module") {
            Then("the check succeeds") {
                buildWith(mapOf(":data:catalog" to ":domain:catalog")).build()
            }
        }
    })
