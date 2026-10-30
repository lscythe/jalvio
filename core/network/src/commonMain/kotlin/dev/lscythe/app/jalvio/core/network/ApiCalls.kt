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
package dev.lscythe.app.jalvio.core.network

import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import dev.lscythe.app.jalvio.core.network.problem.problemOrNull
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.apiCall(
    crossinline request: HttpRequestBuilder.() -> Unit
): Either<ApiError, T> = exchange({ request() }) { response -> response.body<T>() }

suspend inline fun <reified T> HttpClient.fetchCached(
    etag: String?,
    crossinline request: HttpRequestBuilder.() -> Unit,
): Either<ApiError, Fetched<T>> =
    fetchCachedWithUrl<T>(etag, request).map { (fetched, _) -> fetched }

suspend inline fun <reified T> HttpClient.fetchCachedWithUrl(
    etag: String?,
    crossinline request: HttpRequestBuilder.() -> Unit,
): Either<ApiError, Pair<Fetched<T>, Url>> =
    exchange({
        etag?.let { header(HttpHeaders.IfNoneMatch, it) }
        request()
    }) { response ->
        val responseEtag = response.headers[HttpHeaders.ETag]
        val fetched =
            if (response.status == HttpStatusCode.NotModified) Fetched.NotModified(responseEtag)
            else Fetched.Changed(response.body<T>(), responseEtag)
        fetched to response.request.url
    }

@PublishedApi
internal suspend fun <T> HttpClient.exchange(
    request: HttpRequestBuilder.() -> Unit,
    read: suspend (HttpResponse) -> T,
): Either<ApiError, T> = either {
    catch({
        val response = request(request)
        if (!response.status.isSuccess() && response.status != HttpStatusCode.NotModified) {
            raise(ApiError.Http(response.status.value, response.problemOrNull()))
        }
        read(response)
    }) { failure ->
        raise(
            when (failure) {
                is SerializationException,
                is ContentConvertException,
                is NoTransformationFoundException -> ApiError.Malformed
                is IOException -> ApiError.Unreachable
                else -> throw failure
            }
        )
    }
}
