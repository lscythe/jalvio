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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EntranceTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun jumpsToItsRestingPlaceWithReduceMotion() {
        show(reduceMotion = true)

        composeRule.mainClock.advanceTimeByFrame()

        top() shouldBe 0.dp
    }

    @Test
    fun startsBelowItsRestingPlaceAndRises() {
        show(reduceMotion = false)

        composeRule.mainClock.advanceTimeBy(16)
        top() shouldBeGreaterThan 30.dp

        composeRule.mainClock.advanceTimeBy(2_000)
        top() shouldBe 0.dp
    }

    private fun top() = composeRule.onNodeWithTag(Tag).getBoundsInRoot().top

    private fun show(reduceMotion: Boolean) {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            JalvioTheme {
                CompositionLocalProvider(LocalReduceMotion provides reduceMotion) {
                    Box(
                        Modifier.size(40.dp)
                            .entrance(EntranceKind.Up, delayMillis = EntranceDelays.Fifth)
                            .testTag(Tag)
                    )
                }
            }
        }
    }
}

private const val Tag = "entrance"
