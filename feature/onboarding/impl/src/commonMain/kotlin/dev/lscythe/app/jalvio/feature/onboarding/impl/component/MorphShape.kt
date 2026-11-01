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

import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.toPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import kotlin.math.floor

class MorphShape(
    private val morph: Morph,
    private val progress: Float,
    private val rotation: Float = 0f,
) : Shape {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = morph.toPath(progress)
        val matrix = Matrix()
        matrix.translate(size.width / 2, size.height / 2)
        matrix.rotateZ(rotation)
        matrix.translate(-size.width / 2, -size.height / 2)
        matrix.scale(size.width, size.height)
        path.transform(matrix)
        return Outline.Generic(path)
    }
}

@Composable
fun rememberMorph(from: RoundedPolygon, to: RoundedPolygon): Morph =
    remember(from, to) { Morph(from, to) }

fun Modifier.morphBackground(
    morph: Morph,
    color: Color,
    progress: () -> Float,
    rotation: () -> Float = { 0f },
): Modifier = cyclingBackground({ ShapeCycle(morph, progress()) }, color, rotation)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun Modifier.cyclingBackground(
    cycle: () -> ShapeCycle,
    color: Color,
    rotation: () -> Float = { 0f },
): Modifier = drawWithCache {
    val path = Path()
    val matrix = Matrix()
    onDrawBehind {
        val frame = cycle()
        path.rewind()
        frame.morph.toPath(frame.progress, path)
        matrix.reset()
        matrix.scale(size.width, size.height)
        path.transform(matrix)
        rotate(rotation()) { drawPath(path, color) }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun Modifier.polygonBackground(
    polygon: RoundedPolygon,
    color: Color,
    rotation: () -> Float = { 0f },
): Modifier = drawWithCache {
    val path = Morph(polygon, polygon).toPath(0f)
    path.transform(Matrix().apply { scale(size.width, size.height) })
    onDrawBehind { rotate(rotation()) { drawPath(path, color) } }
}

class ShapeCycle(val morph: Morph, val progress: Float)

@Composable
fun rememberShapeCycle(
    polygons: List<RoundedPolygon>,
    stepMillis: Int,
    morphFraction: Float = DefaultMorphFraction,
): () -> ShapeCycle {
    val morphs =
        remember(polygons) {
            polygons.indices.map { Morph(polygons[it], polygons[(it + 1) % polygons.size]) }
        }
    if (LocalReduceMotion.current) return { ShapeCycle(morphs.first(), 0f) }
    val phase by
        rememberInfiniteTransition()
            .animateFloat(
                initialValue = 0f,
                targetValue = polygons.size.toFloat(),
                animationSpec =
                    infiniteRepeatable(
                        tween(stepMillis * polygons.size, easing = LinearEasing),
                        RepeatMode.Restart,
                    ),
            )
    return {
        val step = floor(phase).toInt().coerceIn(0, morphs.lastIndex)
        val local = ((phase - step) / morphFraction).coerceIn(0f, 1f)
        ShapeCycle(morphs[step], EaseOutBack.transform(local))
    }
}

@Composable
fun rememberSpin(periodMillis: Int, clockwise: Boolean = true): () -> Float {
    if (LocalReduceMotion.current) return { 0f }
    val angle by
        rememberInfiniteTransition()
            .animateFloat(
                initialValue = 0f,
                targetValue = if (clockwise) FullTurn else -FullTurn,
                animationSpec = infiniteRepeatable(tween(periodMillis, easing = LinearEasing)),
            )
    return { angle }
}

private const val DefaultMorphFraction = 0.35f
private const val FullTurn = 360f
