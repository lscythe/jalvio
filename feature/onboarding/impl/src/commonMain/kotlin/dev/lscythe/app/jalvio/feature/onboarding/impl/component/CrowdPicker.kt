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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.icon.AirlineSeatReclineExtra
import dev.lscythe.app.jalvio.core.designsystem.icon.EventSeatFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.GroupsFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.PersonFilled
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_community_how_full
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_community_riders
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_crowd_empty
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_crowd_packed
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_crowd_seats
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_crowd_standing
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.CrowdOptionUi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CrowdPicker(
    selected: CrowdOptionUi,
    onSelect: (CrowdOptionUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier
            .fillMaxWidth()
            .background(colors.cardSurface, RoundedCornerShape(36.dp))
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                stringResource(Res.string.onboarding_community_how_full),
                color = colors.onSurface,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                stringResource(Res.string.onboarding_community_riders),
                color = colors.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CrowdOptionUi.entries.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    row.forEach { option ->
                        CrowdOption(
                            option,
                            selected = option == selected,
                            onClick = { onSelect(option) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun CrowdOption(
    option: CrowdOptionUi,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val motion = MaterialTheme.motionScheme
    val interactions = remember { MutableInteractionSource() }
    val pressed by interactions.collectIsPressedAsState()
    val corner by animateDpAsState(if (selected) 16.dp else 32.dp, motion.defaultSpatialSpec())
    val scale by animateFloatAsState(if (pressed) 0.93f else 1f, motion.fastSpatialSpec())
    val container by
        animateColorAsState(
            if (selected) colors.primary else colors.surfaceContainer,
            motion.defaultEffectsSpec(),
        )
    val content by
        animateColorAsState(
            if (selected) colors.onPrimary else colors.onSurface,
            motion.defaultEffectsSpec(),
        )
    Row(
        modifier
            .heightIn(min = 64.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(corner))
            .background(container)
            .selectable(
                selected,
                interactions,
                indication = null,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            option.icon,
            contentDescription = null,
            tint = content,
            modifier = Modifier.size(22.dp),
        )
        Text(
            stringResource(option.label),
            color = content,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

private val CrowdOptionUi.icon: ImageVector
    get() =
        when (this) {
            CrowdOptionUi.Empty -> JalvioIcons.AirlineSeatReclineExtra
            CrowdOptionUi.Seats -> JalvioIcons.EventSeatFilled
            CrowdOptionUi.Standing -> JalvioIcons.PersonFilled
            CrowdOptionUi.Packed -> JalvioIcons.GroupsFilled
        }

private val CrowdOptionUi.label: StringResource
    get() =
        when (this) {
            CrowdOptionUi.Empty -> Res.string.onboarding_crowd_empty
            CrowdOptionUi.Seats -> Res.string.onboarding_crowd_seats
            CrowdOptionUi.Standing -> Res.string.onboarding_crowd_standing
            CrowdOptionUi.Packed -> Res.string.onboarding_crowd_packed
        }
