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
package dev.lscythe.app.jalvio.core.designsystem.component.splash

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeBetween
import io.kotest.matchers.shouldBe

class SplashTimelineTest :
    BehaviorSpec({
        Given("the lockup") {
            Then("Jali shrinks to 35 dp, the height of the o with an 18% margin") {
                SplashLockup.JaliRestSize shouldBe 150f
                SplashLockup.jaliInOSize shouldBe 35f
            }

            Then("Jali sits where the o of jalvio would be") {
                SplashLockup.jaliInOCenter.x shouldBe (50.12f plusOrMinus 0.01f)
                SplashLockup.jaliInOCenter.y shouldBe (6.86f plusOrMinus 0.01f)
            }

            Then("the letters jalvi end where Jali begins, one dp apart") {
                val bounds = SplashLockup.wordmarkBounds
                bounds.left shouldBe (-76.01f plusOrMinus 0.01f)
                bounds.top shouldBe (-33.88f plusOrMinus 0.01f)
                bounds.right shouldBe (31.62f plusOrMinus 0.01f)
                bounds.bottom shouldBe (33.88f plusOrMinus 0.01f)
            }

            Then("the whole lockup is centred on the screen") {
                val textLeft = SplashLockup.wordmarkBounds.left + SplashLockup.wordmarkOverhang
                val lockupRight = SplashLockup.jaliInOCenter.x + SplashLockup.jaliInOSize / 2 + 1f
                textLeft shouldBe (-lockupRight plusOrMinus 0.01f)
            }
        }

        Given("the splash timeline") {
            When("it starts") {
                val frame = splashFrame(elapsedMillis = 0)

                Then("Jali is the settled system splash icon in the centre, eyes open") {
                    frame.jaliSize shouldBe 150f
                    frame.jaliCenter.x shouldBe 0f
                    frame.jaliCenter.y shouldBe 0f
                    frame.eyeOpen shouldBe 1f
                    frame.wordmarkReveal shouldBe 0f
                    frame.alpha shouldBe 1f
                }
            }

            When("370 ms have passed") {
                val frame = splashFrame(elapsedMillis = 370)

                Then("Jali has landed in the o") {
                    frame.jaliSize shouldBe (35f plusOrMinus 0.001f)
                    frame.jaliCenter.x shouldBe (50.12f plusOrMinus 0.01f)
                    frame.jaliCenter.y shouldBe (6.86f plusOrMinus 0.01f)
                }
            }

            Then("the letters reveal between 100 and 410 ms") {
                splashFrame(elapsedMillis = 100).wordmarkReveal shouldBe 0f
                splashFrame(elapsedMillis = 255).wordmarkReveal.shouldBeBetween(0.5f, 0.99f, 0f)
                splashFrame(elapsedMillis = 410).wordmarkReveal shouldBe 1f
            }

            Then("Jali blinks at 470 ms") {
                splashFrame(elapsedMillis = 400).eyeOpen shouldBe 1f
                splashFrame(elapsedMillis = 470).eyeOpen shouldBe (0.08f plusOrMinus 0.001f)
                splashFrame(elapsedMillis = 540).eyeOpen shouldBe 1f
            }

            Then("the lockup holds until 850 ms and fades out by 1150 ms") {
                splashFrame(elapsedMillis = 850).alpha shouldBe 1f
                splashFrame(elapsedMillis = 1_000).alpha.shouldBeBetween(0.01f, 0.99f, 0f)
                splashFrame(elapsedMillis = SplashDurationMillis).alpha shouldBe 0f
                splashFrame(elapsedMillis = SplashDurationMillis + 500).alpha shouldBe 0f
            }
        }
    })
