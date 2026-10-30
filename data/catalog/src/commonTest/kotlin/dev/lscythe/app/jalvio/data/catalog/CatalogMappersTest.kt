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

import dev.lscythe.app.jalvio.api.catalog.dto.CatalogDto
import dev.lscythe.app.jalvio.api.catalog.dto.PositionDto
import dev.lscythe.app.jalvio.api.catalog.dto.ServiceDayDto
import dev.lscythe.app.jalvio.api.catalog.dto.ServiceDto
import dev.lscythe.app.jalvio.api.catalog.dto.StopDto
import dev.lscythe.app.jalvio.api.catalog.dto.StopKindDto
import dev.lscythe.app.jalvio.core.network.JalvioJson
import dev.lscythe.app.jalvio.core.testing.readResource
import dev.lscythe.app.jalvio.domain.catalog.model.RgbColor
import dev.lscythe.app.jalvio.domain.catalog.model.StopKind
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.datetime.DayOfWeek

private val fixture: CatalogDto = JalvioJson.decodeFromString(readResource("catalog/catalog.json"))

private fun roadside(kind: StopKindDto) =
    StopDto(
        id = "X",
        name = "X",
        kind = kind,
        position = PositionDto(0.0, 0.0),
        routeIds = emptyList(),
    )

class CatalogMappersTest :
    BehaviorSpec({
        Given("the catalog fixture as rows") {
            val rows = fixture.toRows()

            Then("a route without a fare keeps a null fare") {
                rows.routes.single { it.id == "JAK.10A" }.fareId.shouldBeNull()
            }
            Then("a platform keeps its parent station") {
                rows.stops.single { it.id == "G00001" }.parentId shouldBe "H00001P"
            }
            Then("stop routes, transfers and facilities become their own rows") {
                rows.stopRoutes
                    .filter { it.stopId == "H00001P" }
                    .map { it.routeId } shouldContainExactly listOf("1")
                rows.transfers.single { it.stopId == "B00004P" }.transferStopId shouldBe "H00003P"
                rows.facilities
                    .filter { it.stopId == "H00002P" }
                    .map { it.key } shouldContainExactly listOf("toilet", "lift")
            }
            Then("pattern stops keep their sequence") {
                rows.patternStops
                    .filter { it.patternId == "1-R02" }
                    .sortedBy { it.sequence }
                    .map { it.stopId } shouldContainExactly listOf("G00003", "G00002", "G00001")
            }
            Then("a route color reads back as RGB") {
                rows.routes.single { it.id == "1" }.toRoute().color shouldBe RgbColor(0xD21F3C)
            }
        }

        Given("service days") {
            Then("every mask round-trips") {
                (0 until 128).forEach { mask -> mask.toDays().toMask() shouldBe mask }
            }
            Then("unknown days are dropped") {
                ServiceDto(
                        "X",
                        listOf(ServiceDayDto.Sat, ServiceDayDto.Unknown),
                        "2026-01-01",
                        "2026-12-31",
                    )
                    .toRow()
                    .days
                    .toDays() shouldBe setOf(DayOfWeek.SATURDAY)
            }
        }

        Given("a stop kind the app does not know") {
            Then("it is stored as unknown and read back as Unknown") {
                val row = roadside(StopKindDto.Unknown).toRow()
                row.kind shouldBe "unknown"
                row.toStop(emptyList(), emptyList(), emptyList()).kind shouldBe StopKind.Unknown
            }
        }
    })
