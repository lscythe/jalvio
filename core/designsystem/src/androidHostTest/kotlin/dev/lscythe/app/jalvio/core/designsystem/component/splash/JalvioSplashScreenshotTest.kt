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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.testing.screenshot.LargestPhoneWindow
import dev.lscythe.app.jalvio.core.testing.screenshot.Phone
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenshot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class JalvioSplashScreenshotTest {
    @get:Rule val composeRule = createComposeRule()

    @Config(qualifiers = LargestPhoneWindow) @Test fun splashLight() = captureTimeline("light")

    @Config(qualifiers = "$LargestPhoneWindow-night")
    @Test
    fun splashDark() = captureTimeline("dark")

    private fun captureTimeline(name: String) {
        val phone = Phone.Medium
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            JalvioTheme {
                Box(Modifier.size(phone.widthDp.dp, phone.heightDp.dp).testTag(SplashTag)) {
                    JalvioSplash(started = true, onFinished = {})
                }
            }
        }
        listOf(0L, 200L, 650L).fold(0L) { previous, at ->
            composeRule.mainClock.advanceTimeBy(at - previous)
            composeRule.onNodeWithTag(SplashTag).captureScreenshot("JalvioSplash_${name}_${at}ms")
            at
        }
    }
}

private const val SplashTag = "splash"
