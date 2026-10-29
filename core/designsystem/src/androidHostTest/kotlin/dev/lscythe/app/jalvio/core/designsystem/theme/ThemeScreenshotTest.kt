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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w480dp-h1200dp")
class ThemeScreenshotTest {
    private val variants =
        mapOf(
            "light" to ThemeSettings(mode = ThemeMode.Light),
            "dark" to ThemeSettings(mode = ThemeMode.Dark),
            "vibrant_pink" to
                ThemeSettings(
                    mode = ThemeMode.Light,
                    colors = ThemeColors.Custom(Color(0xFFD94F8A)),
                    style = ThemeStyle.Vibrant,
                ),
        )

    @Test
    fun typeSpecimen() = variants.forEach { (name, settings) ->
        captureThemedScreenshot("Theme_type_$name", settings) { TypeSpecimen() }
    }

    @Test
    fun colorRoles() = variants.forEach { (name, settings) ->
        captureThemedScreenshot("Theme_colors_$name", settings) { ColorSpecimen() }
    }

    @Test
    fun amoled() =
        captureThemedScreenshot(
            "Theme_colors_amoled",
            ThemeSettings(mode = ThemeMode.Dark, amoled = true),
        ) {
            ColorSpecimen()
        }
}

@Composable
private fun TypeSpecimen() {
    val type = MaterialTheme.typography
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        listOf(
                "displayLarge 7 min" to type.displayLarge,
                "displayMedium Bundaran HI" to type.displayMedium,
                "displaySmall Bundaran HI" to type.displaySmall,
                "headlineLarge How's the stop?" to type.headlineLarge,
                "headlineMedium Report posted" to type.headlineMedium,
                "headlineSmall 1 6 9" to type.headlineSmall,
                "titleLarge Next buses" to type.titleLarge,
                "titleMedium Next buses" to type.titleMedium,
                "titleSmall Next buses" to type.titleSmall,
                "bodyLargeEmphasized to Kota" to type.bodyLargeEmphasized,
                "bodyLarge Short queue at the Kota side" to type.bodyLarge,
                "bodyMedium Short queue at the Kota side" to type.bodyMedium,
                "bodySmall Short queue at the Kota side" to type.bodySmall,
                "labelLarge Send report" to type.labelLarge,
                "labelMedium Standing · 2 min ago" to type.labelMedium,
                "labelSmall Standing · 2 min ago" to type.labelSmall,
                "displayLargeEmphasized 12" to type.displayLargeEmphasized,
            )
            .forEach { (text, style) -> SpecimenLine(text, style) }
    }
}

@Composable
private fun SpecimenLine(text: String, style: TextStyle) = Text(text, style = style, maxLines = 1)

@Composable
private fun ColorSpecimen() {
    val scheme = MaterialTheme.colorScheme
    val jalvio = JalvioTheme.colors
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        listOf(
                "primary" to (scheme.primary to scheme.onPrimary),
                "primaryContainer" to (scheme.primaryContainer to scheme.onPrimaryContainer),
                "secondary" to (scheme.secondary to scheme.onSecondary),
                "secondaryContainer" to (scheme.secondaryContainer to scheme.onSecondaryContainer),
                "tertiary" to (scheme.tertiary to scheme.onTertiary),
                "tertiaryContainer" to (scheme.tertiaryContainer to scheme.onTertiaryContainer),
                "error" to (scheme.error to scheme.onError),
                "errorContainer" to (scheme.errorContainer to scheme.onErrorContainer),
                "surface" to (scheme.surface to scheme.onSurface),
                "surfaceContainer" to (scheme.surfaceContainer to scheme.onSurface),
                "surfaceContainerHighest" to
                    (scheme.surfaceContainerHighest to scheme.onSurfaceVariant),
                "calm" to (jalvio.calm.container to jalvio.calm.onContainer),
                "busy" to (jalvio.busy.container to jalvio.busy.onContainer),
                "standing" to (jalvio.standing.container to jalvio.standing.onContainer),
                "packed" to (jalvio.packed.container to jalvio.packed.onContainer),
                "info" to (jalvio.info.container to jalvio.info.onContainer),
            )
            .forEach { (name, colors) -> Swatch(name, colors.first, colors.second) }
    }
}

@Composable
private fun Swatch(name: String, background: Color, content: Color) =
    Text(
        name,
        color = content,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.background(background).padding(horizontal = 12.dp, vertical = 6.dp),
    )
