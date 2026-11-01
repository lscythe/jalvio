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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
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
fun Modifier.entrance(
    kind: EntranceKind,
    delayMillis: Int = 0,
    spec: FiniteAnimationSpec<Float>? = null,
): Modifier {
    val reduceMotion = LocalReduceMotion.current
    val motion = MaterialTheme.motionScheme
    val animationSpec =
        spec
            ?: if (kind == EntranceKind.Fade) motion.defaultEffectsSpec()
            else motion.slowSpatialSpec()
    val progress = remember { Animatable(if (reduceMotion) 1f else 0f) }
    LaunchedEffect(reduceMotion) {
        if (reduceMotion) {
            progress.snapTo(1f)
        } else {
            delay(delayMillis.toLong())
            progress.animateTo(1f, animationSpec)
        }
    }
    return graphicsLayer {
        val value = progress.value
        when (kind) {
            EntranceKind.Up -> translationY = (1f - value) * RiseDistance.toPx()
            EntranceKind.Fade -> alpha = value.coerceIn(0f, 1f)
            EntranceKind.Grow -> {
                scaleX = lerp(GrowFrom, 1f, value)
                scaleY = scaleX
                rotationZ = GrowTurn * (1f - value)
            }
            EntranceKind.Settle -> {
                scaleX = lerp(SettleFrom, 1f, value)
                scaleY = scaleX
            }
        }
    }
}

private val RiseDistance = 40.dp
private const val GrowFrom = 0.2f
private const val GrowTurn = -60f
private const val SettleFrom = 0.86f
