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

import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.darwin.NSObject

private const val MIN_DISTANCE_METERS = 10.0

@Inject
@ContributesBinding(AppScope::class)
internal class CoreLocationLocationProvider : LocationProvider {
    override val locations: Flow<GeoPoint> = callbackFlow {
        val manager = CLLocationManager()
        val delegate = LocationDelegate { trySend(it) }
        manager.delegate = delegate
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.distanceFilter = MIN_DISTANCE_METERS
        manager.startUpdatingLocation()
        awaitClose {
            manager.stopUpdatingLocation()
            manager.delegate = null
        }
    }
        .flowOn(Dispatchers.Main)
        .conflate()

    override suspend fun lastKnown(): GeoPoint? =
        withContext(Dispatchers.Main) { CLLocationManager().location?.toGeoPoint() }
}

private class LocationDelegate(private val onLocation: (GeoPoint) -> Unit) :
    NSObject(), CLLocationManagerDelegateProtocol {
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        (didUpdateLocations.lastOrNull() as? CLLocation)?.toGeoPoint()?.let(onLocation)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun CLLocation.toGeoPoint(): GeoPoint = coordinate.useContents {
    GeoPoint(latitude, longitude)
}
