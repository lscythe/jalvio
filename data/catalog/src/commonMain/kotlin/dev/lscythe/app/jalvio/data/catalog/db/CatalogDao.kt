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

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CatalogDao {
    @Transaction
    suspend fun replaceCatalog(
        meta: CatalogMetaEntity,
        rows: CatalogRows,
        shapes: List<ShapeEntity>?,
    ) {
        clearNetwork()
        insertRoutes(rows.routes)
        insertStops(rows.stops)
        insertStopRoutes(rows.stopRoutes)
        insertTransfers(rows.transfers)
        insertFacilities(rows.facilities)
        insertPatterns(rows.patterns)
        insertPatternStops(rows.patternStops)
        insertHeadways(rows.headways)
        insertServices(rows.services)
        insertFares(rows.fares)
        if (shapes != null) {
            deleteShapes()
            insertShapes(shapes)
        }
        upsertMeta(meta)
    }

    @Transaction
    suspend fun replaceRegions(regionsEtag: String?, regions: List<RegionEntity>) {
        deleteRegions()
        insertRegions(regions)
        val stored = metaOnce()
        upsertMeta(
            stored?.copy(regionsEtag = regionsEtag)
                ?: CatalogMetaEntity(
                    regionsEtag = regionsEtag,
                    regionId = null,
                    catalogEtag = null,
                    feedVersion = null,
                    facilitiesSyncedAt = null,
                    shapesFeedVersion = null,
                    updatedAt = null,
                )
        )
    }

    @Transaction
    suspend fun clearCatalog() {
        clearNetwork()
        deleteShapes()
        deleteMeta()
    }

    @Query("SELECT * FROM catalog_meta LIMIT 1") fun meta(): Flow<CatalogMetaEntity?>

    @Query("SELECT * FROM catalog_meta LIMIT 1") suspend fun metaOnce(): CatalogMetaEntity?

    @Query("SELECT * FROM region ORDER BY listOrder") suspend fun regions(): List<RegionEntity>

    @Query("SELECT * FROM region WHERE id = :id") fun region(id: String): Flow<RegionEntity?>

    @Query("SELECT * FROM route ORDER BY code") fun routes(): Flow<List<RouteEntity>>

    @Query("SELECT * FROM route WHERE id = :id") fun route(id: String): Flow<RouteEntity?>

    @Transaction
    @Query("SELECT * FROM stop WHERE id = :id")
    fun stop(id: String): Flow<StopWithRelations?>

    @Transaction
    @Query("SELECT * FROM stop WHERE parentId = :stationId ORDER BY platformCode, name")
    fun platforms(stationId: String): Flow<List<StopWithRelations>>

    @Transaction
    @Query("SELECT * FROM pattern WHERE routeId = :routeId ORDER BY direction, id")
    fun patterns(routeId: String): Flow<List<PatternWithStops>>

    @Transaction
    @Query(
        "SELECT stop.* FROM pattern_stop JOIN stop ON stop.id = pattern_stop.stopId " +
            "WHERE pattern_stop.patternId = :patternId ORDER BY pattern_stop.sequence"
    )
    fun patternStops(patternId: String): Flow<List<StopWithRelations>>

    @Query("SELECT * FROM shape WHERE patternId = :patternId")
    fun shape(patternId: String): Flow<ShapeEntity?>

    @Transaction
    suspend fun clearNetwork() {
        deleteRoutes()
        deleteStops()
        deleteStopRoutes()
        deleteTransfers()
        deleteFacilities()
        deletePatterns()
        deletePatternStops()
        deleteHeadways()
        deleteServices()
        deleteFares()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsertMeta(meta: CatalogMetaEntity)

    @Insert suspend fun insertRegions(rows: List<RegionEntity>)

    @Insert suspend fun insertRoutes(rows: List<RouteEntity>)

    @Insert suspend fun insertStops(rows: List<StopEntity>)

    @Insert suspend fun insertStopRoutes(rows: List<StopRouteEntity>)

    @Insert suspend fun insertTransfers(rows: List<StopTransferEntity>)

    @Insert suspend fun insertFacilities(rows: List<StopFacilityEntity>)

    @Insert suspend fun insertPatterns(rows: List<PatternEntity>)

    @Insert suspend fun insertPatternStops(rows: List<PatternStopEntity>)

    @Insert suspend fun insertHeadways(rows: List<HeadwayEntity>)

    @Insert suspend fun insertServices(rows: List<ServiceEntity>)

    @Insert suspend fun insertFares(rows: List<FareEntity>)

    @Insert suspend fun insertShapes(rows: List<ShapeEntity>)

    @Query("DELETE FROM catalog_meta") suspend fun deleteMeta()

    @Query("DELETE FROM region") suspend fun deleteRegions()

    @Query("DELETE FROM route") suspend fun deleteRoutes()

    @Query("DELETE FROM stop") suspend fun deleteStops()

    @Query("DELETE FROM stop_route") suspend fun deleteStopRoutes()

    @Query("DELETE FROM stop_transfer") suspend fun deleteTransfers()

    @Query("DELETE FROM stop_facility") suspend fun deleteFacilities()

    @Query("DELETE FROM pattern") suspend fun deletePatterns()

    @Query("DELETE FROM pattern_stop") suspend fun deletePatternStops()

    @Query("DELETE FROM headway") suspend fun deleteHeadways()

    @Query("DELETE FROM service") suspend fun deleteServices()

    @Query("DELETE FROM fare") suspend fun deleteFares()

    @Query("DELETE FROM shape") suspend fun deleteShapes()
}
