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

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(tableName = "catalog_meta")
internal data class CatalogMetaEntity(
    @PrimaryKey val id: Int = SINGLE_ROW,
    val regionsEtag: String?,
    val regionId: String?,
    val catalogEtag: String?,
    val feedVersion: String?,
    val facilitiesSyncedAt: String?,
    val shapesFeedVersion: String?,
    val updatedAt: Long?,
) {
    companion object {
        const val SINGLE_ROW = 0
    }
}

@Entity(tableName = "region")
internal data class RegionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val timeZone: String,
    val southWestLat: Double,
    val southWestLng: Double,
    val northEastLat: Double,
    val northEastLng: Double,
    val live: Boolean,
    val catalogUrl: String,
    val catalogVersion: String,
    val shapesUrl: String,
    val mapUrl: String?,
    val mapBytes: Long?,
    val mapUpdated: String?,
    val listOrder: Int,
)

@Entity(tableName = "route")
internal data class RouteEntity(
    @PrimaryKey val id: String,
    val code: String,
    val name: String,
    val category: String?,
    val color: String?,
    val textColor: String?,
    val fareId: String?,
)

@Entity(tableName = "stop", indices = [Index("parentId")])
internal data class StopEntity(
    @PrimaryKey val id: String,
    val code: String?,
    val name: String,
    val kind: String,
    val lat: Double,
    val lng: Double,
    val parentId: String?,
    val platformCode: String?,
    val description: String?,
)

@Entity(tableName = "stop_route", primaryKeys = ["stopId", "routeId"], indices = [Index("routeId")])
internal data class StopRouteEntity(val stopId: String, val routeId: String)

@Entity(tableName = "stop_transfer", primaryKeys = ["stopId", "transferStopId"])
internal data class StopTransferEntity(val stopId: String, val transferStopId: String)

@Entity(tableName = "stop_facility", primaryKeys = ["stopId", "key"], indices = [Index("stopId")])
internal data class StopFacilityEntity(
    val stopId: String,
    val key: String,
    val title: String,
    val order: Int,
)

@Entity(tableName = "pattern", indices = [Index("routeId")])
internal data class PatternEntity(
    @PrimaryKey val id: String,
    val routeId: String,
    val direction: Int,
    val headsign: String?,
    val name: String?,
    val serviceId: String,
)

@Entity(
    tableName = "pattern_stop",
    primaryKeys = ["patternId", "sequence"],
    indices = [Index("stopId")],
)
internal data class PatternStopEntity(
    val patternId: String,
    val sequence: Int,
    val stopId: String,
    val fromStartSeconds: Int,
)

@Entity(
    tableName = "headway",
    primaryKeys = ["patternId", "startSeconds"],
    indices = [Index("patternId")],
)
internal data class HeadwayEntity(
    val patternId: String,
    val startSeconds: Int,
    val endSeconds: Int,
    val everySeconds: Int,
)

@Entity(tableName = "service")
internal data class ServiceEntity(
    @PrimaryKey val id: String,
    val days: Int,
    val startDate: String,
    val endDate: String,
)

@Entity(tableName = "fare")
internal data class FareEntity(@PrimaryKey val id: String, val price: Long, val currency: String)

@Entity(tableName = "shape")
internal data class ShapeEntity(@PrimaryKey val patternId: String, val polyline: String)
