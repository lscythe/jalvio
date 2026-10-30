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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal enum class JaliEye(val blinks: Boolean) {
    Pill(blinks = true),
    Glance(blinks = true),
    Lid(blinks = true),
    Chevron(blinks = false),
    Happy(blinks = false),
    Content(blinks = false),
    Wide(blinks = true),
    Line(blinks = false),
    X(blinks = false),
}

internal data class JaliOutline(val lobes: Int, val amplitude: Float, val rotation: Float = 0f)

internal data class JaliIdle(val motion: JaliMotion, val periodMillis: Int)

internal class JaliMoodSpec(
    val eye: JaliEye,
    val outline: JaliOutline,
    val squash: Float,
    val idle: JaliIdle,
    val eyeShift: Float = 0f,
    val sleeping: Boolean = false,
    val muted: Boolean = false,
)

internal val JaliMood.spec: JaliMoodSpec
    get() =
        when (this) {
            JaliMood.Calm ->
                JaliMoodSpec(
                    JaliEye.Pill,
                    JaliOutline(8, 0.05f),
                    0f,
                    JaliIdle(JaliMotion.Breathe, 3_000),
                )
            JaliMood.Busy ->
                JaliMoodSpec(
                    JaliEye.Glance,
                    JaliOutline(6, 0.08f),
                    0.3f,
                    JaliIdle(JaliMotion.Bob, 1_800),
                    eyeShift = 3f,
                )
            JaliMood.Standing ->
                JaliMoodSpec(
                    JaliEye.Lid,
                    JaliOutline(4, 0.12f),
                    0.55f,
                    JaliIdle(JaliMotion.Squish, 2_200),
                )
            JaliMood.Packed ->
                JaliMoodSpec(
                    JaliEye.Chevron,
                    JaliOutline(12, 0.08f),
                    1f,
                    JaliIdle(JaliMotion.Squish, 800),
                )
            JaliMood.Thanks ->
                JaliMoodSpec(
                    JaliEye.Happy,
                    JaliOutline(5, 0.12f),
                    0f,
                    JaliIdle(JaliMotion.Hop, 1_400),
                )
            JaliMood.Late ->
                JaliMoodSpec(
                    JaliEye.Wide,
                    JaliOutline(10, 0.1f),
                    0.2f,
                    JaliIdle(JaliMotion.Shake, 1_000),
                )
            JaliMood.Home ->
                JaliMoodSpec(
                    JaliEye.Content,
                    JaliOutline(6, 0.06f),
                    0f,
                    JaliIdle(JaliMotion.Breathe, 3_600),
                )
            JaliMood.Night ->
                JaliMoodSpec(
                    JaliEye.Line,
                    JaliOutline(8, 0f),
                    0f,
                    JaliIdle(JaliMotion.Breathe, 4_400),
                    sleeping = true,
                )
            JaliMood.Error ->
                JaliMoodSpec(
                    JaliEye.X,
                    JaliOutline(4, 0.035f, PI.toFloat()),
                    0f,
                    JaliIdle(JaliMotion.Shake, 2_600),
                    muted = true,
                )
        }

internal const val JaliArtboard = 100f
internal const val JaliBaseline = 90f
internal const val JaliOutlinePoints = 96

internal fun jaliBody(squash: Float): Rect {
    val width = 80f + 16f * squash
    val height = 80f - 22f * squash
    val center = JaliArtboard / 2
    return Rect(center - width / 2, JaliBaseline - height, center + width / 2, JaliBaseline)
}

internal fun jaliOutline(outline: JaliOutline): List<Offset> =
    List(JaliOutlinePoints) { index ->
        val angle = index.toDouble() / JaliOutlinePoints * 2 * PI
        val amplitude = outline.amplitude.toDouble()
        val radius =
            0.5 * (1 - amplitude + amplitude * cos(outline.lobes * angle + outline.rotation))
        Offset((0.5 + radius * sin(angle)).toFloat(), (0.5 - radius * cos(angle)).toFloat())
    }

internal fun jaliEyeSpacing(squash: Float): Float = 12f + 4f * squash

internal fun jaliEyeCenterY(body: Rect): Float = body.top + body.height / 2
