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

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Corners(val topStart: Dp, val topEnd: Dp, val bottomEnd: Dp, val bottomStart: Dp) {
    constructor(all: Dp) : this(all, all, all, all)

    companion object {
        fun start(outer: Dp, inner: Dp) = Corners(outer, inner, inner, outer)

        fun end(outer: Dp, inner: Dp) = Corners(inner, outer, outer, inner)

        fun top(outer: Dp, inner: Dp) = Corners(outer, outer, inner, inner)

        fun bottom(outer: Dp, inner: Dp) = Corners(inner, inner, outer, outer)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SquishButton(
    onClick: () -> Unit,
    corners: Corners,
    pressedCorners: Corners,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    height: Dp = 72.dp,
    pressedScale: Float = 0.97f,
    enabled: Boolean = true,
    contentPadding: Dp = 20.dp,
    content: @Composable RowScope.() -> Unit,
) {
    val interactions = remember { MutableInteractionSource() }
    val pressed by interactions.collectIsPressedAsState()
    val target = if (pressed) pressedCorners else corners
    val spec = MaterialTheme.motionScheme.fastSpatialSpec<Dp>()
    val topStart by animateDpAsState(target.topStart, spec)
    val topEnd by animateDpAsState(target.topEnd, spec)
    val bottomEnd by animateDpAsState(target.bottomEnd, spec)
    val bottomStart by animateDpAsState(target.bottomStart, spec)
    val scale by
        animateFloatAsState(
            if (pressed) pressedScale else 1f,
            MaterialTheme.motionScheme.fastSpatialSpec(),
        )
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart),
        color = containerColor,
        contentColor = contentColor,
        interactionSource = interactions,
        modifier =
            modifier
                .heightIn(min = height)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .alpha(if (enabled) 1f else DisabledAlpha),
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Row(
                modifier = Modifier.padding(horizontal = contentPadding, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

private const val DisabledAlpha = 0.5f
