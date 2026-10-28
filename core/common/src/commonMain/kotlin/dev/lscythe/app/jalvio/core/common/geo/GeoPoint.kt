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
package dev.lscythe.app.jalvio.core.common.geo

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

private const val MEAN_EARTH_RADIUS_METERS = 6_371_008.8

data class GeoPoint(val lat: Double, val lng: Double)

fun GeoPoint.distanceMetersTo(other: GeoPoint): Double {
    val fromLat = lat.toRadians()
    val toLat = other.lat.toRadians()
    val deltaLat = toLat - fromLat
    val deltaLng = (other.lng - lng).toRadians()
    val halfChordSquared =
        sin(deltaLat / 2).squared() + cos(fromLat) * cos(toLat) * sin(deltaLng / 2).squared()
    return 2 * MEAN_EARTH_RADIUS_METERS * asin(min(1.0, sqrt(halfChordSquared)))
}

fun GeoPoint.isWithin(meters: Double, of: GeoPoint): Boolean = distanceMetersTo(of) <= meters

private fun Double.toRadians(): Double = this * (PI / 180.0)

private fun Double.squared(): Double = this * this
