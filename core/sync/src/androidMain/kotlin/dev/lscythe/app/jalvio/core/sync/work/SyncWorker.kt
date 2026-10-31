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

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.lscythe.app.jalvio.core.sync.SyncEngine

class SyncWorker(context: Context, params: WorkerParameters, private val engine: SyncEngine) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = engine.syncNow().toWorkResult()
}
