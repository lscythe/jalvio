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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import dev.lscythe.app.jalvio.core.designsystem.icon.BookmarkFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.CloudOffFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenScreenshot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w400dp-h720dp")
class JalvioDialogScreenshotTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun errorLight() = capture("Dialog_error_light", DialogTone.Error, ThemeMode.Light)

    @Test fun errorDark() = capture("Dialog_error_dark", DialogTone.Error, ThemeMode.Dark)

    @Test fun primaryLight() = capture("Dialog_primary_light", DialogTone.Primary, ThemeMode.Light)

    @Test
    @Config(qualifiers = "w320dp-h640dp")
    fun errorSmallPhoneLargeFont() {
        RuntimeEnvironment.setFontScale(2f)
        capture("Dialog_error_small_font2x", DialogTone.Error, ThemeMode.Light)
    }

    @Test
    @Config(qualifiers = "w640dp-h320dp")
    fun errorSmallPhoneSideways() =
        capture("Dialog_error_small_sideways", DialogTone.Error, ThemeMode.Light)

    private fun capture(name: String, tone: DialogTone, mode: ThemeMode) {
        composeRule.setContent {
            JalvioTheme(ThemeSettings(mode = mode)) {
                Surface(Modifier.fillMaxSize()) {}
                if (tone == DialogTone.Error) ErrorDialog() else PrimaryDialog()
            }
        }
        composeRule.waitForIdle()
        captureScreenScreenshot(name)
    }
}

@Composable
private fun ErrorDialog() =
    JalvioDialog(
        icon = JalvioIcons.CloudOffFilled,
        title = "Delete your account?",
        text =
            "Your saved places, trips, reports and avatar are removed from this phone. " +
                "There is no backup, so this cannot be undone.",
        confirmLabel = "Delete everything",
        onConfirm = {},
        dismissLabel = "Keep my account",
        onDismissRequest = {},
        tone = DialogTone.Error,
    )

@Composable
private fun PrimaryDialog() =
    JalvioDialog(
        icon = JalvioIcons.BookmarkFilled,
        title = "Save Halte Blok M?",
        text = "Saved stops show up first on Home, with their live arrivals.",
        confirmLabel = "Save stop",
        onConfirm = {},
        dismissLabel = "Not now",
        onDismissRequest = {},
        tone = DialogTone.Primary,
    )
