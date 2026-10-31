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
package dev.lscythe.app.jalvio.core.sync

import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runTest

private fun CoroutineScope.engine(
    vararg syncables: Syncable,
    monitor: RecordingMonitor = RecordingMonitor(),
    requester: SyncRequester = NoRequester,
) = DefaultSyncEngine(syncables.toSet(), monitor, requester, this)

class DefaultSyncEngineTest :
    BehaviorSpec({
        Given("outcomes to combine") {
            Then("nothing to run is Done") {
                combineOutcomes(emptyList()) shouldBe SyncOutcome.Done
            }
            Then("all done is Done") {
                combineOutcomes(listOf(SyncOutcome.Done, SyncOutcome.Done)) shouldBe
                    SyncOutcome.Done
            }
            Then("a failure wins over done") {
                combineOutcomes(listOf(SyncOutcome.Done, SyncOutcome.Failed("x"))) shouldBe
                    SyncOutcome.Failed("x")
            }
            Then("retry later wins over a failure") {
                combineOutcomes(listOf(SyncOutcome.Failed("x"), SyncOutcome.RetryLater)) shouldBe
                    SyncOutcome.RetryLater
            }
        }

        Given("two syncables") {
            Then("both run once, at the same time") {
                runTest {
                    val catalog = GatedSyncable("catalog") { SyncOutcome.Done }
                    val legal = GatedSyncable("legal") { SyncOutcome.Done }
                    val run = async { backgroundScope.engine(catalog, legal).syncNow() }
                    catalog.entered.await()
                    legal.entered.await()
                    catalog.gate.complete(Unit)
                    legal.gate.complete(Unit)
                    run.await() shouldBe SyncOutcome.Done
                    catalog.runs shouldBe 1
                    legal.runs shouldBe 1
                }
            }
        }

        Given("a sync already running") {
            Then("a second request shares it") {
                runTest {
                    val catalog = GatedSyncable("catalog") { SyncOutcome.RetryLater }
                    val engine = backgroundScope.engine(catalog)
                    val first = async { engine.syncNow() }
                    catalog.entered.await()
                    val second = async { engine.syncNow() }
                    catalog.gate.complete(Unit)
                    first.await() shouldBe SyncOutcome.RetryLater
                    second.await() shouldBe SyncOutcome.RetryLater
                    catalog.runs shouldBe 1
                }
            }
            Then("a request after it finished starts a new run") {
                runTest {
                    val catalog = ImmediateSyncable("catalog") { SyncOutcome.Done }
                    val engine = backgroundScope.engine(catalog)
                    engine.syncNow()
                    engine.syncNow()
                    catalog.runs shouldBe 2
                }
            }
        }

        Given("a syncable that fails") {
            Then("the failure is recorded with its name and reason") {
                runTest {
                    val monitor = RecordingMonitor()
                    val engine =
                        backgroundScope.engine(
                            ImmediateSyncable("catalog") { SyncOutcome.Failed("http 500") },
                            monitor = monitor,
                        )
                    engine.syncNow() shouldBe SyncOutcome.Failed("http 500")
                    monitor.errors shouldHaveSize 1
                    monitor.errors.single().message.orEmpty() shouldContain "catalog"
                    monitor.errors.single().message.orEmpty() shouldContain "http 500"
                }
            }
            Then("retry later is not recorded") {
                runTest {
                    val monitor = RecordingMonitor()
                    backgroundScope
                        .engine(
                            ImmediateSyncable("catalog") { SyncOutcome.RetryLater },
                            monitor = monitor,
                        )
                        .syncNow()
                    monitor.errors.shouldBeEmpty()
                }
            }
            Then("an exception counts as a recorded failure") {
                runTest {
                    val monitor = RecordingMonitor()
                    val outcome =
                        backgroundScope
                            .engine(
                                ImmediateSyncable("catalog") { error("boom") },
                                ImmediateSyncable("legal") { SyncOutcome.Done },
                                monitor = monitor,
                            )
                            .syncNow()
                    (outcome is SyncOutcome.Failed) shouldBe true
                    monitor.errors shouldHaveSize 1
                }
            }
        }

        Given("isSyncing") {
            Then("it is true only while a run is active") {
                runTest {
                    val catalog = GatedSyncable("catalog") { SyncOutcome.Done }
                    val engine = backgroundScope.engine(catalog)
                    engine.isSyncing.test {
                        awaitItem() shouldBe false
                        val run = async { engine.syncNow() }
                        awaitItem() shouldBe true
                        catalog.gate.complete(Unit)
                        run.await()
                        awaitItem() shouldBe false
                    }
                }
            }
            Then("it returns to false when the run is cancelled") {
                runTest {
                    val catalog = GatedSyncable("catalog") { SyncOutcome.Done }
                    val runScope =
                        CoroutineScope(
                            backgroundScope.coroutineContext +
                                Job(backgroundScope.coroutineContext[Job])
                        )
                    val engine = runScope.engine(catalog)
                    engine.isSyncing.test {
                        awaitItem() shouldBe false
                        async { runCatching { engine.syncNow() } }
                        awaitItem() shouldBe true
                        runScope.cancel()
                        awaitItem() shouldBe false
                    }
                }
            }
        }

        Given("requestSync") {
            Then("it hands the run to the platform requester") {
                runTest {
                    var requests = 0
                    backgroundScope.engine(requester = SyncRequester { requests++ }).requestSync()
                    requests shouldBe 1
                }
            }
        }
    })
