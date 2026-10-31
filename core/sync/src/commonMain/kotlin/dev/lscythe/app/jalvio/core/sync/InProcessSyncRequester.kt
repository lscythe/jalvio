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

import dev.lscythe.app.jalvio.core.common.coroutines.ApplicationScope
import dev.lscythe.app.jalvio.core.network.monitor.NetworkMonitor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalAtomicApi::class)
@Inject
@SingleIn(AppScope::class)
class InProcessSyncRequester(
    private val engine: Lazy<SyncEngine>,
    private val network: NetworkMonitor,
    @ApplicationScope private val scope: CoroutineScope,
) : SyncRequester {
    private val loop = AtomicReference<Job?>(null)

    override fun request() {
        loop.exchange(scope.launch { syncUntilSettled() })?.cancel()
    }

    private suspend fun syncUntilSettled() {
        var outcome = engine.value.syncNow()
        var backoff = FIRST_RETRY_DELAY
        repeat(MAX_RETRIES) {
            if (outcome != SyncOutcome.RetryLater) return
            delay(backoff)
            network.isOnline.first { it }
            outcome = engine.value.syncNow()
            backoff *= 2
        }
    }
}

private val FIRST_RETRY_DELAY = 30.seconds
private const val MAX_RETRIES = 5
