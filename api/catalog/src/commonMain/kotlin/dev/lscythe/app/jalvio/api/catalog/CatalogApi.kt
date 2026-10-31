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
import dev.lscythe.app.jalvio.api.catalog.dto.ShapesDto
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.Fetched
import io.ktor.http.Url

interface CatalogApi {
    suspend fun regions(etag: String?): Either<ApiError, Fetched<RegionList>>

    suspend fun catalog(url: Url, etag: String?): Either<ApiError, Fetched<CatalogDto>>

    suspend fun shapes(url: Url, etag: String?): Either<ApiError, Fetched<ShapesDto>>
}

data class RegionList(val regions: List<RegionLinks>)

data class RegionLinks(
    val region: RegionDto,
    val catalogUrl: Url,
    val catalogVersion: String,
    val shapesUrl: Url,
    val mapUrl: Url?,
)
