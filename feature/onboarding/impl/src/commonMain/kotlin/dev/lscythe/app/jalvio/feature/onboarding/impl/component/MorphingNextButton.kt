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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.ArrowForward
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun MorphingNextButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val interactions = remember { MutableInteractionSource() }
    val pressed by interactions.collectIsPressedAsState()
    val press by
        animateFloatAsState(if (pressed) 1f else 0f, MaterialTheme.motionScheme.fastSpatialSpec())
    val morph = rememberMorph(MaterialShapes.Circle, MaterialShapes.Cookie9Sided)
    Surface(
        onClick = onClick,
        shape = MorphShape(morph, press, rotation = PressedTurn * press),
        color = colors.primary,
        contentColor = colors.onPrimary,
        interactionSource = interactions,
        modifier =
            modifier.size(80.dp).graphicsLayer {
                val scale = 1f - (1f - PressedScale) * press
                scaleX = scale
                scaleY = scale
            },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(JalvioIcons.ArrowForward, contentDescription, Modifier.size(32.dp))
        }
    }
}

private const val PressedTurn = 20f
private const val PressedScale = 0.94f
