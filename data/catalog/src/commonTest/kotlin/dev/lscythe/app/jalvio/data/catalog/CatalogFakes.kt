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
package dev.lscythe.app.jalvio.data.catalog

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import arrow.core.Either
import arrow.core.right
import dev.lscythe.app.jalvio.api.catalog.CatalogApi
import dev.lscythe.app.jalvio.api.catalog.RegionLinks
import dev.lscythe.app.jalvio.api.catalog.RegionList
import dev.lscythe.app.jalvio.api.catalog.dto.BoundsDto
import dev.lscythe.app.jalvio.api.catalog.dto.CatalogDto
import dev.lscythe.app.jalvio.api.catalog.dto.CatalogLinkDto
import dev.lscythe.app.jalvio.api.catalog.dto.PositionDto
import dev.lscythe.app.jalvio.api.catalog.dto.RegionDto
import dev.lscythe.app.jalvio.api.catalog.dto.ShapeDto
import dev.lscythe.app.jalvio.api.catalog.dto.ShapesDto
import dev.lscythe.app.jalvio.api.catalog.dto.ShapesLinkDto
import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.lscythe.app.jalvio.core.location.LocationProvider
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.Fetched
import dev.lscythe.app.jalvio.core.network.JalvioJson
import dev.lscythe.app.jalvio.core.network.monitor.NetworkMonitor
import dev.lscythe.app.jalvio.core.sync.SyncEngine
import dev.lscythe.app.jalvio.core.sync.SyncOutcome
import dev.lscythe.app.jalvio.core.testing.TestClock
import dev.lscythe.app.jalvio.core.testing.readResource
import dev.lscythe.app.jalvio.data.catalog.db.inMemoryCatalogDatabase
import io.ktor.http.Url
import kotlin.time.Instant
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

internal val catalogFixture: CatalogDto =
    JalvioJson.decodeFromString(readResource("catalog/catalog.json"))

internal val fixtureShapes = ShapesDto("FEED_VERSION", listOf(ShapeDto("1-R01", "abc")))

internal val inBandung = GeoPoint(-6.91, 107.61)
internal val inTheSea = GeoPoint(-5.0, 110.0)

internal fun regionLinks(
    id: String,
    version: String = "W/\"abc\"",
    southWest: PositionDto = PositionDto(-6.45, 106.6),
    northEast: PositionDto = PositionDto(-6.05, 107.05),
) =
    RegionLinks(
        region =
            RegionDto(
                id = id,
                name = id.replaceFirstChar { it.uppercase() },
                timeZone = "Asia/Jakarta",
                bounds = BoundsDto(southWest, northEast),
                live = true,
                catalog = CatalogLinkDto("/v1/regions/$id/catalog", version),
                shapes = ShapesLinkDto("/v1/regions/$id/catalog/shapes"),
            ),
        catalogUrl = Url("https://api.test/v1/regions/$id/catalog"),
        catalogVersion = version,
        shapesUrl = Url("https://api.test/v1/regions/$id/catalog/shapes"),
        mapUrl = null,
    )

internal val jakartaLinks = regionLinks("jakarta")
internal val bandungLinks =
    regionLinks("bandung", "W/\"bdg\"", PositionDto(-7.05, 107.5), PositionDto(-6.8, 107.75))

internal fun regionList(vararg regions: RegionLinks, etag: String = "W/\"r1\"") =
    Fetched.Changed(RegionList(regions.toList()), etag).right()

internal class FakeCatalogApi : CatalogApi {
    var regions: Either<ApiError, Fetched<RegionList>> = regionList(jakartaLinks)
    var catalog: Either<ApiError, Fetched<CatalogDto>> =
        Fetched.Changed(catalogFixture, "W/\"abc\"").right()
    var shapes: Either<ApiError, Fetched<ShapesDto>> = Fetched.Changed(fixtureShapes, null).right()
    var regionsGate: CompletableDeferred<Unit>? = null
    var catalogGate: CompletableDeferred<Unit>? = null
    val catalogStarted = CompletableDeferred<Unit>()

    val regionsRequests = mutableListOf<String?>()
    val catalogRequests = mutableListOf<Url>()
    val shapesRequests = mutableListOf<Url>()

    override suspend fun regions(etag: String?): Either<ApiError, Fetched<RegionList>> {
        regionsRequests += etag
        regionsGate?.await()
        return regions
    }

    override suspend fun catalog(url: Url, etag: String?): Either<ApiError, Fetched<CatalogDto>> {
        catalogRequests += url
        catalogStarted.complete(Unit)
        catalogGate?.await()
        return catalog
    }

    override suspend fun shapes(url: Url, etag: String?): Either<ApiError, Fetched<ShapesDto>> {
        shapesRequests += url
        return shapes
    }
}

internal class FakeLocationProvider(var last: GeoPoint? = null) : LocationProvider {
    override val locations: Flow<GeoPoint> = emptyFlow()

    override suspend fun lastKnown(): GeoPoint? = last
}

internal class FakeSyncEngine : SyncEngine {
    var requests = 0

    override val isSyncing: StateFlow<Boolean> = MutableStateFlow(false)

    override fun requestSync() {
        requests++
    }

    override suspend fun syncNow(): SyncOutcome = SyncOutcome.Done
}

internal class InMemoryPreferences : DataStore<Preferences> {
    private val state = MutableStateFlow(emptyPreferences())

    override val data: Flow<Preferences> = state

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences =
        transform(state.value).also { state.value = it }
}

internal val syncStartedAt: Instant = Instant.parse("2026-10-30T03:00:00Z")

internal class FakeNetworkMonitor(online: Boolean = true) : NetworkMonitor {
    val online = MutableStateFlow(online)

    override val isOnline: Flow<Boolean> = this.online
}

internal class CatalogHarness(lastKnown: GeoPoint? = null) {
    val api = FakeCatalogApi()
    val dao = inMemoryCatalogDatabase().catalogDao()
    val selection = RegionSelectionStore(InMemoryPreferences())
    val location = FakeLocationProvider(lastKnown)
    val network = FakeNetworkMonitor()
    val clock = TestClock(syncStartedAt)
    val applicationScope = CoroutineScope(SupervisorJob())
    val runner = CatalogSyncRunner(api, dao, selection, location, clock, applicationScope)
}
