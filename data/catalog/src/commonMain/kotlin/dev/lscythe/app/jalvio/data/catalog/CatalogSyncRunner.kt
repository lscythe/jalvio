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

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.lscythe.app.jalvio.api.catalog.CatalogApi
import dev.lscythe.app.jalvio.core.common.coroutines.ApplicationScope
import dev.lscythe.app.jalvio.core.location.LocationProvider
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.Fetched
import dev.lscythe.app.jalvio.data.catalog.db.CatalogDao
import dev.lscythe.app.jalvio.data.catalog.db.CatalogMetaEntity
import dev.lscythe.app.jalvio.data.catalog.db.RegionEntity
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.lscythe.app.jalvio.domain.catalog.RegionChoice
import dev.lscythe.app.jalvio.domain.catalog.chooseRegion
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.Url
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Clock
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

private typealias SyncResult = Either<CatalogSyncFailure, Unit>

@Inject
@SingleIn(AppScope::class)
internal class CatalogSyncRunner(
    private val api: CatalogApi,
    private val dao: CatalogDao,
    private val selection: RegionSelectionStore,
    private val location: LocationProvider,
    private val clock: Clock,
    @ApplicationScope private val scope: CoroutineScope,
) {
    private val state = MutableStateFlow<SyncRun>(SyncRun.Idle)
    private val work = Mutex()
    private val sharing = Mutex()
    private var shared: CompletableDeferred<SyncResult>? = null

    val run: StateFlow<SyncRun> = state.asStateFlow()

    suspend fun run(): SyncResult {
        val joined = sharing.withLock { shared }
        if (joined != null) return joined.awaitOrRunAgain()
        val mine = CompletableDeferred<SyncResult>()
        sharing.withLock { shared = mine }
        return try {
            exclusive { sync() }.also { mine.complete(it) }
        } catch (failure: Throwable) {
            mine.completeExceptionally(failure)
            throw failure
        } finally {
            withContext(NonCancellable) { sharing.withLock { if (shared === mine) shared = null } }
        }
    }

    suspend fun runFor(id: RegionId): SyncResult =
        scope
            .async {
                exclusive {
                    selection.select(id)
                    sync()
                }
            }
            .await()

    private suspend fun CompletableDeferred<SyncResult>.awaitOrRunAgain(): SyncResult =
        try {
            await()
        } catch (cancelled: CancellationException) {
            currentCoroutineContext().ensureActive()
            run()
        }

    private suspend fun exclusive(
        block: suspend () -> Either<CatalogSyncFailure, Synced>
    ): SyncResult = work.withLock {
        state.value = SyncRun.Running
        var finished: SyncRun = SyncRun.Idle
        try {
            val result = block()
            finished = result.fold(ifLeft = { SyncRun.Failed(it.error) }, ifRight = { it.toRun() })
            result.map {}
        } catch (failure: Throwable) {
            if (failure !is CancellationException) finished = SyncRun.Failed(CatalogError.Failed)
            throw failure
        } finally {
            state.value = finished
        }
    }

    private suspend fun sync(): Either<CatalogSyncFailure, Synced> = either {
        val (regionsEtag, regions) = refreshRegions()
        val meta = dao.metaOnce()
        val storedId = selection.selected.first()
        val riderLocation =
            if (regions.size > 1 && regions.none { it.id == storedId?.value }) location.lastKnown()
            else null
        when (val choice = chooseRegion(regions.map { it.toRegion() }, storedId, riderLocation)) {
            is RegionChoice.Chosen -> {
                selection.select(choice.region.id)
                download(regions.first { it.id == choice.region.id.value }, meta, regionsEtag)
                Synced.Stored
            }
            is RegionChoice.Ask -> {
                if (meta?.regionId != null && regions.none { it.id == meta.regionId })
                    dao.clearCatalog()
                Synced.Ask(choice.regions)
            }
            RegionChoice.NoneServed -> raise(CatalogSyncFailure(CatalogError.NotReady, null))
        }
    }

    private suspend fun Raise<CatalogSyncFailure>.refreshRegions():
        Pair<String?, List<RegionEntity>> {
        val cached = dao.regions()
        val storedEtag = dao.metaOnce()?.regionsEtag?.takeIf { cached.isNotEmpty() }
        return when (val fetched = bindApi(api.regions(storedEtag))) {
            is Fetched.NotModified -> storedEtag to cached
            is Fetched.Changed -> {
                val rows = fetched.body.regions.toRows()
                ensure(rows.isNotEmpty()) { CatalogSyncFailure(CatalogError.NotReady, null) }
                dao.replaceRegions(fetched.etag, rows)
                fetched.etag to rows
            }
        }
    }

    private suspend fun Raise<CatalogSyncFailure>.download(
        region: RegionEntity,
        meta: CatalogMetaEntity?,
        regionsEtag: String?,
    ) {
        val sameRegion = meta?.regionId == region.id
        if (sameRegion && meta.catalogEtag == region.catalogVersion) return
        if (!sameRegion && meta?.regionId != null) dao.clearCatalog()
        val stored = meta?.takeIf { sameRegion }
        val catalog =
            when (val fetched = bindApi(api.catalog(Url(region.catalogUrl), stored?.catalogEtag))) {
                is Fetched.NotModified -> {
                    stored?.let { dao.upsertMeta(it.copy(catalogEtag = region.catalogVersion)) }
                    return
                }
                is Fetched.Changed -> fetched.body
            }
        val shapes =
            if (stored?.shapesFeedVersion == catalog.feedVersion) {
                null
            } else {
                when (val fetched = bindApi(api.shapes(Url(region.shapesUrl), null))) {
                    is Fetched.NotModified -> null
                    is Fetched.Changed -> fetched.body
                }
            }
        dao.replaceCatalog(
            CatalogMetaEntity(
                regionsEtag = regionsEtag,
                regionId = region.id,
                catalogEtag = region.catalogVersion,
                feedVersion = catalog.feedVersion,
                facilitiesSyncedAt = catalog.facilitiesSyncedAt,
                shapesFeedVersion = shapes?.feedVersion ?: stored?.shapesFeedVersion,
                updatedAt = clock.now().toEpochMilliseconds(),
            ),
            catalog.toRows(),
            shapes?.toRows(),
        )
    }

    private fun <T> Raise<CatalogSyncFailure>.bindApi(result: Either<ApiError, T>): T =
        result.mapLeft { CatalogSyncFailure(it.toCatalogError(), it) }.bind()

    private sealed interface Synced {
        fun toRun(): SyncRun

        data object Stored : Synced {
            override fun toRun() = SyncRun.Idle
        }

        data class Ask(val regions: List<Region>) : Synced {
            override fun toRun() = SyncRun.NeedsChoice(regions)
        }
    }
}
