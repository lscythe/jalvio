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
package dev.lscythe.app.jalvio.domain.catalog

import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId

sealed interface RegionChoice {
    data class Chosen(val region: Region) : RegionChoice

    data class Ask(val regions: List<Region>) : RegionChoice

    data object NoneServed : RegionChoice
}

fun chooseRegion(regions: List<Region>, storedId: RegionId?, location: GeoPoint?): RegionChoice {
    val stored = regions.firstOrNull { it.id == storedId }
    val containingRider = location?.let { point -> regions.firstOrNull { point in it.bounds } }
    return when {
        regions.isEmpty() -> RegionChoice.NoneServed
        stored != null -> RegionChoice.Chosen(stored)
        regions.size == 1 -> RegionChoice.Chosen(regions.single())
        containingRider != null -> RegionChoice.Chosen(containingRider)
        else -> RegionChoice.Ask(regions)
    }
}
