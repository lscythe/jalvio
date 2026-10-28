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

import dev.lscythe.app.jalvio.convention.boundary.CheckModuleBoundariesTask
import dev.lscythe.app.jalvio.convention.boundary.ModuleDependency
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

class ModuleBoundariesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("lifecycle-base")

        val projectPath = target.path
        val checkModuleBoundaries =
            target.tasks.register("checkModuleBoundaries", CheckModuleBoundariesTask::class.java) {
                group = "verification"
                description = "Fails when this module depends on a module its layer forbids."
                dependencyEdges.set(
                    target.provider {
                        target.configurations
                            .flatMap { configuration ->
                                configuration.dependencies
                                    .withType(ProjectDependency::class.java)
                                    .map {
                                        ModuleDependency(projectPath, it.path, configuration.name)
                                    }
                            }
                            .map(CheckModuleBoundariesTask::encodeEdge)
                            .distinct()
                            .sorted()
                    }
                )
                report.set(target.layout.buildDirectory.file("module-boundaries/report.txt"))
            }

        target.tasks.named("check") { dependsOn(checkModuleBoundaries) }
    }
}
