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
package dev.lscythe.app.jalvio.core.designsystem.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import dev.lscythe.app.jalvio.core.testing.screenshot.Phone
import dev.lscythe.app.jalvio.core.testing.screenshot.captureOnPhone
import dev.lscythe.app.jalvio.core.testing.screenshot.captureScreenshot

internal fun captureThemedScreenshot(
    name: String,
    settings: ThemeSettings,
    content: @Composable () -> Unit,
) = captureScreenshot(name) { JalvioTheme(settings) { Surface(content = content) } }

internal fun ComposeContentTestRule.captureThemedPhoneScreenshot(
    name: String,
    phone: Phone,
    content: @Composable () -> Unit,
) =
    captureOnPhone(name, phone) {
        JalvioTheme(ThemeSettings(mode = ThemeMode.Light)) { Surface(content = content) }
    }
