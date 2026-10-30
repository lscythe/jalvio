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

import dev.lscythe.app.jalvio.api.catalog.RegionLinks
import dev.lscythe.app.jalvio.api.catalog.dto.CatalogDto
import dev.lscythe.app.jalvio.api.catalog.dto.FareDto
import dev.lscythe.app.jalvio.api.catalog.dto.PatternDto
import dev.lscythe.app.jalvio.api.catalog.dto.RouteDto
import dev.lscythe.app.jalvio.api.catalog.dto.ServiceDayDto
import dev.lscythe.app.jalvio.api.catalog.dto.ServiceDto
import dev.lscythe.app.jalvio.api.catalog.dto.ShapesDto
import dev.lscythe.app.jalvio.api.catalog.dto.StopDto
import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.lscythe.app.jalvio.data.catalog.db.CatalogRows
import dev.lscythe.app.jalvio.data.catalog.db.FareEntity
import dev.lscythe.app.jalvio.data.catalog.db.HeadwayEntity
import dev.lscythe.app.jalvio.data.catalog.db.PatternEntity
import dev.lscythe.app.jalvio.data.catalog.db.PatternStopEntity
import dev.lscythe.app.jalvio.data.catalog.db.PatternWithStops
import dev.lscythe.app.jalvio.data.catalog.db.RegionEntity
import dev.lscythe.app.jalvio.data.catalog.db.RouteEntity
import dev.lscythe.app.jalvio.data.catalog.db.ServiceEntity
import dev.lscythe.app.jalvio.data.catalog.db.ShapeEntity
import dev.lscythe.app.jalvio.data.catalog.db.StopEntity
import dev.lscythe.app.jalvio.data.catalog.db.StopFacilityEntity
import dev.lscythe.app.jalvio.data.catalog.db.StopRouteEntity
import dev.lscythe.app.jalvio.data.catalog.db.StopTransferEntity
import dev.lscythe.app.jalvio.data.catalog.db.StopWithRelations
import dev.lscythe.app.jalvio.domain.catalog.model.Direction
import dev.lscythe.app.jalvio.domain.catalog.model.Facility
import dev.lscythe.app.jalvio.domain.catalog.model.FareId
import dev.lscythe.app.jalvio.domain.catalog.model.GeoBounds
import dev.lscythe.app.jalvio.domain.catalog.model.Headway
import dev.lscythe.app.jalvio.domain.catalog.model.Pattern
import dev.lscythe.app.jalvio.domain.catalog.model.PatternId
import dev.lscythe.app.jalvio.domain.catalog.model.PatternStop
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import dev.lscythe.app.jalvio.domain.catalog.model.RgbColor
import dev.lscythe.app.jalvio.domain.catalog.model.Route
import dev.lscythe.app.jalvio.domain.catalog.model.RouteId
import dev.lscythe.app.jalvio.domain.catalog.model.ServiceId
import dev.lscythe.app.jalvio.domain.catalog.model.Shape
import dev.lscythe.app.jalvio.domain.catalog.model.Stop
import dev.lscythe.app.jalvio.domain.catalog.model.StopId
import dev.lscythe.app.jalvio.domain.catalog.model.StopKind
import kotlin.time.Duration.Companion.seconds
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone

internal fun CatalogDto.toRows(): CatalogRows =
    CatalogRows(
        routes = routes.map { it.toRow() },
        stops = stops.map { it.toRow() },
        stopRoutes =
            stops.flatMap { stop -> stop.routeIds.distinct().map { StopRouteEntity(stop.id, it) } },
        transfers =
            stops.flatMap { stop ->
                stop.transferIds.distinct().map { StopTransferEntity(stop.id, it) }
            },
        facilities =
            stops.flatMap { stop ->
                stop.facilities
                    .distinctBy { it.key }
                    .mapIndexed { order, facility ->
                        StopFacilityEntity(stop.id, facility.key, facility.title, order)
                    }
            },
        patterns = patterns.map { it.toRow() },
        patternStops =
            patterns.flatMap { pattern ->
                pattern.stops.mapIndexed { sequence, stop ->
                    PatternStopEntity(pattern.id, sequence, stop.stopId, stop.fromStartSeconds)
                }
            },
        headways =
            patterns.flatMap { pattern ->
                pattern.headways
                    .distinctBy { it.startSeconds }
                    .map {
                        HeadwayEntity(pattern.id, it.startSeconds, it.endSeconds, it.everySeconds)
                    }
            },
        services = services.map { it.toRow() },
        fares = fares.map { it.toRow() },
    )

internal fun ShapesDto.toRows(): List<ShapeEntity> = shapes.map {
    ShapeEntity(it.patternId, it.polyline)
}

internal fun List<RegionLinks>.toRows(): List<RegionEntity> = mapIndexed { order, links ->
    val region = links.region
    RegionEntity(
        id = region.id,
        name = region.name,
        timeZone = region.timeZone,
        southWestLat = region.bounds.southWest.lat,
        southWestLng = region.bounds.southWest.lng,
        northEastLat = region.bounds.northEast.lat,
        northEastLng = region.bounds.northEast.lng,
        live = region.live,
        catalogUrl = links.catalogUrl.toString(),
        catalogVersion = links.catalogVersion,
        shapesUrl = links.shapesUrl.toString(),
        mapUrl = links.mapUrl?.toString(),
        mapBytes = region.map?.bytes,
        mapUpdated = region.map?.updated,
        listOrder = order,
    )
}

internal fun RouteDto.toRow() = RouteEntity(id, code, name, category, color, textColor, fareId)

internal fun StopDto.toRow() =
    StopEntity(
        id = id,
        code = code,
        name = name,
        kind = kind.wire,
        lat = position.lat,
        lng = position.lng,
        parentId = parentId,
        platformCode = platformCode,
        description = description,
    )

internal fun PatternDto.toRow() = PatternEntity(id, routeId, direction, headsign, name, serviceId)

internal fun ServiceDto.toRow() =
    ServiceEntity(id, days.mapNotNull { it.toDayOfWeek() }.toSet().toMask(), startDate, endDate)

internal fun FareDto.toRow() = FareEntity(id, price, currency)

internal fun Set<DayOfWeek>.toMask(): Int = fold(0) { mask, day -> mask or (1 shl day.ordinal) }

internal fun Int.toDays(): Set<DayOfWeek> =
    DayOfWeek.entries.filter { this and (1 shl it.ordinal) != 0 }.toSet()

private fun ServiceDayDto.toDayOfWeek(): DayOfWeek? =
    when (this) {
        ServiceDayDto.Mon -> DayOfWeek.MONDAY
        ServiceDayDto.Tue -> DayOfWeek.TUESDAY
        ServiceDayDto.Wed -> DayOfWeek.WEDNESDAY
        ServiceDayDto.Thu -> DayOfWeek.THURSDAY
        ServiceDayDto.Fri -> DayOfWeek.FRIDAY
        ServiceDayDto.Sat -> DayOfWeek.SATURDAY
        ServiceDayDto.Sun -> DayOfWeek.SUNDAY
        ServiceDayDto.Unknown -> null
    }

internal fun RouteEntity.toRoute() =
    Route(
        id = RouteId(id),
        code = code,
        name = name,
        category = category,
        color = color?.toRgbColor(),
        textColor = textColor?.toRgbColor(),
        fareId = fareId?.let(::FareId),
    )

private fun String.toRgbColor(): RgbColor? = toIntOrNull(16)?.let(::RgbColor)

internal fun StopWithRelations.toStop() = stop.toStop(routeIds, transfers, facilities)

internal fun StopEntity.toStop(
    routeIds: List<StopRouteEntity>,
    transfers: List<StopTransferEntity>,
    facilities: List<StopFacilityEntity>,
) =
    Stop(
        id = StopId(id),
        code = code,
        name = name,
        kind = kind.toStopKind(),
        position = GeoPoint(lat, lng),
        parentId = parentId?.let(::StopId),
        platformCode = platformCode,
        description = description,
        routeIds = routeIds.map { RouteId(it.routeId) },
        transferIds = transfers.map { StopId(it.transferStopId) },
        facilities = facilities.sortedBy { it.order }.map { Facility(it.key, it.title) },
    )

private fun String.toStopKind(): StopKind =
    when (this) {
        "station" -> StopKind.Station
        "platform" -> StopKind.Platform
        "roadside" -> StopKind.Roadside
        else -> StopKind.Unknown
    }

internal fun PatternWithStops.toPattern() =
    Pattern(
        id = PatternId(pattern.id),
        routeId = RouteId(pattern.routeId),
        direction = if (pattern.direction == 0) Direction.Outbound else Direction.Inbound,
        headsign = pattern.headsign,
        name = pattern.name,
        serviceId = ServiceId(pattern.serviceId),
        stops =
            stops
                .sortedBy { it.sequence }
                .map { PatternStop(StopId(it.stopId), it.fromStartSeconds.seconds) },
        headways =
            headways
                .sortedBy { it.startSeconds }
                .map {
                    Headway(it.startSeconds.seconds, it.endSeconds.seconds, it.everySeconds.seconds)
                },
    )

internal fun RegionEntity.toRegion() =
    Region(
        id = RegionId(id),
        name = name,
        timeZone = TimeZone.of(timeZone),
        bounds =
            GeoBounds(GeoPoint(southWestLat, southWestLng), GeoPoint(northEastLat, northEastLng)),
        hasLiveBuses = live,
    )

internal fun ShapeEntity.toShape() = Shape(PatternId(patternId), polyline)
