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

import arrow.core.right
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.lscythe.app.jalvio.domain.catalog.CatalogStatus
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.first

private fun CatalogHarness.catalogSync(engine: FakeSyncEngine = FakeSyncEngine()) =
    DefaultCatalogSync(runner, dao, engine, network)

class DefaultCatalogSyncTest :
    BehaviorSpec({
        Given("the catalog status") {
            val harness = CatalogHarness()
            val sync = harness.catalogSync()

            Then("it is downloading before the first sync") {
                sync.status.first() shouldBe CatalogStatus.Downloading
            }
            Then("it is ready after a sync, with the region and the sync time") {
                harness.runner.run()
                val ready = sync.status.first().shouldBeInstanceOf<CatalogStatus.Ready>()
                ready.region.id shouldBe RegionId("jakarta")
                ready.updatedAt shouldBe syncStartedAt
            }
            Then("it stays ready when a refresh fails") {
                harness.api.regions = regionList()
                harness.runner.run()
                sync.status.first().shouldBeInstanceOf<CatalogStatus.Ready>()
            }
        }

        Given("a first launch without a network") {
            val harness = CatalogHarness()
            harness.network.online.value = false
            val sync = harness.catalogSync()

            Then("the catalog is unavailable offline instead of downloading forever") {
                sync.status.first() shouldBe CatalogStatus.Unavailable(CatalogError.Offline)
            }
            Then("it downloads once the network is back") {
                harness.network.online.value = true
                sync.status.first() shouldBe CatalogStatus.Downloading
            }
        }

        Given("a rider who has to choose") {
            val harness = CatalogHarness(lastKnown = inTheSea)
            harness.api.regions = regionList(jakartaLinks, bandungLinks)
            val sync = harness.catalogSync()
            harness.runner.run()

            Then("the status asks for a region") {
                sync.status.first().shouldBeInstanceOf<CatalogStatus.ChooseRegion>()
            }
            Then("selecting a region stores it and downloads it") {
                sync.selectRegion(RegionId("bandung")) shouldBe Unit.right()
                harness.selection.selected.first() shouldBe RegionId("bandung")
                sync.status.first().shouldBeInstanceOf<CatalogStatus.Ready>().region.id shouldBe
                    RegionId("bandung")
            }
        }

        Given("a retry") {
            val engine = FakeSyncEngine()
            val sync = CatalogHarness().catalogSync(engine)

            Then("it asks the sync engine once") {
                sync.retry()
                engine.requests shouldBe 1
            }
        }
    })
