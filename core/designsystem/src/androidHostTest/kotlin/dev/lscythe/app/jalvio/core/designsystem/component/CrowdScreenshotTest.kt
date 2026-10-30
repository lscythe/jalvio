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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.designsystem.theme.captureThemedScreenshot
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class CrowdScreenshotTest {
    private val modes = mapOf("light" to ThemeMode.Light, "dark" to ThemeMode.Dark)

    @Test
    fun crowd() = modes.forEach { (name, mode) ->
        captureThemedScreenshot("Crowd_$name", ThemeSettings(mode = mode)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CrowdLevel.entries.forEach { CrowdChip(it) }
                (CrowdLevel.entries + null).forEach { CrowdDots(it, showLabel = true) }
            }
        }
    }
}
