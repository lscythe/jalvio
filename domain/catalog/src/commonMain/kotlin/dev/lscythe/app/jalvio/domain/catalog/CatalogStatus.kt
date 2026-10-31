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

import dev.lscythe.app.jalvio.domain.catalog.model.Region
import kotlin.time.Instant

sealed interface CatalogError {
    data object Offline : CatalogError

    data object NotReady : CatalogError

    data object RegionGone : CatalogError

    data object Failed : CatalogError
}

sealed interface CatalogStatus {
    data object Downloading : CatalogStatus

    data class ChooseRegion(val regions: List<Region>) : CatalogStatus

    data class Ready(val region: Region, val updatedAt: Instant) : CatalogStatus

    data class Unavailable(val error: CatalogError) : CatalogStatus
}
