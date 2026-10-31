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

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JalvioSplashTest {
    @get:Rule val composeRule = createComposeRule()

    private var finished = false

    @Test
    fun waitsForTheSystemSplashBeforePlaying() {
        show(started = false)

        composeRule.mainClock.advanceTimeBy(SplashDurationMillis * 2)

        finished shouldBe false
    }

    @Test
    fun finishesOnceTheLockupHasHeldAndFaded() {
        show(started = true)

        composeRule.mainClock.advanceTimeBy(SplashDurationMillis - 100)
        finished shouldBe false

        composeRule.mainClock.advanceTimeBy(200)
        finished shouldBe true
    }

    @Test
    fun skipsStraightToTheAppWithReduceMotion() {
        show(started = true, reduceMotion = true)

        composeRule.mainClock.advanceTimeByFrame()

        finished shouldBe true
    }

    private fun show(started: Boolean, reduceMotion: Boolean = false) {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            CompositionLocalProvider(LocalReduceMotion provides reduceMotion) {
                JalvioSplash(started = started, onFinished = { finished = true })
            }
        }
    }
}
