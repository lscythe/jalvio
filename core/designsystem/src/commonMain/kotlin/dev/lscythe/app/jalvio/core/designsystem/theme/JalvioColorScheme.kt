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

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.material3.dynamicColorScheme

object JalvioSeeds {
    val Primary = Color(0xFF4977C2)
    val Secondary = Color(0xFF577F6D)
    val Tertiary = Color(0xFF07885B)
    val Error = Color(0xFFCC4B49)
    val Neutral = Color(0xFF6D778E)
    val NeutralVariant = Color(0xFF5477B5)
}

fun jalvioColorScheme(
    settings: ThemeSettings,
    dark: Boolean,
    platformScheme: ColorScheme?,
): ColorScheme {
    if (settings.dynamicColor && platformScheme != null) {
        return if (dark && settings.amoled) platformScheme.toAmoled() else platformScheme
    }
    val style = settings.style.toPaletteStyle()
    val contrastLevel = settings.contrast.toContrast().value
    val specVersion = settings.effectiveSpec.toSpecVersion()
    return when (val colors = settings.colors) {
        ThemeColors.Jalvio ->
            dynamicColorScheme(
                seedColor = JalvioSeeds.Primary,
                isDark = dark,
                isAmoled = settings.amoled,
                primary = JalvioSeeds.Primary,
                secondary = JalvioSeeds.Secondary,
                tertiary = JalvioSeeds.Tertiary,
                neutral = JalvioSeeds.Neutral,
                neutralVariant = JalvioSeeds.NeutralVariant,
                error = JalvioSeeds.Error,
                style = style,
                contrastLevel = contrastLevel,
                specVersion = specVersion,
            )
        is ThemeColors.Custom ->
            dynamicColorScheme(
                seedColor = colors.seed,
                isDark = dark,
                isAmoled = settings.amoled,
                style = style,
                contrastLevel = contrastLevel,
                specVersion = specVersion,
            )
    }
}

fun ColorScheme.toAmoled(): ColorScheme =
    copy(
        background = Color.Black,
        surface = Color.Black,
        surfaceDim = Color.Black,
        surfaceContainerLowest = Color.Black,
        surfaceContainerLow = Color.Black,
        surfaceContainer = Color.Black,
        surfaceContainerHigh = Color.Black,
        surfaceContainerHighest = Color.Black,
    )

private fun ThemeStyle.toPaletteStyle(): PaletteStyle =
    when (this) {
        ThemeStyle.TonalSpot -> PaletteStyle.TonalSpot
        ThemeStyle.Neutral -> PaletteStyle.Neutral
        ThemeStyle.Vibrant -> PaletteStyle.Vibrant
        ThemeStyle.Expressive -> PaletteStyle.Expressive
        ThemeStyle.Rainbow -> PaletteStyle.Rainbow
        ThemeStyle.FruitSalad -> PaletteStyle.FruitSalad
        ThemeStyle.Monochrome -> PaletteStyle.Monochrome
        ThemeStyle.Fidelity -> PaletteStyle.Fidelity
        ThemeStyle.Content -> PaletteStyle.Content
    }

private fun ThemeSpec.toSpecVersion(): ColorSpec.SpecVersion =
    when (this) {
        ThemeSpec.Spec2021 -> ColorSpec.SpecVersion.SPEC_2021
        ThemeSpec.Spec2025 -> ColorSpec.SpecVersion.SPEC_2025
    }

private fun ThemeContrast.toContrast(): Contrast =
    when (this) {
        ThemeContrast.Standard -> Contrast.Default
        ThemeContrast.Medium -> Contrast.Medium
        ThemeContrast.High -> Contrast.High
    }
