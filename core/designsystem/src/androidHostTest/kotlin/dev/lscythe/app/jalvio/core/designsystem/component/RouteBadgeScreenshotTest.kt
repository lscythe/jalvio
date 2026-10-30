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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.RouteTone
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.designsystem.theme.captureThemedScreenshot
import dev.lscythe.app.jalvio.core.designsystem.theme.container
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w480dp-h1400dp")
class RouteBadgeScreenshotTest {
    private val modes = mapOf("light" to ThemeMode.Light, "dark" to ThemeMode.Dark)

    @Test
    fun routeBadges() = modes.forEach { (name, mode) ->
        captureThemedScreenshot("RouteBadge_$name", ThemeSettings(mode = mode)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                RouteBadgeSize.entries.forEach { size ->
                    RouteTone.entries.forEach { tone -> BadgeRow(size, tone) }
                }
            }
        }
    }
}

@Composable
private fun BadgeRow(size: RouteBadgeSize, tone: RouteTone) {
    val background =
        if (size == RouteBadgeSize.OnColor) tone.container(MaterialTheme.colorScheme)
        else MaterialTheme.colorScheme.surface
    Row(
        Modifier.background(background).padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        listOf("1", "13E", "JAK.10A").forEach { RouteBadge(it, tone, size) }
    }
}
