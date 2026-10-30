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
package dev.lscythe.app.jalvio.core.designsystem.component.toast

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

enum class ToastTone {
    Done,
    Info,
    Action,
    Error,
}

enum class ToastDuration(val value: Duration) {
    Default(4.seconds),
    Short(2200.milliseconds),
}

enum class ToastResult {
    Dismissed,
    ActionPerformed,
}

class ToastData
internal constructor(
    val message: String,
    val tone: ToastTone,
    val icon: ImageVector,
    val action: String?,
    val duration: ToastDuration,
) {
    internal val result = CompletableDeferred<ToastResult>()

    fun dismiss() {
        result.complete(ToastResult.Dismissed)
    }

    fun performAction() {
        result.complete(ToastResult.ActionPerformed)
    }
}

@Stable
class JalvioToastHostState {
    private val mutex = Mutex()

    var currentToast: ToastData? by mutableStateOf(null)
        private set

    suspend fun show(
        message: String,
        tone: ToastTone,
        icon: ImageVector,
        action: String? = null,
        duration: ToastDuration = ToastDuration.Default,
    ): ToastResult = mutex.withLock {
        val toast = ToastData(message, tone, icon, action, duration)
        try {
            currentToast = toast
            toast.result.await()
        } finally {
            currentToast = null
        }
    }
}
