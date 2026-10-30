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

import dev.lscythe.app.jalvio.core.monitoring.Monitor
import dev.lscythe.app.jalvio.core.network.monitor.NetworkMonitor
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class GatedSyncable(override val name: String, private val outcome: () -> SyncOutcome) :
    Syncable {
    val entered = CompletableDeferred<Unit>()
    val gate = CompletableDeferred<Unit>()
    var runs = 0
        private set

    override suspend fun sync(): SyncOutcome {
        runs++
        entered.complete(Unit)
        gate.await()
        return outcome()
    }
}

internal class ImmediateSyncable(
    override val name: String,
    private val outcome: () -> SyncOutcome,
) : Syncable {
    var runs = 0
        private set

    override suspend fun sync(): SyncOutcome {
        runs++
        return outcome()
    }
}

internal class RecordingMonitor : Monitor {
    val errors = mutableListOf<Throwable>()

    override fun recordError(error: Throwable, message: String?) {
        errors += error
    }

    override fun breadcrumb(message: String) = Unit
}

internal class FakeNetworkMonitor(online: Boolean) : NetworkMonitor {
    val online = MutableStateFlow(online)
    override val isOnline: Flow<Boolean> = this.online
}

internal class ScriptedSyncEngine(vararg outcomes: SyncOutcome) : SyncEngine {
    private val queue = ArrayDeque(outcomes.toList())
    val runTimes = mutableListOf<Long>()
    var clock: () -> Long = { 0 }
    override val isSyncing = MutableStateFlow(false)

    override fun requestSync() = Unit

    override suspend fun syncNow(): SyncOutcome {
        runTimes += clock()
        return queue.removeFirstOrNull() ?: SyncOutcome.Done
    }
}

internal object NoRequester : SyncRequester {
    override fun request() = Unit
}
