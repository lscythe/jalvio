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
package dev.lscythe.app.jalvio.core.analytics.firebase

import dev.lscythe.app.jalvio.core.analytics.AnalyticsEvent
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class FirebaseParametersTest :
    BehaviorSpec({
        Given("an event within Firebase limits") {
            Then("it is passed through unchanged") {
                AnalyticsEvent("screen_view", listOf(AnalyticsEvent.Param("screen", "home")))
                    .toFirebaseParameters() shouldBe ("screen_view" to mapOf("screen" to "home"))
            }
        }
        Given("names and values over Firebase limits") {
            Then("names are 40 chars, values 100 chars, invalid characters replaced") {
                val (name, parameters) =
                    AnalyticsEvent(
                            "screen view!".padEnd(60, 'x'),
                            listOf(AnalyticsEvent.Param("route-id", "v".repeat(150))),
                        )
                        .toFirebaseParameters()
                name.length shouldBe 40
                name shouldStartWith "screen_view_"
                parameters.getValue("route_id").length shouldBe 100
            }
        }
        Given("a name that does not start with a letter") {
            Then("it gets a letter prefix") {
                AnalyticsEvent("1st_open").toFirebaseParameters().first shouldBe "jalvio_1st_open"
            }
        }
    })
