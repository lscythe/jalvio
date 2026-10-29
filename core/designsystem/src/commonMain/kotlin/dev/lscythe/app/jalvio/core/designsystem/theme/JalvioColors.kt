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
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.materialkolor.palettes.TonalPalette

@Immutable data class MeaningColor(val accent: Color, val container: Color, val onContainer: Color)

@Immutable
data class JalvioColors(
    val calm: MeaningColor,
    val busy: MeaningColor,
    val standing: MeaningColor,
    val packed: MeaningColor,
    val info: MeaningColor,
) {
    val arriving: MeaningColor
        get() = calm
}

private val BusyAmber = TonalPalette.fromInt(0xFFA87600.toInt())

fun jalvioColors(scheme: ColorScheme, dark: Boolean): JalvioColors =
    JalvioColors(
        calm = MeaningColor(scheme.tertiary, scheme.tertiaryContainer, scheme.onTertiaryContainer),
        busy = busyAmber(dark),
        standing =
            MeaningColor(scheme.secondary, scheme.secondaryContainer, scheme.onSecondaryContainer),
        packed = MeaningColor(scheme.error, scheme.errorContainer, scheme.onErrorContainer),
        info = MeaningColor(scheme.primary, scheme.primaryContainer, scheme.onPrimaryContainer),
    )

private fun busyAmber(dark: Boolean): MeaningColor =
    if (dark) {
        MeaningColor(BusyAmber.color(80), BusyAmber.color(30), BusyAmber.color(90))
    } else {
        MeaningColor(BusyAmber.color(40), BusyAmber.color(90), BusyAmber.color(10))
    }

private fun TonalPalette.color(tone: Int): Color = Color(tone(tone))
