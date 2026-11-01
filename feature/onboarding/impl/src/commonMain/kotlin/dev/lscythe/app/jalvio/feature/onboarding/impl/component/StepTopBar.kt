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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.ArrowBack
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_back
import org.jetbrains.compose.resources.stringResource

@Immutable
internal data class StepTopBarColors(
    val button: Color,
    val buttonContent: Color,
    val track: Color,
    val fill: Color,
)

@Composable
internal fun stepTopBarColors(): StepTopBarColors {
    val colors = MaterialTheme.colorScheme
    return StepTopBarColors(
        button = colors.surfaceContainerHighest,
        buttonContent = colors.onSurface,
        track = colors.surfaceContainerHighest,
        fill = colors.primary,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun StepTopBar(
    filled: Int?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    colors: StepTopBarColors = stepTopBarColors(),
    total: Int = ProgressSegments,
) {
    Row(
        modifier.fillMaxWidth().height(56.dp).padding(horizontal = StepMargin),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SquishButton(
            onClick = onBack,
            corners = Corners(24.dp),
            pressedCorners = Corners(14.dp),
            containerColor = colors.button,
            contentColor = colors.buttonContent,
            height = 48.dp,
            contentPadding = 0.dp,
            modifier = Modifier.width(48.dp),
        ) {
            Icon(
                JalvioIcons.ArrowBack,
                contentDescription = stringResource(Res.string.onboarding_back),
            )
        }
        if (filled != null) {
            StepProgress(filled, total, colors, Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun StepProgress(
    filled: Int,
    total: Int,
    colors: StepTopBarColors,
    modifier: Modifier = Modifier,
) {
    val fill by
        rememberEntranceProgress(
            delayMillis = FillDelayMillis,
            spec = MaterialTheme.motionScheme.defaultSpatialSpec(),
        )
    Row(
        modifier.progressSemantics(filled.toFloat(), 0f..total.toFloat()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        repeat(total) { index ->
            Box(Modifier.weight(1f).height(8.dp).clip(SegmentShape).background(colors.track)) {
                if (index < filled) {
                    Box(
                        Modifier.fillMaxHeight()
                            .fillMaxWidth(if (index == filled - 1) fill.coerceIn(0f, 1f) else 1f)
                            .background(colors.fill, SegmentShape)
                    )
                }
            }
        }
    }
}

private val SegmentShape = RoundedCornerShape(4.dp)
private const val ProgressSegments = 4
private const val FillDelayMillis = 120
