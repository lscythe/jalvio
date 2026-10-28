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
import dev.lscythe.app.jalvio.convention.foss.CheckFossClasspathTask
import dev.lscythe.app.jalvio.convention.foss.FOSS_FORBIDDEN_GROUP_PREFIXES
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.result.ResolvedComponentResult
import org.gradle.api.artifacts.result.ResolvedDependencyResult
import org.gradle.kotlin.dsl.configure

class AndroidApplicationFlavorsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.withPlugin("com.android.application") { target.configureFlavors() }
        target.pluginManager.withPlugin("com.google.gms.google-services") {
            target.disableGoogleServicesOutsidePlaystore()
        }
    }

    private fun Project.disableGoogleServicesOutsidePlaystore() {
        val playstoreTaskPrefix =
            "process" + JalvioDistribution.Playstore.flavorName.replaceFirstChar(Char::uppercase)
        tasks
            .named { it.endsWith("GoogleServices") && !it.startsWith(playstoreTaskPrefix) }
            .configureEach { enabled = false }
    }

    private fun Project.configureFlavors() {
        extensions.configure<ApplicationExtension> {
            flavorDimensions += DISTRIBUTION_DIMENSION
            productFlavors {
                JalvioDistribution.entries.forEach { distribution ->
                    register(distribution.flavorName) {
                        dimension = DISTRIBUTION_DIMENSION
                    }
                }
            }
        }

        val fossClasspathCoordinates = provider {
            configurations.getByName("fossReleaseRuntimeClasspath")
        }
            .flatMap { classpath ->
                classpath.incoming.resolutionResult.rootComponent.map { root ->
                    root.moduleCoordinates()
                }
            }

        val checkFossClasspath =
            tasks.register("checkFossClasspath", CheckFossClasspathTask::class.java) {
                group = "verification"
                description =
                    "Fails when the foss release runtime classpath holds proprietary artifacts."
                resolvedCoordinates.set(fossClasspathCoordinates)
                forbiddenGroupPrefixes.set(FOSS_FORBIDDEN_GROUP_PREFIXES)
                report.set(layout.buildDirectory.file("foss-classpath/report.txt"))
            }

        tasks.named("check") { dependsOn(checkFossClasspath) }
    }
}

private fun ResolvedComponentResult.moduleCoordinates(): List<String> {
    val seen = LinkedHashSet<ResolvedComponentResult>()
    val pending = ArrayDeque(listOf(this))
    while (pending.isNotEmpty()) {
        val component = pending.removeFirst()
        if (seen.add(component)) {
            component.dependencies.filterIsInstance<ResolvedDependencyResult>().forEach {
                pending.addLast(it.selected)
            }
        }
    }
    return seen
        .mapNotNull { it.id as? ModuleComponentIdentifier }
        .map { "${it.group}:${it.module}:${it.version}" }
        .sorted()
}
