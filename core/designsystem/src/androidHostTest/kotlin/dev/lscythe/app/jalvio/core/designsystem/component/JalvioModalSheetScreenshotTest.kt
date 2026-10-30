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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenScreenshot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w400dp-h720dp")
class JalvioModalSheetScreenshotTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun floatingLight() = capture("ModalSheet_light", ThemeMode.Light)

    @Test fun floatingDark() = capture("ModalSheet_dark", ThemeMode.Dark)

    @OptIn(ExperimentalMaterial3Api::class)
    private fun capture(name: String, mode: ThemeMode) {
        composeRule.setContent {
            JalvioTheme(ThemeSettings(mode = mode)) {
                Box(
                    Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                )
                JalvioModalSheet(
                    onDismissRequest = {},
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                ) {
                    Text("How's the bus?", style = MaterialTheme.typography.headlineLarge)
                    Text(
                        "Tell riders on corridor 1 how crowded it is.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Report") }
                }
            }
        }
        composeRule.waitForIdle()
        captureScreenScreenshot(name)
    }
}
