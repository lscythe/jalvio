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

import androidx.compose.ui.graphics.Color

enum class ThemeMode {
    System,
    Light,
    Dark,
}

sealed interface ThemeColors {
    data object Jalvio : ThemeColors

    data class Custom(val seed: Color) : ThemeColors
}

enum class ThemeSpec {
    Spec2021,
    Spec2025,
}

enum class ThemeStyle(val supportedSpecs: Set<ThemeSpec>) {
    TonalSpot(ThemeSpec.entries.toSet()),
    Neutral(ThemeSpec.entries.toSet()),
    Vibrant(ThemeSpec.entries.toSet()),
    Expressive(ThemeSpec.entries.toSet()),
    Rainbow(setOf(ThemeSpec.Spec2021)),
    FruitSalad(setOf(ThemeSpec.Spec2021)),
    Monochrome(setOf(ThemeSpec.Spec2021)),
    Fidelity(setOf(ThemeSpec.Spec2021)),
    Content(setOf(ThemeSpec.Spec2021)),
}

enum class ThemeContrast {
    Standard,
    Medium,
    High,
}

data class ThemeSettings(
    val mode: ThemeMode = ThemeMode.System,
    val dynamicColor: Boolean = false,
    val amoled: Boolean = false,
    val colors: ThemeColors = ThemeColors.Jalvio,
    val style: ThemeStyle = ThemeStyle.TonalSpot,
    val spec: ThemeSpec = ThemeSpec.Spec2025,
    val contrast: ThemeContrast = ThemeContrast.Standard,
    val reduceMotion: Boolean = false,
) {
    val effectiveSpec: ThemeSpec
        get() = spec.takeIf { it in style.supportedSpecs } ?: ThemeSpec.Spec2021

    fun isDark(systemDark: Boolean): Boolean =
        when (mode) {
            ThemeMode.System -> systemDark
            ThemeMode.Light -> false
            ThemeMode.Dark -> true
        }
}
