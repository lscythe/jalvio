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

import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldNotBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class WorkManagerSyncRequesterTest {
    private val context = RuntimeEnvironment.getApplication()
    private val workManager
        get() = WorkManager.getInstance(context)

    @Before
    fun initializeWorkManager() {
        WorkManagerTestInitHelper.initializeTestWorkManager(
            context,
            Configuration.Builder().setExecutor(SynchronousExecutor()).build(),
        )
    }

    @Test
    fun aNewRequestReplacesOneThatIsStillWaiting() {
        val requester = WorkManagerSyncRequester(context)
        requester.request()
        val waiting = workManager.getWorkInfosForUniqueWork("sync").get().single().id

        requester.request()

        val enqueued =
            workManager.getWorkInfosForUniqueWork("sync").get().filter {
                it.state == WorkInfo.State.ENQUEUED
            }
        enqueued.map { it.id } shouldContainExactly listOf(enqueued.single().id)
        enqueued.single().id shouldNotBe waiting
    }
}
