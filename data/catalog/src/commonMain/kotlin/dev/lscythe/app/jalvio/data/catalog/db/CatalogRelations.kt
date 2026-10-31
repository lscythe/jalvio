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
package dev.lscythe.app.jalvio.data.catalog.db

import androidx.room3.Embedded
import androidx.room3.Relation

internal data class StopWithRelations(
    @Embedded val stop: StopEntity,
    @Relation(parentColumns = ["id"], entityColumns = ["stopId"])
    val routeIds: List<StopRouteEntity>,
    @Relation(parentColumns = ["id"], entityColumns = ["stopId"])
    val transfers: List<StopTransferEntity>,
    @Relation(parentColumns = ["id"], entityColumns = ["stopId"])
    val facilities: List<StopFacilityEntity>,
)

internal data class PatternWithStops(
    @Embedded val pattern: PatternEntity,
    @Relation(parentColumns = ["id"], entityColumns = ["patternId"])
    val stops: List<PatternStopEntity>,
    @Relation(parentColumns = ["id"], entityColumns = ["patternId"])
    val headways: List<HeadwayEntity>,
)

internal data class CatalogRows(
    val routes: List<RouteEntity>,
    val stops: List<StopEntity>,
    val stopRoutes: List<StopRouteEntity>,
    val transfers: List<StopTransferEntity>,
    val facilities: List<StopFacilityEntity>,
    val patterns: List<PatternEntity>,
    val patternStops: List<PatternStopEntity>,
    val headways: List<HeadwayEntity>,
    val services: List<ServiceEntity>,
    val fares: List<FareEntity>,
)
