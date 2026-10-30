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

import dev.lscythe.app.jalvio.core.network.JalvioJson
import dev.lscythe.app.jalvio.core.testing.readResource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class CatalogDtoTest :
    BehaviorSpec({
        Given("the catalog fixture") {
            val catalog =
                JalvioJson.decodeFromString<CatalogDto>(readResource("catalog/catalog.json"))

            Then("routes keep their colors, class and optional fare") {
                catalog.routes shouldHaveSize 3
                catalog.routes[0] shouldBe
                    RouteDto(
                        id = "1",
                        code = "1",
                        name = "Blok M - Kota",
                        category = "BRT",
                        color = "D21F3C",
                        textColor = "FFFFFF",
                        fareId = "FP",
                    )
                catalog.routes[1].fareId.shouldBeNull()
            }
            Then("stops keep kind, parent, children, transfers and facilities") {
                val platform = catalog.stops.single { it.id == "G00001" }
                platform.kind shouldBe StopKindDto.Platform
                platform.parentId shouldBe "H00001P"
                val station = catalog.stops.single { it.id == "H00002P" }
                station.kind shouldBe StopKindDto.Station
                station.childIds shouldContainExactly listOf("G00002")
                station.facilities shouldContainExactly
                    listOf(FacilityDto("toilet", "Toilet"), FacilityDto("lift", "Lift"))
                catalog.stops.single { it.id == "B00004P" }.transferIds shouldContainExactly
                    listOf("H00003P")
            }
            Then("patterns keep stop times and headways") {
                val pattern = catalog.patterns.single { it.id == "1-R01" }
                pattern.direction shouldBe 0
                pattern.stops.map { it.fromStartSeconds } shouldContainExactly listOf(0, 1200, 2400)
                pattern.headways.last() shouldBe HeadwayDto(79200, 90000, 1800)
            }
            Then("services and fares decode") {
                catalog.services.single { it.id == "HK" }.days shouldContainExactly
                    listOf(
                        ServiceDayDto.Mon,
                        ServiceDayDto.Tue,
                        ServiceDayDto.Wed,
                        ServiceDayDto.Thu,
                        ServiceDayDto.Fri,
                    )
                catalog.fares shouldContainExactly
                    listOf(FareDto("FP", 3500, "IDR"), FareDto("GR", 0, "IDR"))
                catalog.facilitiesSyncedAt shouldBe "2026-10-21T20:00:00Z"
            }
        }

        Given("values the app does not know yet") {
            Then("an unknown stop kind decodes as Unknown") {
                JalvioJson.decodeFromString<StopDto>(
                        """{"id":"F1","name":"Pier","kind":"ferry","position":{"lat":0,"lng":0},"routeIds":[]}"""
                    )
                    .kind shouldBe StopKindDto.Unknown
            }
            Then("an unknown service day decodes as Unknown") {
                JalvioJson.decodeFromString<ServiceDto>(
                        """{"id":"X","days":["mon","holiday"],"startDate":"2026-01-01","endDate":"2026-12-31"}"""
                    )
                    .days shouldContainExactly listOf(ServiceDayDto.Mon, ServiceDayDto.Unknown)
            }
        }

        Given("the region list fixture") {
            val regions =
                JalvioJson.decodeFromString<RegionListDto>(readResource("catalog/regions.json"))
                    .regions

            Then("each region keeps its bounds, live flag and links") {
                val jakarta = regions.first()
                jakarta.id shouldBe "jakarta"
                jakarta.timeZone shouldBe "Asia/Jakarta"
                jakarta.bounds.southWest shouldBe PositionDto(-6.45, 106.6)
                jakarta.live shouldBe true
                jakarta.catalog shouldBe
                    CatalogLinkDto(
                        "/v1/regions/jakarta/catalog",
                        "W/\"3f2a\"",
                        "2026-10-30T03:00:00Z",
                    )
                jakarta.map shouldBe
                    MapLinkDto(
                        "https://maps.example.test/jakarta.pmtiles",
                        142_000_000,
                        "2026-10-30",
                    )
            }
            Then("a region without a map or update time decodes") {
                val bandung = regions.last()
                bandung.map.shouldBeNull()
                bandung.catalog.updatedAt.shouldBeNull()
            }
        }

        Given("the shapes fixture") {
            Then("each pattern keeps its polyline") {
                val shapes =
                    JalvioJson.decodeFromString<ShapesDto>(readResource("catalog/shapes.json"))
                shapes.feedVersion shouldBe "FEED_VERSION"
                shapes.shapes.first() shouldBe ShapeDto("1-R01", "nbzd@ql|jSqyGkdD")
            }
        }
    })
