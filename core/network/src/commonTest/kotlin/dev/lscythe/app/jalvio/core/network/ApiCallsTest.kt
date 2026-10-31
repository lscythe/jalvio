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

import arrow.core.left
import arrow.core.right
import dev.lscythe.app.jalvio.core.network.problem.Problem
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.path
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlinx.serialization.Serializable

@Serializable private data class Thing(val name: String)

private fun clientAnswering(
    handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
) = createJalvioHttpClient(MockEngine(handler), ApiBaseUrl("https://api.test/"))

private fun MockRequestHandleScope.json(
    body: String,
    status: HttpStatusCode = HttpStatusCode.OK,
    vararg extra: Pair<String, String>,
) =
    respond(
        body,
        status,
        headersOf(
            *(listOf(HttpHeaders.ContentType to "application/json") + extra)
                .map { (name, value) -> name to listOf(value) }
                .toTypedArray()
        ),
    )

class ApiCallsTest :
    BehaviorSpec({
        Given("apiCall") {
            Then("a 200 body decodes") {
                clientAnswering { json("""{"name":"Blok M"}""") }
                    .apiCall<Thing> { url { path("v1/thing") } } shouldBe Thing("Blok M").right()
            }
            Then("an IO failure is Unreachable") {
                clientAnswering { throw IOException("down") }.apiCall<Thing> {} shouldBe
                    ApiError.Unreachable.left()
            }
            Then("a timeout is Unreachable") {
                clientAnswering { throw HttpRequestTimeoutException("https://api.test/", 1_000) }
                    .apiCall<Thing> {} shouldBe ApiError.Unreachable.left()
            }
            Then("a broken body is Malformed") {
                clientAnswering { json("""{"broken"""") }.apiCall<Thing> {} shouldBe
                    ApiError.Malformed.left()
            }
            Then("a problem+json error keeps its status and problem") {
                clientAnswering {
                    respond(
                        """{"title":"Not ready","status":503}""",
                        HttpStatusCode.ServiceUnavailable,
                        headersOf(HttpHeaders.ContentType, "application/problem+json"),
                    )
                }
                    .apiCall<Thing> {} shouldBe
                    ApiError.Http(503, Problem(title = "Not ready", status = 503)).left()
            }
            Then("a plain error has no problem") {
                clientAnswering {
                    respond(
                        "oops",
                        HttpStatusCode.InternalServerError,
                        headersOf(HttpHeaders.ContentType, "text/plain"),
                    )
                }
                    .apiCall<Thing> {} shouldBe ApiError.Http(500, null).left()
            }
            Then("cancellation propagates instead of becoming an error") {
                val entered = CompletableDeferred<Unit>()
                val client = clientAnswering {
                    entered.complete(Unit)
                    awaitCancellation()
                }
                var result: Any? = null
                coroutineScope {
                    val call = launch { result = client.apiCall<Thing> {} }
                    entered.await()
                    call.cancelAndJoin()
                    call.isCancelled shouldBe true
                }
                result.shouldBeNull()
            }
            Then("gzip is accepted") {
                var acceptEncoding: String? = null
                clientAnswering {
                    acceptEncoding = it.headers[HttpHeaders.AcceptEncoding]
                    json("""{"name":"x"}""")
                }
                    .apiCall<Thing> {}
                acceptEncoding.orEmpty() shouldContain "gzip"
            }
        }
        Given("fetchCached") {
            Then("a stored ETag is sent as If-None-Match") {
                var sent: String? = "unset"
                clientAnswering {
                        sent = it.headers[HttpHeaders.IfNoneMatch]
                        json("""{"name":"x"}""")
                    }
                    .fetchCached<Thing>("W/\"abc\"") {}
                sent shouldBe "W/\"abc\""
            }
            Then("no stored ETag sends no If-None-Match") {
                var sent: String? = "unset"
                clientAnswering {
                        sent = it.headers[HttpHeaders.IfNoneMatch]
                        json("""{"name":"x"}""")
                    }
                    .fetchCached<Thing>(null) {}
                sent.shouldBeNull()
            }
            Then("a 304 is NotModified with the response ETag") {
                clientAnswering {
                        respond(
                            "",
                            HttpStatusCode.NotModified,
                            headersOf(HttpHeaders.ETag, "W/\"abc\""),
                        )
                    }
                    .fetchCached<Thing>("W/\"abc\"") {} shouldBe
                    Fetched.NotModified("W/\"abc\"").right()
            }
            Then("a 200 is Changed with the body and the new ETag") {
                clientAnswering {
                        json("""{"name":"x"}""", HttpStatusCode.OK, HttpHeaders.ETag to "W/\"def\"")
                    }
                    .fetchCached<Thing>("W/\"abc\"") {} shouldBe
                    Fetched.Changed(Thing("x"), "W/\"def\"").right()
            }
            Then("the final request URL comes back for resolving links") {
                clientAnswering { json("""{"name":"x"}""") }
                    .fetchCachedWithUrl<Thing>(null) { url { path("v1/regions") } }
                    .map { (_, url) -> url.toString() } shouldBe
                    "https://api.test/v1/regions".right()
            }
        }
    })
