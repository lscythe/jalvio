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

import dev.lscythe.app.jalvio.domain.catalog.model.Facility
import dev.lscythe.app.jalvio.domain.catalog.model.PatternId
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import dev.lscythe.app.jalvio.domain.catalog.model.RouteId
import dev.lscythe.app.jalvio.domain.catalog.model.StopId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class DefaultCatalogRepositoryTest :
    BehaviorSpec({
        Given("no selected region") {
            val harness = CatalogHarness()
            val repository = DefaultCatalogRepository(harness.dao, harness.selection)

            Then("there is no region") { repository.region.first().shouldBeNull() }
        }

        Given("a synced catalog") {
            val harness = CatalogHarness()
            harness.runner.run()
            val repository = DefaultCatalogRepository(harness.dao, harness.selection)

            Then("the region is the selected one from the cached list") {
                repository.region.first()!!.id shouldBe RegionId("jakarta")
            }
            Then("a stop comes with its facilities and routes") {
                val station = repository.stop(StopId("H00002P")).first()!!
                station.facilities shouldContainExactly
                    listOf(Facility("toilet", "Toilet"), Facility("lift", "Lift"))
                station.routeIds shouldContainExactly listOf(RouteId("1"))
            }
            Then("a pattern's stops come in order") {
                repository.patternStops(PatternId("1-R02")).first().map {
                    it.id
                } shouldContainExactly listOf(StopId("G00003"), StopId("G00002"), StopId("G00001"))
            }
            Then("a route's patterns come in direction order") {
                repository.patterns(RouteId("1")).first().map { it.id } shouldContainExactly
                    listOf(PatternId("1-R01"), PatternId("1-R02"))
            }
        }
    })
