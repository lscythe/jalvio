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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.coroutines.resume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter
import platform.darwin.NSObject

@Composable
actual fun rememberPermissionRequester(): PermissionRequester = remember {
    IosPermissionRequester()
}

internal class IosPermissionRequester : PermissionRequester {
    private val locationManager = CLLocationManager()

    override suspend fun status(permission: AppPermission): PermissionStatus =
        when (permission) {
            AppPermission.Location -> currentLocationStatus()
            AppPermission.Notifications -> currentNotificationStatus()
        }

    override suspend fun request(permission: AppPermission): PermissionStatus =
        when (permission) {
            AppPermission.Location -> requestLocation()
            AppPermission.Notifications -> requestNotifications()
        }

    private suspend fun currentLocationStatus(): PermissionStatus =
        withContext(Dispatchers.Main) {
            locationPermissionStatus(locationManager.authorizationStatus)
        }

    private suspend fun currentNotificationStatus(): PermissionStatus =
        suspendCancellableCoroutine { continuation ->
            UNUserNotificationCenter.currentNotificationCenter()
                .getNotificationSettingsWithCompletionHandler { settings ->
                    continuation.resume(
                        settings?.let { notificationPermissionStatus(it.authorizationStatus) }
                            ?: PermissionStatus.NotRequested
                    )
                }
        }

    private suspend fun requestLocation(): PermissionStatus {
        val current = currentLocationStatus()
        if (current != PermissionStatus.NotRequested) return current
        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine { continuation ->
                val delegate = AuthorizationDelegate { status ->
                    if (status != PermissionStatus.NotRequested && continuation.isActive) {
                        locationManager.delegate = null
                        continuation.resume(status)
                    }
                }
                locationManager.delegate = delegate
                continuation.invokeOnCancellation { locationManager.delegate = null }
                locationManager.requestWhenInUseAuthorization()
            }
        }
    }

    private suspend fun requestNotifications(): PermissionStatus =
        suspendCancellableCoroutine { continuation ->
            UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
                UNAuthorizationOptionAlert or
                    UNAuthorizationOptionBadge or
                    UNAuthorizationOptionSound
            ) { granted, _ ->
                continuation.resume(
                    if (granted) PermissionStatus.Granted else PermissionStatus.Denied
                )
            }
        }
}

private class AuthorizationDelegate(private val onChange: (PermissionStatus) -> Unit) :
    NSObject(), CLLocationManagerDelegateProtocol {
    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        onChange(locationPermissionStatus(manager.authorizationStatus))
    }
}
