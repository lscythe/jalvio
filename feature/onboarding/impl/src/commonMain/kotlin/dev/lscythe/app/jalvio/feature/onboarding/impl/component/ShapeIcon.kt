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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.graphics.shapes.RoundedPolygon

@Composable
internal fun ShapeIcon(
    icon: ImageVector,
    shape: RoundedPolygon,
    containerColor: Color,
    iconColor: Color,
    size: Dp,
    iconSize: Dp,
    modifier: Modifier = Modifier,
    rotation: () -> Float = { 0f },
) =
    Box(
        modifier.size(size).polygonBackground(shape, containerColor, rotation),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(iconSize))
    }
