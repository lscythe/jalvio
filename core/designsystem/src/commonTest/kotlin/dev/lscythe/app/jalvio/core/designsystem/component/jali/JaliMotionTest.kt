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

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

class JaliMotionTest :
    BehaviorSpec({
        Given("an idle loop") {
            Then("every loop starts and ends at rest") {
                JaliMotion.entries.forEach {
                    it.pose(0f) shouldBe IdlePose.Rest
                    it.pose(1f) shouldBe IdlePose.Rest
                }
            }

            Then("breathe widens and flattens halfway") {
                JaliMotion.Breathe.pose(0.5f) shouldBe IdlePose(scaleX = 1.035f, scaleY = 0.965f)
            }

            Then("bob rises 3 halfway") {
                JaliMotion.Bob.pose(0.5f) shouldBe IdlePose(translationY = -3f)
            }

            Then("squish widens and flattens halfway") {
                JaliMotion.Squish.pose(0.5f) shouldBe IdlePose(scaleX = 1.06f, scaleY = 0.93f)
            }

            Then("hop jumps at 30 percent and lands squashed at 55 percent") {
                JaliMotion.Hop.pose(0.3f) shouldBe
                    IdlePose(translationY = -9f, scaleX = 0.95f, scaleY = 1.05f)
                JaliMotion.Hop.pose(0.55f) shouldBe IdlePose(scaleX = 1.07f, scaleY = 0.93f)
                JaliMotion.Hop.pose(0.7f) shouldBe IdlePose.Rest
            }

            Then("shake tilts left at 15 percent and right at 30 percent") {
                JaliMotion.Shake.pose(0.15f) shouldBe IdlePose(translationX = -2f, rotation = -2f)
                JaliMotion.Shake.pose(0.3f) shouldBe IdlePose(translationX = 2f, rotation = 2f)
                JaliMotion.Shake.pose(0.6f) shouldBe IdlePose.Rest
            }

            Then("each mood keeps the handoff's loop and period") {
                JaliMood.entries.map { it.spec.idle } shouldBe
                    listOf(
                        JaliIdle(JaliMotion.Breathe, 3_000),
                        JaliIdle(JaliMotion.Bob, 1_800),
                        JaliIdle(JaliMotion.Squish, 2_200),
                        JaliIdle(JaliMotion.Squish, 800),
                        JaliIdle(JaliMotion.Hop, 1_400),
                        JaliIdle(JaliMotion.Shake, 1_000),
                        JaliIdle(JaliMotion.Breathe, 3_600),
                        JaliIdle(JaliMotion.Breathe, 4_400),
                        JaliIdle(JaliMotion.Shake, 2_600),
                    )
            }
        }

        Given("the blink") {
            Then("the eyes stay open until 93 percent and close to 8 percent at 96") {
                jaliBlinkScale(0.5f) shouldBe 1f
                jaliBlinkScale(0.93f) shouldBe 1f
                jaliBlinkScale(0.96f) shouldBe (0.08f plusOrMinus 0.0001f)
                jaliBlinkScale(1f) shouldBe 1f
            }
        }

        Given("a sleeping z") {
            Then("it fades in from below and fades out up and to the right") {
                jaliZPose(0f) shouldBe
                    ZPose(alpha = 0f, translationX = 0f, translationY = 4f, scale = 0.8f)
                jaliZPose(0.4f).alpha shouldBe 1f
                jaliZPose(1f) shouldBe
                    ZPose(alpha = 0f, translationX = 6f, translationY = -8f, scale = 1.1f)
            }
        }
    })
