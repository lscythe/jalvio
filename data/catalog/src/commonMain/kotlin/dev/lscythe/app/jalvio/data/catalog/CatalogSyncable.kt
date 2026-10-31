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
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.sync.SyncOutcome
import dev.lscythe.app.jalvio.core.sync.Syncable
import dev.lscythe.app.jalvio.domain.catalog.CatalogError
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

internal data class CatalogSyncFailure(val error: CatalogError, val cause: ApiError?)

@Inject
@ContributesIntoSet(AppScope::class)
internal class CatalogSyncable(private val runner: CatalogSyncRunner) : Syncable {
    override val name: String = "catalog"

    override suspend fun sync(): SyncOutcome = runner.run().toSyncOutcome()
}

internal fun ApiError.toCatalogError(): CatalogError =
    when (this) {
        ApiError.Unreachable -> CatalogError.Offline
        is ApiError.Http if status == 503 -> CatalogError.NotReady
        is ApiError.Http if status == 404 -> CatalogError.RegionGone
        else -> CatalogError.Failed
    }

internal fun Either<CatalogSyncFailure, Unit>.toSyncOutcome(): SyncOutcome =
    fold(
        ifLeft = { failure ->
            when (failure.error) {
                CatalogError.Offline,
                CatalogError.NotReady,
                CatalogError.RegionGone -> SyncOutcome.RetryLater
                CatalogError.Failed -> SyncOutcome.Failed(failure.cause.describe())
            }
        },
        ifRight = { SyncOutcome.Done },
    )

private fun ApiError?.describe(): String =
    when (this) {
        null -> "catalog failed"
        ApiError.Unreachable -> "Unreachable"
        ApiError.Malformed -> "Malformed response"
        is ApiError.Http -> listOfNotNull("HTTP $status", problem?.title).joinToString(": ")
    }
