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
package dev.lscythe.app.jalvio.core.testing

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

class TestClockTest :
    BehaviorSpec({
        Given("a test clock") {
            Then("advancing moves now") {
                val clock = TestClock(Instant.parse("2026-10-27T00:00:00Z"))
                clock.advanceBy(90.seconds)
                clock.now() shouldBe Instant.parse("2026-10-27T00:01:30Z")
            }
        }
        Given("a test resource") {
            Then("it is read as text") {
                readResource("sample.json") shouldContain "\"ok\": true"
            }
        }
    })
