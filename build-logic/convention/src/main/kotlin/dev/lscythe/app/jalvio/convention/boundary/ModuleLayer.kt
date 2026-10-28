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

enum class ModuleLayer {
    App,
    FeatureApi,
    FeatureImpl,
    Domain,
    Data,
    Api,
    Core,
}

fun layerOf(path: String): ModuleLayer =
    requireNotNull(layerOrNull(path)) { "Module $path does not belong to any known layer" }

internal fun layerOrNull(path: String): ModuleLayer? {
    val segments = path.split(':').filter { it.isNotEmpty() }
    return when (segments.firstOrNull()) {
        "app" -> ModuleLayer.App
        "domain" -> ModuleLayer.Domain
        "data" -> ModuleLayer.Data
        "api" -> ModuleLayer.Api
        "core" -> ModuleLayer.Core
        "feature" ->
            when (segments.getOrNull(2)) {
                "api" -> ModuleLayer.FeatureApi
                "impl" -> ModuleLayer.FeatureImpl
                else -> null
            }
        else -> null
    }
}

internal fun secondSegment(path: String): String? =
    path.split(':').filter { it.isNotEmpty() }.getOrNull(1)
