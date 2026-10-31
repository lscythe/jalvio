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

import arrow.core.left
import arrow.core.right
import dev.lscythe.app.jalvio.core.network.ApiBaseUrl
import dev.lscythe.app.jalvio.core.network.ApiError
import dev.lscythe.app.jalvio.core.network.Fetched
import dev.lscythe.app.jalvio.core.network.createJalvioHttpClient
import dev.lscythe.app.jalvio.core.network.problem.Problem
import dev.lscythe.app.jalvio.core.testing.readResource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.headersOf

private fun api(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData) =
    KtorCatalogApi(createJalvioHttpClient(MockEngine(handler), ApiBaseUrl("https://api.test/")))

private fun MockRequestHandleScope.json(body: String) =
    respond(body, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))

private fun Fetched<RegionList>.regions(): List<RegionLinks> =
    (this as Fetched.Changed<RegionList>).body.regions

class KtorCatalogApiTest :
    BehaviorSpec({
        Given("the region list") {
            Then("it is requested from v1/regions") {
                var requested: Url? = null
                api {
                        requested = it.url
                        json(readResource("catalog/regions.json"))
                    }
                    .regions(null)
                requested.toString() shouldBe "https://api.test/v1/regions"
            }
            Then("relative links resolve against the response URL and absolute ones stay") {
                val jakarta =
                    api { json(readResource("catalog/regions.json")) }
                        .regions(null)
                        .getOrNull()!!
                        .regions()
                        .first()
                jakarta.catalogUrl.toString() shouldBe "https://api.test/v1/regions/jakarta/catalog"
                jakarta.shapesUrl.toString() shouldBe
                    "https://api.test/v1/regions/jakarta/catalog/shapes"
                jakarta.mapUrl.toString() shouldBe "https://maps.example.test/jakarta.pmtiles"
            }
            Then("after a redirect, relative links resolve against the final URL") {
                val jakarta =
                    api { request ->
                            if (request.url.host == "api.test") {
                                respond(
                                    "",
                                    HttpStatusCode.Found,
                                    headersOf(HttpHeaders.Location, "https://edge.test/v1/regions"),
                                )
                            } else {
                                json(readResource("catalog/regions.json"))
                            }
                        }
                        .regions(null)
                        .getOrNull()!!
                        .regions()
                        .first()
                jakarta.catalogUrl.toString() shouldBe
                    "https://edge.test/v1/regions/jakarta/catalog"
            }
            Then("the catalog version keeps its weak ETag exactly") {
                api { json(readResource("catalog/regions.json")) }
                    .regions(null)
                    .getOrNull()!!
                    .regions()
                    .map { it.catalogVersion } shouldContainExactly
                    listOf("W/\"3f2a\"", "W/\"9b1c\"")
            }
            Then("a region without a map has no map URL") {
                api { json(readResource("catalog/regions.json")) }
                    .regions(null)
                    .getOrNull()!!
                    .regions()
                    .last()
                    .mapUrl shouldBe null
            }
            Then("a 304 is NotModified") {
                api {
                        respond(
                            "",
                            HttpStatusCode.NotModified,
                            headersOf(HttpHeaders.ETag, "W/\"r1\""),
                        )
                    }
                    .regions("W/\"r1\"") shouldBe Fetched.NotModified("W/\"r1\"").right()
            }
        }

        Given("a region's catalog") {
            val catalogUrl = Url("https://api.test/v1/regions/jakarta/catalog")

            Then("it is fetched from the given URL with the stored ETag") {
                var requested: Url? = null
                var ifNoneMatch: String? = null
                api {
                        requested = it.url
                        ifNoneMatch = it.headers[HttpHeaders.IfNoneMatch]
                        json(readResource("catalog/catalog.json"))
                    }
                    .catalog(catalogUrl, "W/\"abc\"")
                    .getOrNull()
                    .shouldBeInstanceOf<Fetched.Changed<*>>()
                requested shouldBe catalogUrl
                ifNoneMatch shouldBe "W/\"abc\""
            }
            Then("an unknown region is an Http 404 with its problem") {
                api {
                        respond(
                            """{"title":"Unknown region","status":404}""",
                            HttpStatusCode.NotFound,
                            headersOf(HttpHeaders.ContentType, "application/problem+json"),
                        )
                    }
                    .catalog(catalogUrl, null) shouldBe
                    ApiError.Http(404, Problem(title = "Unknown region", status = 404)).left()
            }
        }

        Given("a region's shapes") {
            Then("they are fetched from an absolute URL on another host") {
                var requested: Url? = null
                api {
                        requested = it.url
                        json(readResource("catalog/shapes.json"))
                    }
                    .shapes(Url("https://cdn.test/jakarta/shapes.json"), null)
                requested.toString() shouldBe "https://cdn.test/jakarta/shapes.json"
            }
        }
    })
