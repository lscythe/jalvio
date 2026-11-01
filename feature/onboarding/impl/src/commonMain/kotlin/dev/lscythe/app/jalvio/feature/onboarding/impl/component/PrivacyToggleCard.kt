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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.icon.Check
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_learn_more
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun PrivacyToggleCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconContainerColor: Color,
    iconContentColor: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val motion = MaterialTheme.motionScheme
    val on by animateFloatAsState(if (checked) 1f else 0f, motion.defaultSpatialSpec())
    val morph = rememberMorph(MaterialShapes.Circle, MaterialShapes.Cookie9Sided)
    val learnMore = stringResource(Res.string.onboarding_privacy_learn_more)

    Column(
        modifier.fillMaxWidth().background(colors.surfaceContainer, shape).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            Modifier.fillMaxWidth()
                .heightIn(min = 52.dp)
                .toggleable(checked, role = Role.Switch, onValueChange = onCheckedChange),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier.size(52.dp)
                    .morphBackground(
                        morph,
                        iconContainerColor,
                        progress = { on },
                        rotation = { IconTurn * on },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = iconContentColor)
            }
            Text(
                title,
                modifier = Modifier.weight(1f),
                fontSize = 17.sp,
                lineHeight = 1.2.em,
                fontWeight = FontWeight.Bold,
            )
            Switch(
                checked = checked,
                onCheckedChange = null,
                thumbContent =
                    if (checked) {
                        { Icon(JalvioIcons.Check, null, Modifier.size(SwitchDefaults.IconSize)) }
                    } else null,
            )
        }
        Text(
            buildAnnotatedString {
                append(description)
                append(" ")
                withStyle(
                    SpanStyle(
                        color = colors.primary,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(learnMore)
                }
            },
            color = colors.onSurfaceVariant,
            fontSize = 14.sp,
            lineHeight = 1.45.em,
        )
    }
}

private const val IconTurn = 40f
