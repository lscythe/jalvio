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
package dev.lscythe.app.jalvio.core.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Composable
actual fun rememberPermissionRequester(): PermissionRequester {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val results = remember { MutableSharedFlow<Map<String, Boolean>>(extraBufferCapacity = 1) }
    val launcher =
        rememberLauncherForActivityResult(RequestMultiplePermissions()) { results.tryEmit(it) }
    return remember(context, activity, launcher) {
        AndroidPermissionRequester(context, activity, results, launch = { launcher.launch(it) })
    }
}

internal class AndroidPermissionRequester(
    private val context: Context,
    private val activity: Activity?,
    private val results: MutableSharedFlow<Map<String, Boolean>>,
    private val launch: (Array<String>) -> Unit,
) : PermissionRequester {
    private val requestLock = Mutex()

    override suspend fun status(permission: AppPermission): PermissionStatus =
        permissionStatus(
            androidPermissionsFor(permission, SDK_INT),
            isGranted = ::isGranted,
            shouldShowRationale = { activity?.shouldShowRequestPermissionRationale(it) == true },
        )

    override suspend fun request(permission: AppPermission): PermissionStatus {
        val required = androidPermissionsFor(permission, SDK_INT)
        if (required.isEmpty() || required.any(::isGranted)) return PermissionStatus.Granted
        return requestLock.withLock {
            val result = results.onSubscription { launch(required.toTypedArray()) }.first()
            requestResultStatus(result)
        }
    }

    private fun isGranted(permission: String): Boolean =
        context.checkSelfPermission(permission) == PERMISSION_GRANTED
}
