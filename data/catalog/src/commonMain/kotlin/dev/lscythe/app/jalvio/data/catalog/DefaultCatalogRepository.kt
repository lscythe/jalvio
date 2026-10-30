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

import dev.lscythe.app.jalvio.data.catalog.db.CatalogDao
import dev.lscythe.app.jalvio.domain.catalog.CatalogRepository
import dev.lscythe.app.jalvio.domain.catalog.model.Pattern
import dev.lscythe.app.jalvio.domain.catalog.model.PatternId
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.Route
import dev.lscythe.app.jalvio.domain.catalog.model.RouteId
import dev.lscythe.app.jalvio.domain.catalog.model.Shape
import dev.lscythe.app.jalvio.domain.catalog.model.Stop
import dev.lscythe.app.jalvio.domain.catalog.model.StopId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(AppScope::class)
internal class DefaultCatalogRepository(
    private val dao: CatalogDao,
    selection: RegionSelectionStore,
) : CatalogRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val region: Flow<Region?> =
        selection.selected.flatMapLatest { id ->
            if (id == null) flowOf(null) else dao.region(id.value).map { it?.toRegion() }
        }

    override fun routes(): Flow<List<Route>> =
        dao.routes().map { rows -> rows.map { it.toRoute() } }

    override fun route(id: RouteId): Flow<Route?> = dao.route(id.value).map { it?.toRoute() }

    override fun stop(id: StopId): Flow<Stop?> = dao.stop(id.value).map { it?.toStop() }

    override fun platforms(stationId: StopId): Flow<List<Stop>> =
        dao.platforms(stationId.value).map { rows -> rows.map { it.toStop() } }

    override fun patterns(routeId: RouteId): Flow<List<Pattern>> =
        dao.patterns(routeId.value).map { rows -> rows.map { it.toPattern() } }

    override fun patternStops(patternId: PatternId): Flow<List<Stop>> =
        dao.patternStops(patternId.value).map { rows -> rows.map { it.toStop() } }

    override fun shape(patternId: PatternId): Flow<Shape?> =
        dao.shape(patternId.value).map { it?.toShape() }
}
