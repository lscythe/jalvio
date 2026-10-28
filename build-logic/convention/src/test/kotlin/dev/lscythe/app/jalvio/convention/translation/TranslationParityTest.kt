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
package dev.lscythe.app.jalvio.convention.translation

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class TranslationParityTest :
    BehaviorSpec({
        val english =
            """<resources><string name="a">Hi %1${'$'}s</string><string name="b">B</string></resources>"""

        Given("matching files") {
            Then("the report is clean") {
                translationParity(
                        english,
                        """<resources><string name="a">Halo %1${'$'}s</string><string name="b">B</string></resources>""",
                    )
                    .isClean
                    .shouldBeTrue()
            }
        }

        Given("a missing and an extra key") {
            Then("both are reported") {
                val report =
                    translationParity(
                        english,
                        """<resources><string name="a">Halo %1${'$'}s</string><string name="c">C</string></resources>""",
                    )
                report.missing shouldBe setOf("b")
                report.extra shouldBe setOf("c")
                report.isClean.shouldBeFalse()
            }
        }

        Given("a dropped placeholder") {
            Then("it is a mismatch") {
                translationParity(
                        english,
                        """<resources><string name="a">Halo</string><string name="b">B</string></resources>""",
                    )
                    .placeholderMismatches shouldBe setOf("a")
            }
        }

        Given("a key that is not translatable") {
            Then("the translation does not need it") {
                translationParity(
                        """<resources><string name="app_name" translatable="false">Jalvio</string></resources>""",
                        "<resources/>",
                    )
                    .isClean
                    .shouldBeTrue()
            }
        }

        Given("plurals with different quantities") {
            Then("they match when the placeholders do") {
                translationParity(
                        """<resources><plurals name="stops"><item quantity="one">%1${'$'}d stop</item><item quantity="other">%1${'$'}d stops</item></plurals></resources>""",
                        """<resources><plurals name="stops"><item quantity="other">%1${'$'}d halte</item></plurals></resources>""",
                    )
                    .isClean
                    .shouldBeTrue()
            }
        }

        Given("a string array") {
            Then("it counts as a key by name") {
                translationParity(
                        """<resources><string-array name="days"><item>Mon</item></string-array></resources>""",
                        "<resources/>",
                    )
                    .missing shouldBe setOf("days")
            }
        }

        Given("placeholders in a different order") {
            Then("they still match") {
                translationParity(
                        """<resources><string name="a">%1${'$'}s to %2${'$'}s</string></resources>""",
                        """<resources><string name="a">ke %2${'$'}s dari %1${'$'}s</string></resources>""",
                    )
                    .placeholderMismatches
                    .shouldBeEmpty()
            }
        }
    })
