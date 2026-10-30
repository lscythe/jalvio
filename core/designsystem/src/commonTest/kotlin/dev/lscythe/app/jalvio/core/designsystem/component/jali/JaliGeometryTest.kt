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
package dev.lscythe.app.jalvio.core.designsystem.component.jali

import androidx.compose.ui.geometry.Rect
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import kotlin.math.hypot

class JaliGeometryTest :
    BehaviorSpec({
        Given("the body box") {
            Then("an unsquashed body is 80 by 80 on the 90 baseline") {
                jaliBody(squash = 0f) shouldBe Rect(10f, 10f, 90f, 90f)
            }

            Then("a fully squashed body is 96 wide and 58 tall") {
                val body = jaliBody(squash = 1f)
                body.left shouldBe (2f plusOrMinus 0.001f)
                body.top shouldBe (32f plusOrMinus 0.001f)
                body.right shouldBe (98f plusOrMinus 0.001f)
                body.bottom shouldBe 90f
            }
        }

        Given("the wavy outline") {
            Then("it has 96 points") { jaliOutline(JaliMood.Calm.spec.outline) shouldHaveSize 96 }

            Then("calm starts at the top of the box, on a crest") {
                val first = jaliOutline(JaliMood.Calm.spec.outline).first()
                first.x shouldBe (0.5f plusOrMinus 0.0001f)
                first.y shouldBe (0f plusOrMinus 0.0001f)
            }

            Then("error's half turn starts the outline in a trough") {
                val first = jaliOutline(JaliMood.Error.spec.outline).first()
                first.x shouldBe (0.5f plusOrMinus 0.0001f)
                first.y shouldBe (0.035f plusOrMinus 0.0001f)
            }

            Then("night has no waves, so every point is on the circle") {
                jaliOutline(JaliMood.Night.spec.outline).forEach {
                    hypot(it.x - 0.5f, it.y - 0.5f) shouldBe (0.5f plusOrMinus 0.0001f)
                }
            }
        }

        Given("the eyes") {
            Then("they spread with the squash") {
                jaliEyeSpacing(squash = 0f) shouldBe 12f
                jaliEyeSpacing(squash = 0.55f) shouldBe (14.2f plusOrMinus 0.001f)
                jaliEyeSpacing(squash = 1f) shouldBe 16f
            }

            Then("they sit halfway down the body") {
                jaliEyeCenterY(jaliBody(squash = 1f)) shouldBe (61f plusOrMinus 0.001f)
            }

            Then("only busy glances to the side") {
                JaliMood.entries.filter { it.spec.eyeShift != 0f } shouldContainExactly
                    listOf(JaliMood.Busy)
                JaliMood.Busy.spec.eyeShift shouldBe 3f
            }

            Then("only pill, glance, wide and lid eyes blink") {
                JaliEye.entries.filter { it.blinks } shouldContainExactly
                    listOf(JaliEye.Pill, JaliEye.Glance, JaliEye.Lid, JaliEye.Wide)
            }
        }

        Given("each mood") {
            Then("the squash follows the handoff") {
                JaliMood.entries.map { it.spec.squash } shouldContainExactly
                    listOf(0f, 0.3f, 0.55f, 1f, 0f, 0.2f, 0f, 0f, 0f)
            }

            Then("only night sleeps and only error is muted") {
                JaliMood.entries.filter { it.spec.sleeping } shouldContainExactly
                    listOf(JaliMood.Night)
                JaliMood.entries.filter { it.spec.muted } shouldContainExactly
                    listOf(JaliMood.Error)
            }
        }
    })
