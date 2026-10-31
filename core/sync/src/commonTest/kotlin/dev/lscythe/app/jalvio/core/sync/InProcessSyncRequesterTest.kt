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

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

private fun TestScope.requester(engine: ScriptedSyncEngine, network: FakeNetworkMonitor) =
    InProcessSyncRequester(lazy { engine }, network, backgroundScope).also {
        engine.clock = { testScheduler.currentTime }
    }

private fun TestScope.settle() {
    testScheduler.advanceTimeBy(1.hours)
    runCurrent()
}

class InProcessSyncRequesterTest :
    BehaviorSpec({
        Given("a sync that succeeds") {
            Then("it runs once and stops") {
                runTest {
                    val engine = ScriptedSyncEngine(SyncOutcome.Done)
                    requester(engine, FakeNetworkMonitor(online = true)).request()
                    settle()
                    engine.runTimes shouldBe listOf(0L)
                }
            }
        }

        Given("a sync that should be retried while offline") {
            Then("it runs again once the network is back") {
                runTest {
                    val network = FakeNetworkMonitor(online = false)
                    val engine = ScriptedSyncEngine(SyncOutcome.RetryLater, SyncOutcome.Done)
                    requester(engine, network).request()
                    testScheduler.advanceTimeBy(10.minutes)
                    runCurrent()
                    engine.runTimes shouldBe listOf(0L)
                    network.online.value = true
                    settle()
                    engine.runTimes shouldBe listOf(0L, 10.minutes.inWholeMilliseconds)
                }
            }
        }

        Given("a sync that keeps asking to retry while online") {
            Then("retries back off from 30 s and stop after five") {
                runTest {
                    val engine = ScriptedSyncEngine(*Array(10) { SyncOutcome.RetryLater })
                    requester(engine, FakeNetworkMonitor(online = true)).request()
                    settle()
                    engine.runTimes shouldBe
                        listOf(0L, 30_000L, 90_000L, 210_000L, 450_000L, 930_000L)
                }
            }
        }

        Given("a request while one is waiting to retry") {
            Then("it syncs again right away instead of waiting") {
                runTest {
                    val engine = ScriptedSyncEngine(SyncOutcome.RetryLater, SyncOutcome.Done)
                    val requester = requester(engine, FakeNetworkMonitor(online = true))
                    requester.request()
                    testScheduler.advanceTimeBy(10.seconds)
                    runCurrent()
                    requester.request()
                    settle()
                    engine.runTimes shouldBe listOf(0L, 10_000L)
                }
            }
        }
    })
