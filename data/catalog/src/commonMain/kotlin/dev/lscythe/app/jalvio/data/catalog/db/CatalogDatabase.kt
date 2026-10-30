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

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor

@Database(
    entities =
        [
            CatalogMetaEntity::class,
            RegionEntity::class,
            RouteEntity::class,
            StopEntity::class,
            StopRouteEntity::class,
            StopTransferEntity::class,
            StopFacilityEntity::class,
            PatternEntity::class,
            PatternStopEntity::class,
            HeadwayEntity::class,
            ServiceEntity::class,
            FareEntity::class,
            ShapeEntity::class,
        ],
    version = 1,
)
@ConstructedBy(CatalogDatabaseConstructor::class)
internal abstract class CatalogDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
}

@Suppress("KotlinNoActualForExpect")
internal expect object CatalogDatabaseConstructor : RoomDatabaseConstructor<CatalogDatabase> {
    override fun initialize(): CatalogDatabase
}

internal const val CATALOG_DATABASE_FILE_NAME = "catalog.db"
