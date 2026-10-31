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
package dev.lscythe.app.jalvio.api.catalog

import arrow.core.Either
import dev.lscythe.app.jalvio.api.catalog.dto.CatalogDto
import dev.lscythe.app.jalvio.api.catalog.dto.RegionDto
import dev.lscythe.app.jalvio.api.catalog.dto.RegionListDto
import dev.lscythe.app.jalvio.api.catalog.dto.ShapesDto
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.Fetched
import dev.lscythe.app.jalvio.core.network.fetchCached
import dev.lscythe.app.jalvio.core.network.fetchCachedWithUrl
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.takeFrom

@Inject
@ContributesBinding(AppScope::class)
internal class KtorCatalogApi(private val client: HttpClient) : CatalogApi {
    override suspend fun regions(etag: String?): Either<ApiError, Fetched<RegionList>> =
        client
            .fetchCachedWithUrl<RegionListDto>(etag) { url(REGIONS_PATH) }
            .map { (fetched, responseUrl) ->
                when (fetched) {
                    is Fetched.NotModified -> fetched
                    is Fetched.Changed ->
                        Fetched.Changed(
                            RegionList(fetched.body.regions.map { it.withLinksFrom(responseUrl) }),
                            fetched.etag,
                        )
                }
            }

    override suspend fun catalog(url: Url, etag: String?): Either<ApiError, Fetched<CatalogDto>> =
        client.fetchCached(etag) { url(url) }

    override suspend fun shapes(url: Url, etag: String?): Either<ApiError, Fetched<ShapesDto>> =
        client.fetchCached(etag) { url(url) }
}

private fun RegionDto.withLinksFrom(base: Url) =
    RegionLinks(
        region = this,
        catalogUrl = base.resolve(catalog.url),
        catalogVersion = catalog.version,
        shapesUrl = base.resolve(shapes.url),
        mapUrl = map?.let { base.resolve(it.url) },
    )

private fun Url.resolve(link: String): Url = URLBuilder(this).takeFrom(link).build()

private const val REGIONS_PATH = "v1/regions"
