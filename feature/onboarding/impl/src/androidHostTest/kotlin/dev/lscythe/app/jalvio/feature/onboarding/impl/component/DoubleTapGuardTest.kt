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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DoubleTapGuardTest {
    @get:Rule val composeRule = createComposeRule()

    private var clicks = 0

    @Test
    fun ignoresTapsWithinADoubleTapOfAppearing() {
        show()

        val clock = composeRule.mainClock
        while (clock.currentTime < WithinDoubleTapMillis) {
            tap()
            clock.advanceTimeByFrame()
        }

        clicks shouldBe 0
    }

    @Test
    fun takesTapsOnceADoubleTapHasPassed() {
        show()

        composeRule.mainClock.advanceTimeBy(PastDoubleTapMillis)
        tap()

        clicks shouldBe 1
    }

    private fun show() {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            DoubleTapGuard {
                Box(Modifier.size(80.dp).testTag(Tag).clickable { clicks++ })
            }
        }
    }

    private fun tap() = composeRule.onNodeWithTag(Tag).performTouchInput { click() }
}

private const val Tag = "guarded"
private const val WithinDoubleTapMillis = 250L
private const val PastDoubleTapMillis = 400L
