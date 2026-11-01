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
package dev.lscythe.app.jalvio.feature.onboarding.impl.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.lscythe.app.jalvio.core.designsystem.theme.rememberRobotoFlexFamily
import kotlin.math.abs
import kotlin.math.roundToInt

internal data class HeadlineAxes(val weight: Int, val width: Float)

private const val StartWeight = 200
private const val EndWeight = 850
private const val WeightStep = 50
private val WidthStops = listOf(100f, 85f, 70f, 55f, 40f, 28f)

internal fun headlineAxes(progress: Float): HeadlineAxes {
    val clamped = progress.coerceIn(0f, 1f)
    val weight = lerp(StartWeight.toFloat(), EndWeight.toFloat(), clamped)
    val width = lerp(WidthStops.first(), WidthStops.last(), clamped)
    return HeadlineAxes(
        weight = (weight / WeightStep).roundToInt() * WeightStep,
        width = WidthStops.minBy { abs(it - width) },
    )
}

internal fun fittedHeadlineSize(maxSp: Float, minSp: Float, fits: (Float) -> Boolean): Float =
    generateSequence(maxSp) { it - SizeStepSp }.takeWhile { it > minSp }.firstOrNull(fits) ?: minSp

@Composable
fun SpringHeadline(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 64.sp,
    color: Color = Color.Unspecified,
    delayMillis: Int = 0,
) {
    val progress by rememberEntranceProgress(delayMillis)
    val axes = headlineAxes(progress)
    val startStyle = headlineStyle(headlineAxes(0f))
    val endStyle = headlineStyle(headlineAxes(1f))
    val measurer = rememberTextMeasurer()
    BoxWithConstraints(modifier) {
        val maxWidthPx = constraints.maxWidth
        val fitted =
            remember(text, fontSize, maxWidthPx, startStyle, endStyle) {
                val words = text.split(' ').filter { it.isNotEmpty() }
                fittedHeadlineSize(fontSize.value, MinFontSize.value) { size ->
                    listOf(startStyle, endStyle).all { style ->
                        words.all { word ->
                            measurer
                                .measure(word, style.copy(fontSize = size.sp), maxLines = 1)
                                .size
                                .width <= maxWidthPx
                        }
                    }
                }
            }
        Text(
            text = text,
            modifier = Modifier.graphicsLayer { alpha = entranceAlpha(progress) },
            color = color,
            style = headlineStyle(axes).copy(fontSize = fitted.sp),
        )
    }
}

@Composable
private fun headlineStyle(axes: HeadlineAxes) =
    TextStyle(
        fontFamily = rememberRobotoFlexFamily(axes.weight, axes.width),
        fontWeight = FontWeight(axes.weight),
        lineHeight = HeadlineLineHeight,
        letterSpacing = (-0.01).em,
        lineHeightStyle =
            LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.Both),
    )

private val MinFontSize = 28.sp
private val HeadlineLineHeight = 0.88.em
private const val SizeStepSp = 2f
