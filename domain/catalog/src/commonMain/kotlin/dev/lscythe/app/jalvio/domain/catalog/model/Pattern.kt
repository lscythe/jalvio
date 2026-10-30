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

import kotlin.time.Duration
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class Pattern(
    val id: PatternId,
    val routeId: RouteId,
    val direction: Direction,
    val headsign: String?,
    val name: String?,
    val serviceId: ServiceId,
    val stops: List<PatternStop>,
    val headways: List<Headway>,
)

enum class Direction {
    Outbound,
    Inbound,
}

data class PatternStop(val stopId: StopId, val fromStart: Duration)

data class Headway(val start: Duration, val end: Duration, val every: Duration)

data class Service(
    val id: ServiceId,
    val days: Set<DayOfWeek>,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

data class Shape(val patternId: PatternId, val encodedPolyline: String)
