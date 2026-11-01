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

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun LocationPulse(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier
            .size(128.dp)
            .clearAndSetSemantics {}
            .entrance(EntranceKind.Grow, 100)
            .polygonBackground(
                MaterialShapes.Burst,
                colors.tertiaryContainer,
                rememberSpin(BurstSpinMillis),
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (!LocalReduceMotion.current) PulseRing()
        Box(
            Modifier.entrance(EntranceKind.Grow, 400, MaterialTheme.motionScheme.fastSpatialSpec())
                .size(28.dp)
                .background(colors.surfaceContainerLowest, CircleShape)
                .padding(4.dp)
                .background(colors.primary, CircleShape)
        )
    }
}

@Composable
private fun PulseRing() {
    val color = MaterialTheme.colorScheme.primary
    val pulse =
        rememberInfiniteTransition()
            .animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec =
                    infiniteRepeatable(
                        tween(PulseMillis, easing = EaseOut),
                        RepeatMode.Restart,
                        StartOffset(PulseDelayMillis),
                    ),
            )
    Box(
        Modifier.size(20.dp)
            .graphicsLayer {
                val scale = lerp(PulseFrom, PulseTo, pulse.value)
                scaleX = scale
                scaleY = scale
                alpha = PulseAlpha * (1f - pulse.value)
            }
            .background(color, CircleShape)
    )
}

private const val BurstSpinMillis = 20_000
private const val PulseMillis = 2_000
private const val PulseDelayMillis = 700
private const val PulseFrom = 0.6f
private const val PulseTo = 2.6f
private const val PulseAlpha = 0.6f
