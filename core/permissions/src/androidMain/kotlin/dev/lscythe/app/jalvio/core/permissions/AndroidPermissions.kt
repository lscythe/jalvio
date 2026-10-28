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

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build.VERSION_CODES.TIRAMISU

internal fun androidPermissionsFor(permission: AppPermission, sdkInt: Int): List<String> =
    when (permission) {
        AppPermission.Location -> listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        AppPermission.Notifications ->
            if (sdkInt >= TIRAMISU) listOf(POST_NOTIFICATIONS) else emptyList()
    }

internal fun permissionStatus(
    required: List<String>,
    isGranted: (String) -> Boolean,
    shouldShowRationale: (String) -> Boolean,
): PermissionStatus =
    when {
        required.isEmpty() || required.any(isGranted) -> PermissionStatus.Granted
        required.any(shouldShowRationale) -> PermissionStatus.Denied
        else -> PermissionStatus.NotRequested
    }

internal fun requestResultStatus(result: Map<String, Boolean>): PermissionStatus =
    if (result.values.any { it }) PermissionStatus.Granted else PermissionStatus.Denied
