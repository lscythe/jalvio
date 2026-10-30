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
package dev.lscythe.app.jalvio.api.catalog.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    val feedVersion: String,
    val facilitiesSyncedAt: String? = null,
    val routes: List<RouteDto>,
    val stops: List<StopDto>,
    val patterns: List<PatternDto>,
    val services: List<ServiceDto>,
    val fares: List<FareDto>,
)

@Serializable
data class RouteDto(
    val id: String,
    val code: String,
    val name: String,
    @SerialName("class") val category: String? = null,
    val color: String? = null,
    val textColor: String? = null,
    val fareId: String? = null,
)

@Serializable
data class StopDto(
    val id: String,
    val code: String? = null,
    val name: String,
    val kind: StopKindDto,
    val position: PositionDto,
    val parentId: String? = null,
    val childIds: List<String> = emptyList(),
    val platformCode: String? = null,
    val description: String? = null,
    val routeIds: List<String>,
    val transferIds: List<String> = emptyList(),
    val facilities: List<FacilityDto> = emptyList(),
)

@Serializable data class FacilityDto(val key: String, val title: String)

@Serializable
data class PatternDto(
    val id: String,
    val routeId: String,
    val direction: Int,
    val headsign: String? = null,
    val name: String? = null,
    val serviceId: String,
    val stops: List<PatternStopDto>,
    val headways: List<HeadwayDto>,
)

@Serializable data class PatternStopDto(val stopId: String, val fromStartSeconds: Int)

@Serializable
data class HeadwayDto(val startSeconds: Int, val endSeconds: Int, val everySeconds: Int)

@Serializable
data class ServiceDto(
    val id: String,
    val days: List<ServiceDayDto>,
    val startDate: String,
    val endDate: String,
)

@Serializable data class FareDto(val id: String, val price: Long, val currency: String)

@Serializable(with = StopKindSerializer::class)
enum class StopKindDto(val wire: String) {
    Station("station"),
    Platform("platform"),
    Roadside("roadside"),
    Unknown("unknown"),
}

@Serializable(with = ServiceDaySerializer::class)
enum class ServiceDayDto(val wire: String) {
    Mon("mon"),
    Tue("tue"),
    Wed("wed"),
    Thu("thu"),
    Fri("fri"),
    Sat("sat"),
    Sun("sun"),
    Unknown("unknown"),
}
