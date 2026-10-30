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
package dev.lscythe.app.jalvio.core.designsystem.component.toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.BookmarkRemoveFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.CheckCircleFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.CloudOffFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.NotificationsFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.ThumbUpFilled
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenshot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w400dp-h1000dp")
class JalvioToastScreenshotTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun toastsLight() = captureToasts("light", ThemeMode.Light)

    @Test fun toastsDark() = captureToasts("dark", ThemeMode.Dark)

    private fun captureToasts(name: String, mode: ThemeMode) {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            JalvioTheme(ThemeSettings(mode = mode)) {
                Surface {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        toasts.forEach { JalvioToast(it) }
                    }
                }
            }
        }
        composeRule.onRoot().captureScreenshot("Toast_$name")
        composeRule.mainClock.advanceTimeBy(HALF_DEFAULT_MILLIS)
        composeRule.onRoot().captureScreenshot("Toast_${name}_half")
    }
}

private const val HALF_DEFAULT_MILLIS = 2_000L

private val toasts =
    listOf(
        ToastData(
            "Report posted",
            ToastTone.Done,
            JalvioIcons.CheckCircleFilled,
            null,
            ToastDuration.Default,
        ),
        ToastData(
            "Bus 13E is arriving",
            ToastTone.Info,
            JalvioIcons.NotificationsFilled,
            null,
            ToastDuration.Default,
        ),
        ToastData(
            "Thanks for confirming",
            ToastTone.Action,
            JalvioIcons.ThumbUpFilled,
            null,
            ToastDuration.Short,
        ),
        ToastData(
            "You're offline",
            ToastTone.Error,
            JalvioIcons.CloudOffFilled,
            null,
            ToastDuration.Default,
        ),
        ToastData(
            "Stop removed",
            ToastTone.Action,
            JalvioIcons.BookmarkRemoveFilled,
            "Undo",
            ToastDuration.Default,
        ),
    )
