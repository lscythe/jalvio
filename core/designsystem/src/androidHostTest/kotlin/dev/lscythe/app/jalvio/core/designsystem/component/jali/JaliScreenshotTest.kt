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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.designsystem.theme.captureThemedScreenshot
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenshot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w520dp-h200dp")
class JaliScreenshotTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun moodsInEveryTone() =
        mapOf("light" to ThemeMode.Light, "dark" to ThemeMode.Dark).forEach { (name, mode) ->
            captureThemedScreenshot("Jali_moods_$name", ThemeSettings(mode = mode)) {
                Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SheetTones.forEach { tone ->
                        MoodRow { Jali(it, tone = tone, size = 52.dp, idle = false) }
                    }
                }
            }
        }

    @Test fun idleAtTheBlink() = captureIdleFrame("Jali_idle_blink", reduceMotion = false)

    @Test
    fun reducedMotionStaysStill() = captureIdleFrame("Jali_reduced_motion", reduceMotion = true)

    @Test
    fun moodChangeMorphsTheBody() {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            var mood by remember { mutableStateOf(JaliMood.Calm) }
            JalvioTheme(ThemeSettings(mode = ThemeMode.Light)) {
                Surface { Jali(mood, Modifier.padding(16.dp), idle = false) }
            }
            LaunchedEffect(Unit) { mood = JaliMood.Packed }
        }
        composeRule.mainClock.advanceTimeBy(MORPH_MILLIS)
        composeRule.onRoot().captureScreenshot("Jali_morph_calm_to_packed")
    }

    private fun captureIdleFrame(name: String, reduceMotion: Boolean) {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            JalvioTheme(ThemeSettings(mode = ThemeMode.Light)) {
                CompositionLocalProvider(LocalReduceMotion provides reduceMotion) {
                    Surface { MoodRow(Modifier.padding(8.dp)) { Jali(it, size = 52.dp) } }
                }
            }
        }
        composeRule.mainClock.advanceTimeBy(BLINK_CLOSED_MILLIS)
        composeRule.onRoot().captureScreenshot(name)
    }
}

@Composable
private fun MoodRow(modifier: Modifier = Modifier, jali: @Composable (JaliMood) -> Unit) =
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        JaliMood.entries.forEach { jali(it) }
    }

private val SheetTones = listOf(JaliTone.Tertiary, JaliTone.Primary, JaliTone.Container)
private const val BLINK_CLOSED_MILLIS = 4_032L
private const val MORPH_MILLIS = 150L
