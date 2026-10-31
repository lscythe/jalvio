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

import androidx.work.BackoffPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OutOfQuotaPolicy
import dev.lscythe.app.jalvio.core.sync.SyncOutcome
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.days
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class SyncRequestsTest {
    @Test
    fun oneTimeSyncIsExpeditedWithAFallbackOnAConnectedNetwork() {
        val spec = oneTimeSyncRequest().workSpec
        spec.expedited shouldBe true
        spec.outOfQuotaPolicy shouldBe OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST
        spec.constraints.requiredNetworkType shouldBe NetworkType.CONNECTED
        spec.backoffPolicy shouldBe BackoffPolicy.EXPONENTIAL
    }

    @Test
    @Config(sdk = [30])
    fun oneTimeSyncIsNotExpeditedBeforeAndroid12() {
        val spec = oneTimeSyncRequest().workSpec
        spec.expedited shouldBe false
        spec.constraints.requiredNetworkType shouldBe NetworkType.CONNECTED
    }

    @Test
    fun dailySyncRunsOnceADayOnAConnectedNetworkWithBattery() {
        val spec = dailySyncRequest().workSpec
        spec.isPeriodic shouldBe true
        spec.intervalDuration shouldBe 1.days.inWholeMilliseconds
        spec.constraints.requiredNetworkType shouldBe NetworkType.CONNECTED
        spec.constraints.requiresBatteryNotLow() shouldBe true
    }

    @Test
    fun outcomesBecomeWorkResults() {
        SyncOutcome.Done.toWorkResult() shouldBe ListenableWorker.Result.success()
        SyncOutcome.RetryLater.toWorkResult() shouldBe ListenableWorker.Result.retry()
        SyncOutcome.Failed("boom").toWorkResult() shouldBe ListenableWorker.Result.failure()
    }
}
