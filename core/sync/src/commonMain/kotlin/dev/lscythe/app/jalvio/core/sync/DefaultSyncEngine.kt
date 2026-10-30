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

import arrow.core.raise.catch
import dev.lscythe.app.jalvio.core.common.coroutines.ApplicationScope
import dev.lscythe.app.jalvio.core.monitoring.Monitor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
internal class DefaultSyncEngine(
    private val syncables: Set<Syncable>,
    private val monitor: Monitor,
    private val requester: SyncRequester,
    @ApplicationScope private val scope: CoroutineScope,
) : SyncEngine {
    private val running = MutableStateFlow(false)
    private val runLock = Mutex()
    private var currentRun: Deferred<SyncOutcome>? = null

    override val isSyncing: StateFlow<Boolean> = running.asStateFlow()

    override fun requestSync() = requester.request()

    override suspend fun syncNow(): SyncOutcome =
        runLock
            .withLock {
                currentRun?.takeIf { it.isActive }
                    ?: scope.async { runAll() }.also { currentRun = it }
            }
            .await()

    private suspend fun runAll(): SyncOutcome {
        running.value = true
        try {
            val outcomes = supervisorScope {
                syncables.map { syncable -> async { syncable to syncable.syncSafely() } }.awaitAll()
            }
            outcomes.forEach { (syncable, outcome) ->
                if (outcome is SyncOutcome.Failed) {
                    monitor.recordError(SyncFailure(syncable.name, outcome.reason))
                }
            }
            return combineOutcomes(outcomes.map { (_, outcome) -> outcome })
        } finally {
            running.value = false
        }
    }

    private suspend fun Syncable.syncSafely(): SyncOutcome =
        catch({ sync() }) { failure ->
            SyncOutcome.Failed(failure.message ?: failure::class.simpleName ?: UNKNOWN_FAILURE)
        }
}

internal fun combineOutcomes(outcomes: List<SyncOutcome>): SyncOutcome =
    outcomes.firstOrNull { it == SyncOutcome.RetryLater }
        ?: outcomes.firstOrNull { it is SyncOutcome.Failed }
        ?: SyncOutcome.Done

private const val UNKNOWN_FAILURE = "unknown failure"
