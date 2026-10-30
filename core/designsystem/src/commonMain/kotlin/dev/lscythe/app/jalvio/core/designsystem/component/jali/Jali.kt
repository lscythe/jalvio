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
package dev.lscythe.app.jalvio.core.designsystem.component.jali

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateRectAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion

enum class JaliMood {
    Calm,
    Busy,
    Standing,
    Packed,
    Thanks,
    Late,
    Home,
    Night,
    Error,
}

enum class JaliTone {
    Primary,
    Container,
    Tertiary,
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Jali(
    mood: JaliMood,
    modifier: Modifier = Modifier,
    tone: JaliTone = JaliTone.Primary,
    size: Dp = 120.dp,
    idle: Boolean = true,
    contentDescription: String? = null,
) {
    val spec = mood.spec
    val scheme = MaterialTheme.colorScheme
    val motion = MaterialTheme.motionScheme
    val (body, ink) =
        if (spec.muted) scheme.surfaceContainerHighest to scheme.onSurfaceVariant
        else tone.colors(scheme)
    val bodyColor = animateColorAsState(body, tween(BodyColorMillis))
    val box = animateRectAsState(jaliBody(spec.squash), motion.fastSpatialSpec())
    val eyeSpacing = animateFloatAsState(jaliEyeSpacing(spec.squash), motion.fastSpatialSpec())
    val eyeShift = animateFloatAsState(spec.eyeShift, motion.fastSpatialSpec())
    val outline = rememberMorphingOutline(spec.outline, motion.defaultSpatialSpec())
    val animated = idle && !LocalReduceMotion.current
    val idleLoop = if (animated) key(spec.idle) { rememberLoop(spec.idle.periodMillis) } else null
    val blinkLoop = if (animated && spec.eye.blinks) rememberLoop(BlinkMillis) else null
    val zLoop = if (animated && spec.sleeping) rememberLoop(ZMillis) else null
    val zLetters = if (spec.sleeping) rememberZLetters() else null
    val zColor = scheme.onSurfaceVariant
    val description =
        if (contentDescription == null) Modifier
        else
            Modifier.semantics {
                this.contentDescription = contentDescription
                role = Role.Image
            }

    Canvas(modifier.size(size.coerceIn(MinSize, MaxSize)).then(description)) {
        val unit = this.size.width / JaliArtboard
        scale(unit, unit, pivot = Offset.Zero) {
            val pose = idleLoop?.let { spec.idle.motion.pose(it.value) } ?: IdlePose.Rest
            val bodyBox = box.value
            withTransform({
                translate(pose.translationX, pose.translationY)
                rotate(pose.rotation, IdleOrigin)
                scale(pose.scaleX, pose.scaleY, IdleOrigin)
            }) {
                drawPath(outlinePath(outline.value, bodyBox), bodyColor.value)
                val blink = blinkLoop?.let { jaliBlinkScale(it.value) } ?: 1f
                listOf(-1f, 1f).forEach { side ->
                    withTransform({
                        translate(
                            JaliArtboard / 2 + side * eyeSpacing.value + eyeShift.value,
                            jaliEyeCenterY(bodyBox),
                        )
                        scale(side, blink, Offset.Zero)
                    }) {
                        drawEye(spec.eye, ink)
                    }
                }
            }
            if (zLetters != null) {
                val first = zLoop?.let { jaliZPose(it.value) } ?: ZPose.Rest
                val second = zLoop?.let { jaliZPose((it.value + 0.5f) % 1f) } ?: ZPose.Rest
                drawZ(zLetters.first, Offset(bodyBox.right - 6f, bodyBox.top - 8f), first, zColor)
                drawZ(
                    zLetters.second,
                    Offset(bodyBox.right + 3f, bodyBox.top - 17f),
                    second,
                    zColor,
                )
            }
        }
    }
}

@Composable
private fun rememberLoop(periodMillis: Int): State<Float> =
    rememberInfiniteTransition()
        .animateFloat(0f, 1f, infiniteRepeatable(tween(periodMillis, easing = LinearEasing)))

@Composable
private fun rememberMorphingOutline(
    outline: JaliOutline,
    spec: FiniteAnimationSpec<Float>,
): State<List<Offset>> {
    val target = remember(outline) { jaliOutline(outline) }
    val from = remember { mutableStateOf(target) }
    val to = remember { mutableStateOf(target) }
    val progress = remember { Animatable(1f) }
    LaunchedEffect(target) {
        if (target == to.value) return@LaunchedEffect
        from.value = lerp(from.value, to.value, progress.value)
        to.value = target
        progress.snapTo(0f)
        progress.animateTo(1f, spec)
    }
    return remember { derivedStateOf { lerp(from.value, to.value, progress.value) } }
}

@Composable
private fun rememberZLetters(): Pair<TextLayoutResult, TextLayoutResult> {
    val measurer = rememberTextMeasurer()
    val family = MaterialTheme.typography.titleLarge.fontFamily
    return remember(measurer, family) {
        fun measure(size: Int) =
            measurer.measure(
                "z",
                TextStyle(
                    fontFamily = family,
                    fontWeight = FontWeight(900),
                    fontSize = size.sp,
                    lineHeight = size.sp,
                ),
                density = ArtboardDensity,
            )
        measure(15) to measure(10)
    }
}

private fun lerp(from: List<Offset>, to: List<Offset>, fraction: Float): List<Offset> =
    from.zip(to) { start, stop -> start + (stop - start) * fraction }

private fun outlinePath(points: List<Offset>, box: Rect): Path =
    Path().apply {
        points.forEachIndexed { index, point ->
            val x = box.left + point.x * box.width
            val y = box.top + point.y * box.height
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }

private fun DrawScope.drawEye(eye: JaliEye, ink: Color) {
    when (eye) {
        JaliEye.Pill,
        JaliEye.Glance -> drawBar(ink, Rect(-3.5f, -7f, 3.5f, 7f), 4f)
        JaliEye.Wide -> drawBar(ink, Rect(-4.5f, -8f, 4.5f, 8f), 5f)
        JaliEye.Line -> drawBar(ink, Rect(-5.5f, 0.25f, 5.5f, 3.75f), 2f)
        JaliEye.Lid ->
            drawPath(
                Path().apply {
                    addRoundRect(
                        RoundRect(
                            Rect(-4.5f, -2f, 4.5f, 6f),
                            topLeft = CornerRadius(1f),
                            topRight = CornerRadius(1f),
                            bottomRight = CornerRadius(5f),
                            bottomLeft = CornerRadius(5f),
                        )
                    )
                },
                ink,
            )
        JaliEye.Chevron ->
            rotate(-45f, Offset(1.5f, 0f)) {
                drawBar(ink, Rect(-3f, -4.5f, 6f, -1f), 1.5f)
                drawBar(ink, Rect(-3f, -4.5f, 0.5f, 4.5f), 1.5f)
            }
        JaliEye.Happy ->
            drawArc(ink, 180f, 180f, false, Offset(-4.75f, -1.25f), ArcSize, style = ArcStroke)
        JaliEye.Content ->
            drawArc(ink, 0f, 180f, false, Offset(-4.75f, -11.25f), ArcSize, style = ArcStroke)
        JaliEye.X -> {
            rotate(45f, Offset.Zero) { drawBar(ink, Rect(-6f, -1.75f, 6f, 1.75f), 2f) }
            rotate(-45f, Offset.Zero) { drawBar(ink, Rect(-6f, -1.75f, 6f, 1.75f), 2f) }
        }
    }
}

private fun DrawScope.drawBar(ink: Color, rect: Rect, radius: Float) =
    drawRoundRect(ink, rect.topLeft, rect.size, CornerRadius(radius))

private fun DrawScope.drawZ(letter: TextLayoutResult, topLeft: Offset, pose: ZPose, color: Color) {
    val center = topLeft + Offset(letter.size.width / 2f, letter.size.height / 2f)
    withTransform({
        translate(pose.translationX, pose.translationY)
        scale(pose.scale, pose.scale, center)
    }) {
        drawText(letter, color, topLeft, alpha = pose.alpha)
    }
}

private fun JaliTone.colors(scheme: ColorScheme): Pair<Color, Color> =
    when (this) {
        JaliTone.Primary -> scheme.primary to scheme.onPrimary
        JaliTone.Container -> scheme.primaryContainer to scheme.onPrimaryContainer
        JaliTone.Tertiary -> scheme.tertiaryContainer to scheme.onTertiaryContainer
    }

private val MinSize = 16.dp
private val MaxSize = 240.dp
private val IdleOrigin = Offset(JaliArtboard / 2, JaliBaseline)
private val ArtboardDensity = Density(1f)
private val ArcSize = Size(9.5f, 12.5f)
private val ArcStroke = Stroke(width = 3.5f)
private const val BodyColorMillis = 300
private const val BlinkMillis = 4_200
private const val ZMillis = 2_400
