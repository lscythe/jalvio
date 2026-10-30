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

import arrow.core.left
import arrow.core.right
import dev.lscythe.app.jalvio.api.catalog.dto.ShapeDto
import dev.lscythe.app.jalvio.api.catalog.dto.ShapesDto
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.Fetched
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield

private fun CatalogHarness.publish(feedVersion: String, version: String, polyline: String? = null) {
    api.regions = regionList(regionLinks("jakarta", version), etag = "W/\"$version\"")
    api.catalog = Fetched.Changed(catalogFixture.copy(feedVersion = feedVersion), version).right()
    if (polyline != null) {
        api.shapes =
            Fetched.Changed(ShapesDto(feedVersion, listOf(ShapeDto("1-R01", polyline))), null)
                .right()
    }
}

class CatalogSyncRunnerTest :
    BehaviorSpec({
        Given("a first sync") {
            val harness = CatalogHarness()

            Then("regions, catalog and shapes are fetched and stored") {
                harness.runner.run() shouldBe Unit.right()
                harness.api.regionsRequests.size shouldBe 1
                harness.api.catalogRequests.single().toString() shouldBe
                    "https://api.test/v1/regions/jakarta/catalog"
                harness.api.shapesRequests.size shouldBe 1
                harness.dao.routes().first().size shouldBe 3
                harness.dao.shape("1-R01").first()!!.polyline shouldBe "abc"
                val meta = harness.dao.metaOnce()!!
                meta.regionId shouldBe "jakarta"
                meta.catalogEtag shouldBe "W/\"abc\""
                meta.feedVersion shouldBe "FEED_VERSION"
                meta.shapesFeedVersion shouldBe "FEED_VERSION"
                meta.updatedAt shouldBe syncStartedAt.toEpochMilliseconds()
                harness.selection.selected.first() shouldBe RegionId("jakarta")
                harness.runner.run.value shouldBe SyncRun.Idle
            }
        }

        Given("a stored catalog whose weak ETag the region list still reports") {
            val harness = CatalogHarness()
            harness.runner.run()
            harness.runner.run() shouldBe Unit.right()

            Then("neither catalog nor shapes are requested again") {
                harness.api.catalogRequests.size shouldBe 1
                harness.api.shapesRequests.size shouldBe 1
            }
        }

        Given("a region list that has not changed") {
            val harness = CatalogHarness()
            harness.runner.run()
            harness.api.regions = Fetched.NotModified("W/\"r1\"").right()

            Then("the cached list is used") {
                harness.runner.run() shouldBe Unit.right()
                harness.api.regionsRequests shouldContainExactly listOf(null, "W/\"r1\"")
                harness.dao.regions().map { it.id } shouldContainExactly listOf("jakarta")
                harness.api.catalogRequests.size shouldBe 1
            }
        }

        Given("a new catalog ETag from the same feed") {
            val harness = CatalogHarness()
            harness.runner.run()
            harness.publish(feedVersion = "FEED_VERSION", version = "W/\"def\"")

            Then("the catalog is fetched and the shapes are kept") {
                harness.runner.run() shouldBe Unit.right()
                harness.api.catalogRequests.size shouldBe 2
                harness.api.shapesRequests.size shouldBe 1
                harness.dao.shape("1-R01").first()!!.polyline shouldBe "abc"
                harness.dao.metaOnce()!!.catalogEtag shouldBe "W/\"def\""
            }
        }

        Given("a new feed") {
            val harness = CatalogHarness()
            harness.runner.run()
            harness.publish(feedVersion = "FEED_2", version = "W/\"def\"", polyline = "xyz")

            Then("catalog and shapes are both replaced") {
                harness.runner.run() shouldBe Unit.right()
                harness.api.catalogRequests.size shouldBe 2
                harness.api.shapesRequests.size shouldBe 2
                harness.dao.shape("1-R01").first()!!.polyline shouldBe "xyz"
                harness.dao.metaOnce()!!.shapesFeedVersion shouldBe "FEED_2"
            }
        }

        Given("a new feed whose shapes cannot be fetched") {
            val harness = CatalogHarness()
            harness.runner.run()
            val metaBefore = harness.dao.metaOnce()
            harness.publish(feedVersion = "FEED_2", version = "W/\"def\"")
            harness.api.shapes = ApiError.Unreachable.left()

            Then("the sync is offline and the stored catalog is untouched") {
                harness.runner.run() shouldBe
                    CatalogSyncFailure(CatalogError.Offline, ApiError.Unreachable).left()
                harness.dao.metaOnce()?.copy(regionsEtag = null) shouldBe
                    metaBefore?.copy(regionsEtag = null)
                harness.dao.routes().first().size shouldBe 3
                harness.dao.shape("1-R01").first()!!.polyline shouldBe "abc"
                harness.runner.run.value shouldBe SyncRun.Failed(CatalogError.Offline)
            }
        }

        Given("a stored region that is no longer listed") {
            val harness = CatalogHarness()
            harness.runner.run()
            harness.api.regions = regionList(bandungLinks, etag = "W/\"r2\"")

            Then("the remaining region is selected and downloaded") {
                harness.runner.run() shouldBe Unit.right()
                harness.selection.selected.first() shouldBe RegionId("bandung")
                harness.api.catalogRequests.last().toString() shouldBe
                    "https://api.test/v1/regions/bandung/catalog"
                harness.dao.metaOnce()!!.regionId shouldBe "bandung"
            }
        }

        Given("two regions and a rider in neither") {
            val harness = CatalogHarness(lastKnown = inTheSea)
            harness.api.regions = regionList(jakartaLinks, bandungLinks)

            Then("the rider is asked and nothing is downloaded") {
                harness.runner.run() shouldBe Unit.right()
                harness.runner.run.value.shouldBeInstanceOf<SyncRun.NeedsChoice>().regions.map {
                    it.id
                } shouldContainExactly listOf(RegionId("jakarta"), RegionId("bandung"))
                harness.api.catalogRequests.shouldBeEmpty()
            }
            Then("choosing a region downloads it") {
                harness.runner.runFor(RegionId("bandung")) shouldBe Unit.right()
                harness.selection.selected.first() shouldBe RegionId("bandung")
                harness.dao.metaOnce()!!.regionId shouldBe "bandung"
                harness.runner.run.value shouldBe SyncRun.Idle
            }
        }

        Given("two regions and a rider in one of them") {
            val harness = CatalogHarness(lastKnown = inBandung)
            harness.api.regions = regionList(jakartaLinks, bandungLinks)

            Then("the region containing the rider is downloaded") {
                harness.runner.run() shouldBe Unit.right()
                harness.dao.metaOnce()!!.regionId shouldBe "bandung"
            }
        }

        Given("an empty region list") {
            val harness = CatalogHarness()
            harness.api.regions = regionList()

            Then("the catalog is not ready") {
                harness.runner.run() shouldBe CatalogSyncFailure(CatalogError.NotReady, null).left()
                harness.runner.run.value shouldBe SyncRun.Failed(CatalogError.NotReady)
            }
        }

        Given("two overlapping syncs") {
            val harness = CatalogHarness()
            val gate = CompletableDeferred<Unit>()
            harness.api.regionsGate = gate

            Then("they share one run") {
                coroutineScope {
                    val runs = List(2) { async { harness.runner.run() } }
                    repeat(10) { yield() }
                    gate.complete(Unit)
                    runs.awaitAll() shouldBe List(2) { Unit.right() }
                }
                harness.api.regionsRequests.size shouldBe 1
            }
        }

        Given("a sync cancelled while the catalog downloads") {
            val harness = CatalogHarness()
            harness.api.catalogGate = CompletableDeferred()

            Then("nothing is written and the next sync completes") {
                coroutineScope {
                    val job = launch { harness.runner.run() }
                    harness.api.catalogStarted.await()
                    job.cancelAndJoin()
                }
                harness.dao.routes().first().shouldBeEmpty()
                harness.dao.metaOnce()?.catalogEtag shouldBe null
                harness.runner.run.value shouldBe SyncRun.Idle

                harness.api.catalogGate = null
                harness.runner.run() shouldBe Unit.right()
                harness.dao.routes().first().size shouldBe 3
            }
        }

        Given("a region choice whose screen goes away mid-download") {
            val harness = CatalogHarness(lastKnown = inTheSea)
            harness.api.regions = regionList(jakartaLinks, bandungLinks)
            harness.runner.run()
            val gate = CompletableDeferred<Unit>()
            harness.api.catalogGate = gate

            Then("the chosen region still finishes downloading") {
                coroutineScope {
                    val choice = launch { harness.runner.runFor(RegionId("bandung")) }
                    harness.api.catalogStarted.await()
                    choice.cancelAndJoin()
                }
                gate.complete(Unit)
                withTimeout(5.seconds) { harness.dao.meta().first { it?.regionId == "bandung" } }
                harness.dao.routes().first().size shouldBe 3
            }
        }
    })
