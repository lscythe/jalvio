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
package dev.lscythe.app.jalvio.core.network.problem

import dev.lscythe.app.jalvio.core.network.ApiBaseUrl
import dev.lscythe.app.jalvio.core.network.createJalvioHttpClient
import dev.lscythe.app.jalvio.core.testing.readResource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

private suspend fun response(
    body: String,
    status: HttpStatusCode,
    contentType: String,
): HttpResponse =
    createJalvioHttpClient(
            MockEngine {
                respond(body, status, headersOf(HttpHeaders.ContentType, contentType))
            },
            ApiBaseUrl("https://api.example.test/"),
        )
        .get("v1/anything")

class ProblemTest :
    BehaviorSpec({
        Given("a problem+json 429") {
            Then("it decodes") {
                response(
                        readResource("problem/rate-limited.json"),
                        HttpStatusCode.TooManyRequests,
                        "application/problem+json",
                    )
                    .problemOrNull() shouldBe
                    Problem(
                        type = "https://jalvio.app/problems/rate-limited",
                        title = "Too many requests",
                        status = 429,
                    )
            }
        }
        Given("extra problem members") {
            Then("they are ignored") {
                response(
                        readResource("problem/extra-members.json"),
                        HttpStatusCode.BadRequest,
                        "application/problem+json",
                    )
                    .problemOrNull()
                    ?.status shouldBe 400
            }
        }
        Given("an HTML 502 from the edge") {
            Then("problemOrNull is null") {
                response("<html>Bad gateway</html>", HttpStatusCode.BadGateway, "text/html")
                    .problemOrNull()
                    .shouldBeNull()
            }
        }
        Given("an empty or malformed problem body") {
            Then("problemOrNull is null") {
                response("", HttpStatusCode.ServiceUnavailable, "application/problem+json")
                    .problemOrNull()
                    .shouldBeNull()
                response("{not json", HttpStatusCode.ServiceUnavailable, "application/problem+json")
                    .problemOrNull()
                    .shouldBeNull()
            }
        }
    })
