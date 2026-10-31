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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliMood
import dev.lscythe.app.jalvio.core.designsystem.component.jali.jaliOutline
import dev.lscythe.app.jalvio.core.designsystem.component.jali.outlinePath
import dev.lscythe.app.jalvio.core.designsystem.component.jali.spec
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.WordmarkLetters
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioBrandScheme
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import kotlin.math.min

@Composable
fun JalvioSplash(started: Boolean, onFinished: () -> Unit, modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val reduceMotion = LocalReduceMotion.current
    val elapsed = remember { mutableLongStateOf(0L) }
    val finish by rememberUpdatedState(onFinished)
    val wordmark = rememberVectorPainter(JalvioIcons.WordmarkLetters)
    val outline = remember { jaliOutline(JaliMood.Calm.spec.outline) }

    LaunchedEffect(started, reduceMotion) {
        if (!started) return@LaunchedEffect
        if (!reduceMotion) {
            val start = withFrameMillis { it }
            while (elapsed.longValue < SplashDurationMillis) {
                elapsed.longValue = withFrameMillis { it } - start
            }
        }
        finish()
    }

    Canvas(modifier.fillMaxSize().graphicsLayer { alpha = splashFrame(elapsed.longValue).alpha }) {
        val frame = splashFrame(elapsed.longValue)
        drawRect(colors.surface)
        drawJali(outline, frame)
        drawWordmark(wordmark, frame.wordmarkReveal, colors)
    }
}

private fun DrawScope.drawWordmark(wordmark: Painter, reveal: Float, colors: ColorScheme) {
    if (reveal <= 0f) return
    val bounds = SplashLockup.wordmarkBounds
    val shift = SplashLockup.RevealShift * (1f - reveal)
    val inkSize = Size(px(bounds.width), px(bounds.height))
    translate(center.x + px(bounds.left + shift), center.y + px(bounds.top)) {
        clipRect(left = inkSize.width * (1f - reveal)) {
            with(wordmark) { draw(inkSize, colorFilter = ColorFilter.tint(colors.onSurface)) }
        }
    }
}

private fun DrawScope.drawJali(outline: List<Offset>, frame: SplashFrame) {
    val size = px(frame.jaliSize)
    val topLeft = center + frame.jaliCenter * density - Offset(size, size) / 2f
    drawPath(
        outlinePath(outline, Rect(topLeft, Size(size, size))),
        JalvioBrandScheme.primaryContainer,
    )
    withTransform({
        translate(topLeft.x, topLeft.y)
        scale(size / ArtboardSize, size / ArtboardSize, Offset.Zero)
    }) {
        val height = EyeHeight * frame.eyeOpen
        listOf(-1f, 1f).forEach { side ->
            drawRoundRect(
                JalvioBrandScheme.onPrimaryContainer,
                topLeft =
                    Offset(
                        ArtboardSize / 2 + side * EyeSpacing - EyeWidth / 2,
                        (ArtboardSize - height) / 2,
                    ),
                size = Size(EyeWidth, height),
                cornerRadius = CornerRadius(min(EyeWidth, height) / 2),
            )
        }
    }
}

private fun DrawScope.px(dp: Float): Float = dp.dp.toPx()

private const val ArtboardSize = 100f
private const val EyeSpacing = 12f
private const val EyeWidth = 7f
private const val EyeHeight = 14f
