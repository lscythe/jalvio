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
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.problem.Problem
import dev.lscythe.app.jalvio.core.sync.SyncOutcome
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

private fun failure(error: CatalogError, cause: ApiError? = null) =
    CatalogSyncFailure(error, cause).left()

class CatalogSyncableTest :
    BehaviorSpec({
        Given("an api error") {
            Then("unreachable is offline") {
                ApiError.Unreachable.toCatalogError() shouldBe CatalogError.Offline
            }
            Then("503 is not ready") {
                ApiError.Http(503, null).toCatalogError() shouldBe CatalogError.NotReady
            }
            Then("404 is region gone") {
                ApiError.Http(404, null).toCatalogError() shouldBe CatalogError.RegionGone
            }
            Then("malformed and 500 are failed") {
                ApiError.Malformed.toCatalogError() shouldBe CatalogError.Failed
                ApiError.Http(500, null).toCatalogError() shouldBe CatalogError.Failed
            }
        }

        Given("a sync result") {
            Then("success is done") { Unit.right().toSyncOutcome() shouldBe SyncOutcome.Done }
            Then("offline, not ready and region gone retry later") {
                failure(CatalogError.Offline).toSyncOutcome() shouldBe SyncOutcome.RetryLater
                failure(CatalogError.NotReady).toSyncOutcome() shouldBe SyncOutcome.RetryLater
                failure(CatalogError.RegionGone).toSyncOutcome() shouldBe SyncOutcome.RetryLater
            }
            Then("a failed http call names its status") {
                val outcome =
                    failure(
                            CatalogError.Failed,
                            ApiError.Http(500, Problem(title = "Boom", status = 500)),
                        )
                        .toSyncOutcome()
                outcome.shouldBeInstanceOf<SyncOutcome.Failed>().reason shouldContain "500"
            }
            Then("a malformed response is named") {
                failure(CatalogError.Failed, ApiError.Malformed)
                    .toSyncOutcome()
                    .shouldBeInstanceOf<SyncOutcome.Failed>()
                    .reason shouldContain "Malformed"
            }
        }

        Given("the catalog syncable") {
            val harness = CatalogHarness()
            val syncable = CatalogSyncable(harness.runner)

            Then("it is named catalog") { syncable.name shouldBe "catalog" }
            Then("it runs the catalog sync") {
                syncable.sync() shouldBe SyncOutcome.Done
                harness.api.catalogRequests.size shouldBe 1
            }
        }
    })
