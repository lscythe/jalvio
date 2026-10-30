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

import dev.lscythe.app.jalvio.api.catalog.dto.CatalogDto
import dev.lscythe.app.jalvio.core.network.JalvioJson
import dev.lscythe.app.jalvio.core.testing.readResource
import dev.lscythe.app.jalvio.data.catalog.toRows
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

private val fixture: CatalogDto = JalvioJson.decodeFromString(readResource("catalog/catalog.json"))

private val meta =
    CatalogMetaEntity(
        regionsEtag = "W/\"r1\"",
        regionId = "jakarta",
        catalogEtag = "W/\"c1\"",
        feedVersion = "FEED_VERSION",
        facilitiesSyncedAt = null,
        shapesFeedVersion = "FEED_VERSION",
        updatedAt = 1_000L,
    )

private val shapes = listOf(ShapeEntity("1-R01", "abc"), ShapeEntity("1-R02", "def"))

private val jakarta =
    RegionEntity(
        id = "jakarta",
        name = "Jakarta",
        timeZone = "Asia/Jakarta",
        southWestLat = -6.45,
        southWestLng = 106.6,
        northEastLat = -6.05,
        northEastLng = 107.05,
        live = true,
        catalogUrl = "https://api.test/v1/regions/jakarta/catalog",
        catalogVersion = "W/\"c1\"",
        shapesUrl = "https://api.test/v1/regions/jakarta/catalog/shapes",
        mapUrl = null,
        mapBytes = null,
        mapUpdated = null,
        listOrder = 0,
    )

class CatalogDaoTest :
    BehaviorSpec({
        Given("a stored catalog") {
            val database = inMemoryCatalogDatabase()
            val dao = database.catalogDao()
            dao.replaceCatalog(meta, fixture.toRows(), shapes)

            Then("a pattern's stops come in sequence order") {
                dao.patternStops("1-R02").first().map { it.stop.id } shouldContainExactly
                    listOf("G00003", "G00002", "G00001")
            }
            Then("a station's platforms are only its own children") {
                dao.platforms("H00002P").first().map { it.stop.id } shouldContainExactly
                    listOf("G00002")
            }
            Then("a stop comes with its routes, transfers and facilities") {
                val station = dao.stop("H00003P").first()!!
                station.routeIds.map { it.routeId } shouldContainExactly listOf("1")
                station.transfers.map { it.transferStopId } shouldContainExactly listOf("B00004P")
                dao.stop("H00002P")
                    .first()!!
                    .facilities
                    .sortedBy { it.order }
                    .map { it.key } shouldContainExactly listOf("toilet", "lift")
            }
            Then("a route's patterns come with their stops and headways") {
                val patterns = dao.patterns("1").first().associateBy { it.pattern.id }
                patterns.keys shouldBe setOf("1-R01", "1-R02")
                patterns.getValue("1-R01").headways.size shouldBe 2
                patterns.getValue("1-R01").stops.size shouldBe 3
            }
        }

        Given("a second, smaller catalog") {
            val database = inMemoryCatalogDatabase()
            val dao = database.catalogDao()
            dao.replaceCatalog(meta, fixture.toRows(), shapes)
            val smaller =
                fixture
                    .copy(
                        routes = fixture.routes.filter { it.id == "JAK.18" },
                        stops = fixture.stops.filter { it.id.startsWith("B") },
                        patterns = fixture.patterns.filter { it.routeId == "JAK.18" },
                    )
                    .toRows()
            dao.replaceCatalog(
                meta.copy(catalogEtag = "W/\"c2\""),
                smaller,
                listOf(ShapeEntity("JAK.18-R01", "ghi")),
            )

            Then("no rows from the first remain") {
                dao.routes().first().map { it.id } shouldContainExactly listOf("JAK.18")
                dao.stop("G00001").first().shouldBeNull()
                dao.patterns("1").first().shouldBeEmpty()
                dao.shape("1-R01").first().shouldBeNull()
                dao.metaOnce()!!.catalogEtag shouldBe "W/\"c2\""
            }
        }

        Given("a catalog refresh from the same feed") {
            val database = inMemoryCatalogDatabase()
            val dao = database.catalogDao()
            dao.replaceCatalog(meta, fixture.toRows(), shapes)
            dao.replaceCatalog(meta.copy(catalogEtag = "W/\"c2\""), fixture.toRows(), null)

            Then("the stored shapes are kept") {
                dao.shape("1-R01").first()!!.polyline shouldBe "abc"
            }
        }

        Given("a cleared catalog") {
            val database = inMemoryCatalogDatabase()
            val dao = database.catalogDao()
            dao.replaceRegions("W/\"r1\"", listOf(jakarta))
            dao.replaceCatalog(meta, fixture.toRows(), shapes)
            dao.clearCatalog()

            Then("every catalog table and the meta are empty") {
                dao.routes().first().shouldBeEmpty()
                dao.stop("B00001P").first().shouldBeNull()
                dao.patterns("JAK.18").first().shouldBeEmpty()
                dao.patternStops("1-R01").first().shouldBeEmpty()
                dao.shape("1-R01").first().shouldBeNull()
                dao.metaOnce().shouldBeNull()
            }
            Then("the regions are kept") { dao.regions() shouldContainExactly listOf(jakarta) }
        }

        Given("a region list without a stored catalog") {
            val database = inMemoryCatalogDatabase()
            val dao = database.catalogDao()
            dao.replaceRegions("W/\"r1\"", listOf(jakarta))

            Then("its ETag is kept in the meta without a catalog") {
                val stored = dao.metaOnce()!!
                stored.regionsEtag shouldBe "W/\"r1\""
                stored.catalogEtag.shouldBeNull()
            }
            Then("a newer list replaces it and keeps a stored catalog's meta") {
                dao.replaceCatalog(meta, fixture.toRows(), shapes)
                dao.replaceRegions("W/\"r2\"", listOf(jakarta.copy(name = "DKI Jakarta")))
                dao.regions().single().name shouldBe "DKI Jakarta"
                dao.metaOnce() shouldBe meta.copy(regionsEtag = "W/\"r2\"")
            }
        }
    })
