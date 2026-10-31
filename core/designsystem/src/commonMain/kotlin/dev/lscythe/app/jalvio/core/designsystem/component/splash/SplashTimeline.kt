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
package dev.lscythe.app.jalvio.core.designsystem.component.splash

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

internal data class SplashFrame(
    val jaliSize: Float,
    val jaliCenter: Offset,
    val eyeOpen: Float,
    val wordmarkReveal: Float,
    val alpha: Float,
)

internal const val SplashDurationMillis = 1_150L

internal object SplashLockup {
    const val JaliRestSize = 150f
    const val RevealShift = 18f

    private const val FontSize = 56f
    private const val UnitsPerEm = 1000f
    private const val Ascent = 974f
    private const val Descent = 236f
    private const val JalviAdvance = 1790f
    private const val InkOverhang = 132f
    private const val InkWidth = 1922f
    private const val OAdvance = 558f
    private const val OTop = 511f
    private const val OBottom = -18f
    private const val OMargin = 1.18f

    private fun fromFontUnits(units: Float) = units / UnitsPerEm * FontSize

    val wordmarkOverhang = fromFontUnits(InkOverhang)
    val jaliInOSize = (fromFontUnits(OTop - OBottom) * OMargin).roundToInt().toFloat()

    private val gap = max(0f, (fromFontUnits(OAdvance) - jaliInOSize) / 2) + 1f
    private val textWidth = fromFontUnits(JalviAdvance)
    private val textLeft = -(textWidth + gap * 2 + jaliInOSize) / 2
    private val baseline =
        (FontSize - fromFontUnits(Ascent + Descent)) / 2 + fromFontUnits(Ascent) - FontSize / 2

    val jaliInOCenter =
        Offset(
            textLeft + textWidth + gap + jaliInOSize / 2,
            baseline - fromFontUnits(OTop + OBottom) / 2,
        )

    val wordmarkBounds =
        Rect(
            left = textLeft - wordmarkOverhang,
            top = baseline - fromFontUnits(Ascent),
            right = textLeft - wordmarkOverhang + fromFontUnits(InkWidth),
            bottom = baseline + fromFontUnits(Descent),
        )
}

internal fun splashFrame(elapsedMillis: Long): SplashFrame {
    val time = elapsedMillis.toFloat()
    val travel = progress(time, 0f, TravelEndMillis)
    return SplashFrame(
        jaliSize = lerp(SplashLockup.JaliRestSize, SplashLockup.jaliInOSize, travel),
        jaliCenter = SplashLockup.jaliInOCenter * travel,
        eyeOpen = 1f - BlinkDepth * bump(time, BlinkAtMillis, BlinkHalfWidthMillis),
        wordmarkReveal = progress(time, RevealStartMillis, RevealEndMillis),
        alpha = 1f - progress(time, HoldEndMillis, SplashDurationMillis.toFloat()),
    )
}

private fun progress(time: Float, start: Float, end: Float): Float =
    when {
        time <= start -> 0f
        time >= end -> 1f
        else -> easeOutCubic((time - start) / (end - start))
    }

private fun easeOutCubic(fraction: Float): Float {
    val remaining = 1f - fraction
    return 1f - remaining * remaining * remaining
}

private fun bump(time: Float, at: Float, halfWidth: Float): Float {
    val distance = abs(time - at) / halfWidth
    return if (distance < 1f) 1f - distance else 0f
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
    start + (stop - start) * fraction

private const val TravelEndMillis = 370f
private const val RevealStartMillis = 100f
private const val RevealEndMillis = 410f
private const val BlinkAtMillis = 470f
private const val BlinkHalfWidthMillis = 70f
private const val BlinkDepth = 0.92f
private const val HoldEndMillis = 850f
