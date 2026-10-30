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

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing

internal data class IdlePose(
    val translationX: Float = 0f,
    val translationY: Float = 0f,
    val scaleX: Float = 1f,
    val scaleY: Float = 1f,
    val rotation: Float = 0f,
) {
    companion object {
        val Rest = IdlePose()
    }
}

internal data class ZPose(
    val alpha: Float,
    val translationX: Float,
    val translationY: Float,
    val scale: Float,
) {
    companion object {
        val Rest = ZPose(alpha = 1f, translationX = 0f, translationY = 0f, scale = 1f)
    }
}

internal enum class JaliMotion(private val frames: List<Keyframe<IdlePose>>) {
    Breathe(restAround(0.5f to IdlePose(scaleX = 1.035f, scaleY = 0.965f))),
    Bob(restAround(0.5f to IdlePose(translationY = -3f))),
    Squish(restAround(0.5f to IdlePose(scaleX = 1.06f, scaleY = 0.93f))),
    Hop(
        restAround(
            0.3f to IdlePose(translationY = -9f, scaleX = 0.95f, scaleY = 1.05f),
            0.55f to IdlePose(scaleX = 1.07f, scaleY = 0.93f),
            0.7f to IdlePose.Rest,
        )
    ),
    Shake(
        restAround(
            0.15f to IdlePose(translationX = -2f, rotation = -2f),
            0.3f to IdlePose(translationX = 2f, rotation = 2f),
            0.45f to IdlePose(translationX = -1f),
            0.6f to IdlePose.Rest,
        )
    );

    fun pose(fraction: Float): IdlePose = interpolate(fraction, frames, EaseInOut, ::lerp)
}

internal fun jaliBlinkScale(fraction: Float): Float =
    interpolate(fraction, BlinkFrames, LinearEasing, ::lerp)

internal fun jaliZPose(fraction: Float): ZPose {
    val travel = EaseOut.transform(fraction)
    return ZPose(
        alpha = interpolate(fraction, ZAlphaFrames, EaseOut, ::lerp),
        translationX = lerp(0f, 6f, travel),
        translationY = lerp(4f, -8f, travel),
        scale = lerp(0.8f, 1.1f, travel),
    )
}

internal class Keyframe<T>(val at: Float, val value: T)

private val EaseInOut = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
private val EaseOut = CubicBezierEasing(0f, 0f, 0.58f, 1f)

private val BlinkFrames =
    listOf(Keyframe(0f, 1f), Keyframe(0.93f, 1f), Keyframe(0.96f, 0.08f), Keyframe(1f, 1f))

private val ZAlphaFrames = listOf(Keyframe(0f, 0f), Keyframe(0.4f, 1f), Keyframe(1f, 0f))

private fun restAround(vararg frames: Pair<Float, IdlePose>): List<Keyframe<IdlePose>> =
    listOf(Keyframe(0f, IdlePose.Rest)) +
        frames.map { (at, pose) -> Keyframe(at, pose) } +
        Keyframe(1f, IdlePose.Rest)

private fun <T> interpolate(
    fraction: Float,
    frames: List<Keyframe<T>>,
    easing: Easing,
    lerp: (T, T, Float) -> T,
): T {
    frames
        .firstOrNull { it.at == fraction }
        ?.let {
            return it.value
        }
    val end =
        frames.indexOfFirst { it.at > fraction }.takeIf { it > 0 } ?: return frames.last().value
    val from = frames[end - 1]
    val to = frames[end]
    val local = (fraction - from.at) / (to.at - from.at)
    return lerp(from.value, to.value, easing.transform(local))
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
    start * (1 - fraction) + stop * fraction

private fun lerp(start: IdlePose, stop: IdlePose, fraction: Float): IdlePose =
    IdlePose(
        translationX = lerp(start.translationX, stop.translationX, fraction),
        translationY = lerp(start.translationY, stop.translationY, fraction),
        scaleX = lerp(start.scaleX, stop.scaleX, fraction),
        scaleY = lerp(start.scaleY, stop.scaleY, fraction),
        rotation = lerp(start.rotation, stop.rotation, fraction),
    )
