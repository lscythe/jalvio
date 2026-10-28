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

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.request.get
import io.ktor.http.Url

class JalvioHttpClientTest :
    BehaviorSpec({
        Given("a base URL") {
            Then("relative paths resolve under it") {
                var seen: Url? = null
                val client =
                    createJalvioHttpClient(
                        MockEngine {
                            seen = it.url
                            respondOk()
                        },
                        ApiBaseUrl("https://api.example.test/"),
                    )
                client.get("v1/catalog")
                seen.toString() shouldBe "https://api.example.test/v1/catalog"
            }
        }
        Given("an invalid base URL") {
            Then("construction fails") {
                shouldThrow<IllegalArgumentException> { ApiBaseUrl("https://api.example.test/v1") }
                shouldThrow<IllegalArgumentException> { ApiBaseUrl("api.example.test/") }
            }
        }
    })
