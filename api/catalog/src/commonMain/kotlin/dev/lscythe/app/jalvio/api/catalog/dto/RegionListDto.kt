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

import kotlinx.serialization.Serializable

@Serializable data class RegionListDto(val regions: List<RegionDto>)

@Serializable
data class RegionDto(
    val id: String,
    val name: String,
    val timeZone: String,
    val bounds: BoundsDto,
    val live: Boolean,
    val catalog: CatalogLinkDto,
    val shapes: ShapesLinkDto,
    val map: MapLinkDto? = null,
)

@Serializable data class BoundsDto(val southWest: PositionDto, val northEast: PositionDto)

@Serializable data class PositionDto(val lat: Double, val lng: Double)

@Serializable
data class CatalogLinkDto(val url: String, val version: String, val updatedAt: String? = null)

@Serializable data class ShapesLinkDto(val url: String)

@Serializable data class MapLinkDto(val url: String, val bytes: Long, val updated: String)
