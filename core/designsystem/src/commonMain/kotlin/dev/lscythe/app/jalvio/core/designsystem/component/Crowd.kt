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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.EventSeatFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.GroupsFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.PersonFilled
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioColors
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.MeaningColor
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.crowd_busy
import dev.lscythe.app.jalvio.core.resources.generated.resources.crowd_calm
import dev.lscythe.app.jalvio.core.resources.generated.resources.crowd_none
import dev.lscythe.app.jalvio.core.resources.generated.resources.crowd_packed
import dev.lscythe.app.jalvio.core.resources.generated.resources.crowd_standing
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class CrowdLevel(val filledDots: Int) {
    Calm(1),
    Busy(2),
    Standing(2),
    Packed(3),
}

fun CrowdLevel.meaning(colors: JalvioColors): MeaningColor =
    when (this) {
        CrowdLevel.Calm -> colors.calm
        CrowdLevel.Busy -> colors.busy
        CrowdLevel.Standing -> colors.standing
        CrowdLevel.Packed -> colors.packed
    }

@Composable
fun CrowdChip(level: CrowdLevel, modifier: Modifier = Modifier) {
    val meaning = level.meaning(JalvioTheme.colors)
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = meaning.container,
        contentColor = meaning.onContainer,
    ) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(level.icon, contentDescription = null, modifier = Modifier.size(14.dp))
            Text(stringResource(level.label), style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun CrowdDots(level: CrowdLevel?, modifier: Modifier = Modifier, showLabel: Boolean = false) {
    val dotColors =
        crowdDotColors(level, JalvioTheme.colors, MaterialTheme.colorScheme.outlineVariant)
    val label = stringResource(level?.label ?: Res.string.crowd_none)
    Row(
        modifier.semantics(mergeDescendants = true) {
            if (!showLabel) contentDescription = label
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            dotColors.forEach { color -> Box(Modifier.size(8.dp).background(color, CircleShape)) }
        }
        if (showLabel) {
            Spacer(Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.labelMedium)
        }
    }
}

internal fun crowdDotColors(level: CrowdLevel?, colors: JalvioColors, empty: Color): List<Color> {
    val filled = level?.meaning(colors)?.accent ?: empty
    val filledDots = level?.filledDots ?: 0
    return List(DOT_COUNT) { index -> if (index < filledDots) filled else empty }
}

private const val DOT_COUNT = 3

private val CrowdLevel.icon: ImageVector
    get() =
        when (this) {
            CrowdLevel.Calm -> JalvioIcons.EventSeatFilled
            CrowdLevel.Busy,
            CrowdLevel.Standing -> JalvioIcons.PersonFilled
            CrowdLevel.Packed -> JalvioIcons.GroupsFilled
        }

private val CrowdLevel.label: StringResource
    get() =
        when (this) {
            CrowdLevel.Calm -> Res.string.crowd_calm
            CrowdLevel.Busy -> Res.string.crowd_busy
            CrowdLevel.Standing -> Res.string.crowd_standing
            CrowdLevel.Packed -> Res.string.crowd_packed
        }
