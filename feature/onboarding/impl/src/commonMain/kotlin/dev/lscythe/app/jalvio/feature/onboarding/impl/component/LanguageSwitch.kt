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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage

@Composable
internal fun LanguageSwitch(
    current: AppLanguage,
    onSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LanguageSwitchTab(
            label = "EN",
            selected = current == AppLanguage.English,
            leading = true,
            onClick = { onSelect(AppLanguage.English) },
        )
        LanguageSwitchTab(
            label = "ID",
            selected = current == AppLanguage.Indonesian,
            leading = false,
            onClick = { onSelect(AppLanguage.Indonesian) },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LanguageSwitchTab(
    label: String,
    selected: Boolean,
    leading: Boolean,
    onClick: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val motion = MaterialTheme.motionScheme
    val dark = colors.isDark
    val innerCorner by
        animateDpAsState(if (selected) OuterCorner else InnerCorner, motion.fastSpatialSpec())
    val container by
        animateColorAsState(
            when {
                selected && dark -> colors.tertiary
                selected -> colors.onTertiaryContainer
                dark -> colors.surfaceContainerHighest
                else -> Color.White
            },
            motion.defaultEffectsSpec(),
        )
    val content by
        animateColorAsState(
            when {
                selected && dark -> colors.onTertiary
                selected -> colors.tertiaryContainer
                dark -> colors.onSurface
                else -> colors.primary
            },
            motion.defaultEffectsSpec(),
        )
    val shape =
        if (leading) {
            RoundedCornerShape(OuterCorner, innerCorner, innerCorner, OuterCorner)
        } else {
            RoundedCornerShape(innerCorner, OuterCorner, OuterCorner, innerCorner)
        }
    Box(
        modifier =
            Modifier.height(40.dp)
                .clip(shape)
                .background(container)
                .selectable(
                    selected = selected,
                    interactionSource = null,
                    indication = null,
                    role = Role.RadioButton,
                    onClick = onClick,
                )
                .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, color = content, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

private val OuterCorner = 20.dp
private val InnerCorner = 6.dp
