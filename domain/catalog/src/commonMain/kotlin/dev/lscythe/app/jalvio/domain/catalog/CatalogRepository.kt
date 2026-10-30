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

import dev.lscythe.app.jalvio.domain.catalog.model.Pattern
import dev.lscythe.app.jalvio.domain.catalog.model.PatternId
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.Route
import dev.lscythe.app.jalvio.domain.catalog.model.RouteId
import dev.lscythe.app.jalvio.domain.catalog.model.Shape
import dev.lscythe.app.jalvio.domain.catalog.model.Stop
import dev.lscythe.app.jalvio.domain.catalog.model.StopId
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    val region: Flow<Region?>

    fun routes(): Flow<List<Route>>

    fun route(id: RouteId): Flow<Route?>

    fun stop(id: StopId): Flow<Stop?>

    fun platforms(stationId: StopId): Flow<List<Stop>>

    fun patterns(routeId: RouteId): Flow<List<Pattern>>

    fun patternStops(patternId: PatternId): Flow<List<Stop>>

    fun shape(patternId: PatternId): Flow<Shape?>
}
