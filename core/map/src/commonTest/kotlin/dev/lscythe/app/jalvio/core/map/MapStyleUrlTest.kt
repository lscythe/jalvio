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
package dev.lscythe.app.jalvio.core.map

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class MapStyleUrlTest :
    BehaviorSpec({
        Given("an absolute http or https style URL") {
            Then("it is accepted") {
                MapStyleUrl("https://demotiles.maplibre.org/style.json").value shouldBe
                    "https://demotiles.maplibre.org/style.json"
                MapStyleUrl("http://10.0.2.2:8080/style.json").value shouldBe
                    "http://10.0.2.2:8080/style.json"
            }
        }
        Given("a relative or blank URL") {
            Then("it is rejected") {
                shouldThrow<IllegalArgumentException> { MapStyleUrl("style.json") }
                shouldThrow<IllegalArgumentException> { MapStyleUrl(" ") }
                shouldThrow<IllegalArgumentException> { MapStyleUrl("https://") }
            }
        }
        Given("a non-http scheme") {
            Then("it is rejected") {
                shouldThrow<IllegalArgumentException> {
                    MapStyleUrl("ftp://tiles.example/style.json")
                }
            }
        }
    })
