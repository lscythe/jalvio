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

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build.VERSION.SDK_INT
import android.os.Looper
import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

private const val UPDATE_INTERVAL_MILLIS = 5_000L
private const val MIN_DISTANCE_METERS = 10f

@SuppressLint("MissingPermission")
@Inject
@ContributesBinding(AppScope::class)
internal class LocationManagerLocationProvider(context: Context) : LocationProvider {
    private val locationManager =
        checkNotNull(context.getSystemService(LocationManager::class.java))

    override val locations: Flow<GeoPoint> = callbackFlow {
        val listener = LocationListener { location -> trySend(location.toGeoPoint()) }
        activeProviders().forEach { provider ->
            locationManager.requestLocationUpdates(
                provider,
                UPDATE_INTERVAL_MILLIS,
                MIN_DISTANCE_METERS,
                listener,
                Looper.getMainLooper(),
            )
        }
        awaitClose { locationManager.removeUpdates(listener) }
    }
        .conflate()

    override suspend fun lastKnown(): GeoPoint? =
        activeProviders()
            .mapNotNull(locationManager::getLastKnownLocation)
            .maxByOrNull(Location::getTime)
            ?.toGeoPoint()

    private fun activeProviders(): List<String> =
        locationProvidersFor(SDK_INT, locationManager.getProviders(true))

    private fun Location.toGeoPoint(): GeoPoint = GeoPoint(latitude, longitude)
}
