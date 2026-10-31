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
package dev.lscythe.app.jalvio.data.catalog

import dev.lscythe.app.jalvio.data.catalog.db.CatalogMetaEntity
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.lscythe.app.jalvio.domain.catalog.CatalogStatus
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import kotlin.time.Instant

internal sealed interface SyncRun {
    data object Idle : SyncRun

    data object Running : SyncRun

    data class Failed(val error: CatalogError) : SyncRun

    data class NeedsChoice(val regions: List<Region>) : SyncRun
}

internal fun catalogStatus(
    meta: CatalogMetaEntity?,
    region: Region?,
    run: SyncRun,
    online: Boolean,
): CatalogStatus {
    val updatedAt = meta?.takeIf { it.catalogEtag != null }?.updatedAt
    return when {
        run is SyncRun.NeedsChoice -> CatalogStatus.ChooseRegion(run.regions)
        region != null && updatedAt != null ->
            CatalogStatus.Ready(region, Instant.fromEpochMilliseconds(updatedAt))
        run is SyncRun.Failed -> CatalogStatus.Unavailable(run.error)
        run == SyncRun.Idle && !online -> CatalogStatus.Unavailable(CatalogError.Offline)
        else -> CatalogStatus.Downloading
    }
}
