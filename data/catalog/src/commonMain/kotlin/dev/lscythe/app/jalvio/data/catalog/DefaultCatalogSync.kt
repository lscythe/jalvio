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

import arrow.core.Either
import dev.lscythe.app.jalvio.core.network.monitor.NetworkMonitor
import dev.lscythe.app.jalvio.core.sync.SyncEngine
import dev.lscythe.app.jalvio.data.catalog.db.CatalogDao
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.lscythe.app.jalvio.domain.catalog.CatalogStatus
import dev.lscythe.app.jalvio.domain.catalog.CatalogSync
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(AppScope::class)
internal class DefaultCatalogSync(
    private val runner: CatalogSyncRunner,
    private val dao: CatalogDao,
    private val engine: SyncEngine,
    network: NetworkMonitor,
) : CatalogSync {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val status: Flow<CatalogStatus> =
        combine(
                dao.meta().flatMapLatest { meta ->
                    storedRegion(meta?.regionId).map { meta to it }
                },
                runner.run,
                network.isOnline,
            ) { (meta, region), run, online ->
                catalogStatus(meta, region, run, online)
            }
            .distinctUntilChanged()

    override suspend fun selectRegion(id: RegionId): Either<CatalogError, Unit> =
        runner.runFor(id).mapLeft { it.error }

    override fun retry() = engine.requestSync()

    private fun storedRegion(id: String?): Flow<Region?> =
        if (id == null) flowOf(null) else dao.region(id).map { it?.toRegion() }
}
