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

import android.os.Build
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import dev.lscythe.app.jalvio.core.sync.SyncOutcome
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

internal fun oneTimeSyncRequest(): OneTimeWorkRequest =
    OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(onConnectedNetwork().build())
        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, INITIAL_BACKOFF.toJavaDuration())
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            }
        }
        .build()

internal fun dailySyncRequest(): PeriodicWorkRequest =
    PeriodicWorkRequestBuilder<SyncWorker>(1.days.toJavaDuration())
        .setConstraints(onConnectedNetwork().setRequiresBatteryNotLow(true).build())
        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, INITIAL_BACKOFF.toJavaDuration())
        .build()

internal fun SyncOutcome.toWorkResult(): ListenableWorker.Result =
    when (this) {
        SyncOutcome.Done -> ListenableWorker.Result.success()
        SyncOutcome.RetryLater -> ListenableWorker.Result.retry()
        is SyncOutcome.Failed -> ListenableWorker.Result.failure()
    }

private fun onConnectedNetwork() =
    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)

private val INITIAL_BACKOFF = 30.seconds
