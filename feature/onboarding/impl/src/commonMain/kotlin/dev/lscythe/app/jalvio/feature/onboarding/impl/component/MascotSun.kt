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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.component.jali.Jali
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliMood
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliTone

@Composable
internal fun MascotSun(modifier: Modifier = Modifier) =
    Box(
        modifier.size(SunSize).entrance(EntranceKind.Grow, EntranceDelays.First),
        contentAlignment = Alignment.Center,
    ) {
        Jali(
            JaliMood.Calm,
            Modifier.graphicsLayer {
                scaleX = SunScale
                scaleY = SunScale
            },
            tone = JaliTone.Tertiary,
            size = JaliSize,
        )
    }

private val SunSize = 300.dp
private val JaliSize = 240.dp
private const val SunScale = 300f / 240f
