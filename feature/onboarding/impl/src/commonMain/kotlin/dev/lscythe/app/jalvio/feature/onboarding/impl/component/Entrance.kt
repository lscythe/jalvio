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
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalScreenRevealed
import kotlinx.coroutines.delay

enum class EntranceKind {
    Up,
    Fade,
    Grow,
    Settle,
}

object EntranceDelays {
    const val First = 60
    const val Second = 220
    const val Third = 380
    const val Fourth = 420
    const val Fifth = 460
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun rememberEntranceProgress(
    delayMillis: Int = 0,
    spec: FiniteAnimationSpec<Float> = MaterialTheme.motionScheme.slowSpatialSpec(),
): State<Float> {
    val reduceMotion = LocalReduceMotion.current
    val revealed = LocalScreenRevealed.current
    val progress = remember { Animatable(if (reduceMotion) 1f else 0f) }
    LaunchedEffect(reduceMotion, revealed) {
        if (reduceMotion) {
            progress.snapTo(1f)
        } else if (revealed) {
            delay(delayMillis.toLong())
            progress.animateTo(1f, spec)
        }
    }
    return progress.asState()
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Modifier.entrance(
    kind: EntranceKind,
    delayMillis: Int = 0,
    spec: FiniteAnimationSpec<Float>? = null,
): Modifier {
    val motion = MaterialTheme.motionScheme
    val animationSpec =
        spec
            ?: when (kind) {
                EntranceKind.Fade -> motion.defaultEffectsSpec()
                EntranceKind.Up,
                EntranceKind.Settle -> motion.defaultSpatialSpec()
                EntranceKind.Grow -> motion.slowSpatialSpec()
            }
    val progress = rememberEntranceProgress(delayMillis, animationSpec)
    return graphicsLayer {
        val frame = entranceFrame(kind, progress.value)
        alpha = frame.alpha
        translationY = frame.rise * RiseDistance.toPx()
        scaleX = frame.scale
        scaleY = frame.scale
        rotationZ = frame.rotation
    }
}

internal data class EntranceFrame(
    val alpha: Float,
    val rise: Float = 0f,
    val scale: Float = 1f,
    val rotation: Float = 0f,
)

internal fun entranceAlpha(progress: Float): Float = progress.coerceIn(0f, 1f)

internal fun entranceFrame(kind: EntranceKind, progress: Float): EntranceFrame {
    val alpha = entranceAlpha(progress)
    return when (kind) {
        EntranceKind.Up -> EntranceFrame(alpha, rise = 1f - progress)
        EntranceKind.Fade -> EntranceFrame(alpha)
        EntranceKind.Grow ->
            EntranceFrame(
                alpha,
                scale = lerp(GrowFrom, 1f, progress),
                rotation = GrowTurn * (1f - progress),
            )
        EntranceKind.Settle -> EntranceFrame(alpha, scale = lerp(SettleFrom, 1f, progress))
    }
}

private val RiseDistance = 40.dp
private const val GrowFrom = 0f
private const val GrowTurn = -60f
private const val SettleFrom = 0.86f
