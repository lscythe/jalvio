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
package dev.lscythe.app.jalvio.convention.endpoints

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class EndpointsConfigTest :
    BehaviorSpec({
        val file =
            mapOf(
                "api.baseUrl.debug" to "https://api-nonprod.jalvio.app/",
                "api.baseUrl.release" to "https://api.jalvio.app/",
                "map.styleUrl.debug" to "https://demotiles.maplibre.org/style.json",
                "map.styleUrl.release" to "https://demotiles.maplibre.org/style.json",
            )

        Given("a complete file") {
            Then("values resolve") {
                resolveEndpoints(file, emptyMap()).release.apiBaseUrl shouldBe
                    "https://api.jalvio.app/"
            }
        }
        Given("a Gradle property override") {
            Then("it wins") {
                resolveEndpoints(file, mapOf("jalvio.api.baseUrl.debug" to "http://10.0.2.2:8080/"))
                    .debug
                    .apiBaseUrl shouldBe "http://10.0.2.2:8080/"
            }
        }
        Given("a missing key") {
            Then("it fails naming the key") {
                shouldThrow<IllegalArgumentException> {
                        resolveEndpoints(file - "map.styleUrl.release", emptyMap())
                    }
                    .message shouldContain "map.styleUrl.release"
            }
        }
        Given("a release URL over http") {
            Then("it fails") {
                shouldThrow<IllegalArgumentException> {
                        resolveEndpoints(
                            file + ("api.baseUrl.release" to "http://api.jalvio.app/"),
                            emptyMap(),
                        )
                    }
                    .message shouldContain "api.baseUrl.release"
            }
        }
        Given("a base URL without trailing slash") {
            Then("it fails") {
                shouldThrow<IllegalArgumentException> {
                        resolveEndpoints(
                            file + ("api.baseUrl.debug" to "https://api-nonprod.jalvio.app/v1"),
                            emptyMap(),
                        )
                    }
                    .message shouldContain "trailing slash"
            }
        }
        Given("rendering") {
            Then("the object is internal and contains both builds") {
                val source =
                    renderEndpointsSource(
                        "dev.lscythe.app.jalvio.config",
                        resolveEndpoints(file, emptyMap()),
                    )
                source shouldContain "internal object JalvioEndpoints"
                source shouldContain "apiBaseUrl = \"https://api.jalvio.app/\""
            }
        }
        Given("a value containing a dollar sign, quote and backslash") {
            Then("they are escaped in the generated source") {
                val source =
                    renderEndpointsSource(
                        "dev.lscythe.app.jalvio.config",
                        resolveEndpoints(
                            file,
                            mapOf("jalvio.map.styleUrl.debug" to "https://x.app/\${a}\"b\\c/"),
                        ),
                    )
                source shouldContain "mapStyleUrl = \"https://x.app/\\\${a}\\\"b\\\\c/\""
            }
        }
    })
