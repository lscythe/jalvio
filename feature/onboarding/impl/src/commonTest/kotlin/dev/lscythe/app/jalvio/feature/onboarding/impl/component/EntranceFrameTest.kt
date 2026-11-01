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
package dev.lscythe.app.jalvio.feature.onboarding.impl.component

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class EntranceFrameTest :
    BehaviorSpec({
        EntranceKind.entries.forEach { kind ->
            Given("a $kind entrance") {
                Then("it starts invisible") { entranceFrame(kind, 0f).alpha shouldBe 0f }
                Then("it is half visible halfway") { entranceFrame(kind, 0.5f).alpha shouldBe 0.5f }
                Then("it ends fully visible") { entranceFrame(kind, 1f).alpha shouldBe 1f }
                Then("a spring overshoot stays fully visible") {
                    entranceFrame(kind, 1.08f).alpha shouldBe 1f
                }
            }
        }
        Given("an Up entrance") {
            Then("it starts a full rise below its place") {
                entranceFrame(EntranceKind.Up, 0f).rise shouldBe 1f
            }
            Then("it rests in place") { entranceFrame(EntranceKind.Up, 1f).rise shouldBe 0f }
        }
        Given("a Grow entrance") {
            Then("it starts at no size, turned back") {
                entranceFrame(EntranceKind.Grow, 0f) shouldBe
                    EntranceFrame(alpha = 0f, scale = 0f, rotation = -60f)
            }
            Then("it rests at full size, upright") {
                entranceFrame(EntranceKind.Grow, 1f) shouldBe EntranceFrame(alpha = 1f)
            }
        }
        Given("a Settle entrance") {
            Then("it starts slightly small") {
                entranceFrame(EntranceKind.Settle, 0f).scale shouldBe 0.86f
            }
        }
    })
