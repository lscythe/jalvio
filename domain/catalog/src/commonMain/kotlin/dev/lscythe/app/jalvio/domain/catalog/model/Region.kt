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
package dev.lscythe.app.jalvio.domain.catalog.model

import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import kotlinx.datetime.TimeZone

data class Region(
    val id: RegionId,
    val name: String,
    val timeZone: TimeZone,
    val bounds: GeoBounds,
    val hasLiveBuses: Boolean,
)

data class GeoBounds(val southWest: GeoPoint, val northEast: GeoPoint) {
    operator fun contains(point: GeoPoint): Boolean =
        point.lat in southWest.lat..northEast.lat && point.lng in southWest.lng..northEast.lng
}
