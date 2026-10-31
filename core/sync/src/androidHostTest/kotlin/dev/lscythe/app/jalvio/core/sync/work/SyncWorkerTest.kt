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
package dev.lscythe.app.jalvio.core.sync.work

import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import dev.lscythe.app.jalvio.core.sync.ScriptedSyncEngine
import dev.lscythe.app.jalvio.core.sync.SyncOutcome
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SyncWorkerTest {
    private fun worker(engine: ScriptedSyncEngine) =
        TestListenableWorkerBuilder<SyncWorker>(RuntimeEnvironment.getApplication())
            .setWorkerFactory(SyncWorkerFactory(engine))
            .build()

    @Test
    fun theWorkerRunsTheEngineOnce() = runTest {
        val engine = ScriptedSyncEngine(SyncOutcome.Done)
        worker(engine).doWork() shouldBe ListenableWorker.Result.success()
        engine.runTimes.size shouldBe 1
    }

    @Test
    fun aSyncThatShouldRetryAsksWorkManagerToRetry() = runTest {
        worker(ScriptedSyncEngine(SyncOutcome.RetryLater)).doWork() shouldBe
            ListenableWorker.Result.retry()
    }
}
