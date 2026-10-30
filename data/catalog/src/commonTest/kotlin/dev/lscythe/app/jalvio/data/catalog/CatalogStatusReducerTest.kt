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

import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.lscythe.app.jalvio.data.catalog.db.CatalogMetaEntity
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.lscythe.app.jalvio.domain.catalog.CatalogStatus
import dev.lscythe.app.jalvio.domain.catalog.model.GeoBounds
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Instant
import kotlinx.datetime.TimeZone

private val jakarta =
    Region(
        id = RegionId("jakarta"),
        name = "Jakarta",
        timeZone = TimeZone.of("Asia/Jakarta"),
        bounds = GeoBounds(GeoPoint(-6.45, 106.6), GeoPoint(-6.05, 107.05)),
        hasLiveBuses = true,
    )

private val stored =
    CatalogMetaEntity(
        regionsEtag = "W/\"r1\"",
        regionId = "jakarta",
        catalogEtag = "W/\"abc\"",
        feedVersion = "FEED_VERSION",
        facilitiesSyncedAt = null,
        shapesFeedVersion = "FEED_VERSION",
        updatedAt = 1_000L,
    )

private val regionListOnly = stored.copy(regionId = null, catalogEtag = null, updatedAt = null)

private val ready = CatalogStatus.Ready(jakarta, Instant.fromEpochMilliseconds(1_000L))

class CatalogStatusReducerTest :
    BehaviorSpec({
        Given("no stored catalog") {
            Then("a running sync is downloading") {
                catalogStatus(null, null, SyncRun.Running, online = true) shouldBe
                    CatalogStatus.Downloading
            }
            Then("an idle sync is downloading") {
                catalogStatus(null, null, SyncRun.Idle, online = true) shouldBe
                    CatalogStatus.Downloading
            }
            Then("only a region list is still downloading") {
                catalogStatus(regionListOnly, null, SyncRun.Running, online = true) shouldBe
                    CatalogStatus.Downloading
            }
            Then("a failed sync is unavailable with its error") {
                catalogStatus(
                    null,
                    null,
                    SyncRun.Failed(CatalogError.Offline),
                    online = true,
                ) shouldBe CatalogStatus.Unavailable(CatalogError.Offline)
            }
            Then("an idle sync while offline is unavailable offline") {
                catalogStatus(null, null, SyncRun.Idle, online = false) shouldBe
                    CatalogStatus.Unavailable(CatalogError.Offline)
            }
            Then("a running sync while offline is still downloading") {
                catalogStatus(null, null, SyncRun.Running, online = false) shouldBe
                    CatalogStatus.Downloading
            }
        }
        Given("a stored catalog") {
            Then("a failed refresh stays ready") {
                catalogStatus(
                    stored,
                    jakarta,
                    SyncRun.Failed(CatalogError.Offline),
                    online = true,
                ) shouldBe ready
            }
            Then("a running refresh stays ready") {
                catalogStatus(stored, jakarta, SyncRun.Running, online = true) shouldBe ready
            }
            Then("it stays ready while offline") {
                catalogStatus(stored, jakarta, SyncRun.Idle, online = false) shouldBe ready
            }
        }
        Given("a sync that needs the rider to choose") {
            val choice = SyncRun.NeedsChoice(listOf(jakarta))
            Then("the rider is asked without a catalog") {
                catalogStatus(null, null, choice, online = true) shouldBe
                    CatalogStatus.ChooseRegion(listOf(jakarta))
            }
            Then("the rider is asked with a catalog") {
                catalogStatus(stored, jakarta, choice, online = true) shouldBe
                    CatalogStatus.ChooseRegion(listOf(jakarta))
            }
        }
    })
