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
package dev.lscythe.app.jalvio.core.designsystem.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion

@Composable
fun LiveDot(modifier: Modifier = Modifier, label: String? = null) {
    val primary = MaterialTheme.colorScheme.primary
    val blink = if (LocalReduceMotion.current) null else rememberBlink()
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(8.dp)
                .graphicsLayer { alpha = blink?.value ?: 1f }
                .background(primary, CircleShape)
        )
        if (label != null) {
            Spacer(Modifier.width(6.dp))
            Text(label, color = primary, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun rememberBlink(): State<Float> =
    rememberInfiniteTransition()
        .animateFloat(
            initialValue = 1f,
            targetValue = DimAlpha,
            animationSpec = infiniteRepeatable(tween(HalfLoopMillis), RepeatMode.Reverse),
        )

private const val DimAlpha = 0.25f
private const val HalfLoopMillis = 800
