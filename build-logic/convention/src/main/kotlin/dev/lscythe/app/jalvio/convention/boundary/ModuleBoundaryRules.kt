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
package dev.lscythe.app.jalvio.convention.boundary

import java.io.Serializable

data class ModuleDependency(val from: String, val to: String, val configuration: String) :
    Serializable

private val featureImplCoreModules =
    setOf(
        "ui",
        "designsystem",
        "navigation",
        "resources",
        "common",
        "permissions",
        "location",
        "map",
        "analytics",
    )
private val dataCoreModules =
    setOf("network", "database", "datastore", "location", "sync", "common")

fun boundaryViolations(dependencies: List<ModuleDependency>): List<String> =
    dependencies
        .filterNot { "Test" in it.configuration || it.from == it.to }
        .mapNotNull { dependency ->
            ruleBroken(dependency)?.let {
                "${dependency.from} -> ${dependency.to} (${dependency.configuration}): $it"
            }
        }

private fun ruleBroken(dependency: ModuleDependency): String? {
    val from = layerOrNull(dependency.from) ?: return "unrecognised module ${dependency.from}"
    val to = layerOrNull(dependency.to) ?: return "unrecognised module ${dependency.to}"
    val target = secondSegment(dependency.to)
    return when (from) {
        ModuleLayer.App -> null
        ModuleLayer.FeatureImpl ->
            "feature impl may depend only on feature api, domain and selected core modules"
                .takeUnless {
                    to == ModuleLayer.FeatureApi ||
                        to == ModuleLayer.Domain ||
                        (to == ModuleLayer.Core && target in featureImplCoreModules)
                }
        ModuleLayer.FeatureApi ->
            "feature api may depend only on core:navigation"
                .takeUnless {
                    to == ModuleLayer.Core && target == "navigation"
                }
        ModuleLayer.Domain ->
            "domain may depend only on domain and core:common"
                .takeUnless {
                    to == ModuleLayer.Domain || (to == ModuleLayer.Core && target == "common")
                }
        ModuleLayer.Data ->
            "data may depend only on its own domain, api and selected core modules"
                .takeUnless {
                    (to == ModuleLayer.Domain && target == secondSegment(dependency.from)) ||
                        to == ModuleLayer.Api ||
                        (to == ModuleLayer.Core && target in dataCoreModules)
                }
        ModuleLayer.Api ->
            "api may depend only on core:network"
                .takeUnless {
                    to == ModuleLayer.Core && target == "network"
                }
        ModuleLayer.Core -> "core may depend only on core".takeUnless { to == ModuleLayer.Core }
    }
}
