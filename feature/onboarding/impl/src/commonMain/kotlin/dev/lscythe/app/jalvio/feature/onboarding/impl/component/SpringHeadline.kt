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

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import dev.lscythe.app.jalvio.core.designsystem.theme.rememberRobotoFlexFamily
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.delay

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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SpringHeadline(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 64.sp,
    color: Color = Color.Unspecified,
    delayMillis: Int = 0,
) {
    val reduceMotion = LocalReduceMotion.current
    val spec = MaterialTheme.motionScheme.slowSpatialSpec<Float>()
    val progress = remember { Animatable(if (reduceMotion) 1f else 0f) }
    LaunchedEffect(reduceMotion) {
        if (reduceMotion) {
            progress.snapTo(1f)
        } else {
            delay(delayMillis.toLong())
            progress.animateTo(1f, spec)
        }
    }
    val axes = headlineAxes(progress.value)
    Text(
        text = text,
        modifier = modifier,
        color = color,
        autoSize = TextAutoSize.StepBased(minFontSize = MinFontSize, maxFontSize = fontSize),
        style =
            TextStyle(
                fontFamily = rememberRobotoFlexFamily(axes.weight, axes.width),
                fontWeight = FontWeight(axes.weight),
                lineHeight = HeadlineLineHeight,
                letterSpacing = (-0.01).em,
                lineHeightStyle =
                    LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.Both),
            ),
    )
}

private val MinFontSize = 28.sp
private val HeadlineLineHeight = 0.88.em
