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

class SpringHeadlineTest :
    BehaviorSpec({
        Given("the headline spring") {
            Then("it starts thin and wide") { headlineAxes(0f) shouldBe HeadlineAxes(200, 100f) }
            Then("it ends heavy and condensed") {
                headlineAxes(1f) shouldBe HeadlineAxes(850, 28f)
            }
            Then("an overshoot stays at the resting axes") {
                headlineAxes(1.05f) shouldBe HeadlineAxes(850, 28f)
            }
            Then("weights snap to steps of 50 and widths to six stops") {
                headlineAxes(0.5f) shouldBe HeadlineAxes(550, 70f)
            }
        }
        Given("a headline whose longest word is wider than the screen at full size") {
            val fitsUpTo = 61f
            Then("it shrinks in 2 sp steps until the word fits") {
                fittedHeadlineSize(76f, 28f) { it <= fitsUpTo } shouldBe 60f
            }
            Then("it keeps the full size when the word already fits") {
                fittedHeadlineSize(52f, 28f) { it <= fitsUpTo } shouldBe 52f
            }
            Then("it stops at the minimum size when nothing fits") {
                fittedHeadlineSize(76f, 28f) { false } shouldBe 28f
            }
        }
    })
