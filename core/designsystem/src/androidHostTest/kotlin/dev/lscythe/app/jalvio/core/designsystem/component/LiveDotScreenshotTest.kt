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
package dev.lscythe.app.jalvio.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenshot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class LiveDotScreenshotTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun liveDotLight() = captureBlink("light", ThemeMode.Light)

    @Test fun liveDotDark() = captureBlink("dark", ThemeMode.Dark)

    private fun captureBlink(name: String, mode: ThemeMode) {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            JalvioTheme(ThemeSettings(mode = mode)) {
                Surface {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        LiveDot()
                        LiveDot(label = "Live")
                        CompositionLocalProvider(LocalReduceMotion provides true) {
                            LiveDot(label = "Live")
                        }
                    }
                }
            }
        }
        composeRule.onRoot().captureScreenshot("LiveDot_$name")
        composeRule.mainClock.advanceTimeBy(HALF_LOOP_MILLIS)
        composeRule.onRoot().captureScreenshot("LiveDot_${name}_dim")
    }
}

private const val HALF_LOOP_MILLIS = 800L
