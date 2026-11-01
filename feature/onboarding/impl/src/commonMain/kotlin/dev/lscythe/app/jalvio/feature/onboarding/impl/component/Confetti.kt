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
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion

internal data class ConfettiPiece(
    val angleDegrees: Float,
    val sizeDp: Float,
    val shape: Int,
    val color: Int,
    val delayMillis: Int,
)

internal fun confettiPieces(): List<ConfettiPiece> =
    (0 until PieceCount).map { i ->
        ConfettiPiece(
            angleDegrees = (i * 18 + (i % 3) * 7).toFloat(),
            sizeDp = if (i % 3 == 0) 20f else 14f,
            shape = i % 4,
            color = i % 4,
            delayMillis = 300 + (i % 4) * 40,
        )
    }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun Confetti(modifier: Modifier = Modifier) {
    if (LocalReduceMotion.current) return
    val scheme = MaterialTheme.colorScheme
    val palette = listOf(scheme.primary, scheme.onTertiaryContainer, scheme.tertiary, Color.White)
    val paths = remember {
        listOf(
                MaterialShapes.Cookie9Sided,
                MaterialShapes.Flower,
                MaterialShapes.Clover4Leaf,
                MaterialShapes.Circle,
            )
            .map { Morph(it, it).toPath(0f) }
    }
    val pieces = remember { confettiPieces() }
    val elapsed = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        elapsed.animateTo(TotalMillis.toFloat(), tween(TotalMillis, easing = LinearEasing))
    }
    Canvas(modifier) {
        val rise = RiseDp.dp.toPx()
        pieces.forEach { piece ->
            val local = (elapsed.value - piece.delayMillis) / FlightMillis
            if (local <= 0f || local >= 1f) return@forEach
            val eased = FlightEasing.transform(local)
            val side = piece.sizeDp.dp.toPx()
            rotate(piece.angleDegrees, center) {
                translate(center.x - side / 2, center.y - side / 2 - rise * eased) {
                    rotate(SpinDegrees * eased, Offset(side / 2, side / 2)) {
                        scale(side, side, Offset.Zero) {
                            drawPath(
                                paths[piece.shape],
                                palette[piece.color],
                                alpha = 1f - eased,
                            )
                        }
                    }
                }
            }
        }
    }
}

private val FlightEasing = CubicBezierEasing(0.15f, 0.7f, 0.3f, 1f)
private const val PieceCount = 20
private const val FlightMillis = 1_200f
private const val TotalMillis = 1_620
private const val RiseDp = 190f
private const val SpinDegrees = 300f
