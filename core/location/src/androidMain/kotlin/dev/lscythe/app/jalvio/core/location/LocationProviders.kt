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
package dev.lscythe.app.jalvio.core.location

import android.location.LocationManager.FUSED_PROVIDER
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Build.VERSION_CODES.S

internal fun locationProvidersFor(sdkInt: Int, enabled: Collection<String>): List<String> =
    if (sdkInt >= S && FUSED_PROVIDER in enabled) listOf(FUSED_PROVIDER)
    else listOf(GPS_PROVIDER, NETWORK_PROVIDER).filter { it in enabled }
