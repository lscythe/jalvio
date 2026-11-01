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
package dev.lscythe.app.jalvio.feature.onboarding.impl.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.icon.ArrowForward
import dev.lscythe.app.jalvio.core.designsystem.icon.Check
import dev.lscythe.app.jalvio.core.designsystem.icon.Download
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.Wifi
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_continue
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_download
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_later
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_pack
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_pack_contents
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_pack_size
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_title
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_offline_wifi_only
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingUiState
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepTopBar
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.morphBackground
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.polygonBackground
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberMorph
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberSpin
import kotlin.math.roundToInt
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

private enum class DownloadState {
    Idle,
    Downloading,
    Completed,
}

@Composable
internal fun OfflineStep(state: OnboardingUiState, onIntent: (OnboardingIntent) -> Unit) {
    val colors = MaterialTheme.colorScheme
    val reduceMotion = LocalReduceMotion.current
    val coroutineScope = rememberCoroutineScope()
    var downloadState by remember { mutableStateOf(DownloadState.Idle) }
    val progress = remember { Animatable(0f) }

    val isIdle = downloadState == DownloadState.Idle
    val isCompleted = downloadState == DownloadState.Completed

    StepScaffold(
        containerColor = colors.surface,
        contentColor = colors.onSurface,
        topBar = { StepTopBar(filled = null, onBack = { onIntent(OnboardingIntent.Back) }) },
        spacing = 14.dp,
    ) {
        OfflineIllustration(
            completed = isCompleted,
            modifier = Modifier.fillMaxWidth().weight(1f).heightIn(min = 190.dp),
        )
        SpringHeadline(
            stringResource(Res.string.onboarding_offline_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up, 80),
            fontSize = 60.sp,
            delayMillis = 80,
        )
        StepBody(
            stringResource(Res.string.onboarding_offline_body),
            Modifier.entrance(EntranceKind.Fade, 180),
            color = colors.onSurfaceVariant,
            fontSize = 15.sp,
        )
        Column(
            Modifier.entrance(EntranceKind.Up, 220),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            PackCard(
                progress = progress.value,
                completed = isCompleted,
            )
            WifiOnlyRow(
                checked = state.wifiOnly,
                onCheckedChange = { onIntent(OnboardingIntent.SetWifiOnly(it)) },
            )
        }
        Row(
            Modifier.fillMaxWidth().height(IntrinsicSize.Min).entrance(EntranceKind.Up, 320),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SquishButton(
                onClick = {
                    if (isIdle) {
                        downloadState = DownloadState.Downloading
                        coroutineScope.launch {
                            if (reduceMotion) {
                                progress.snapTo(1f)
                            } else {
                                progress.animateTo(
                                    targetValue = 1f,
                                    animationSpec =
                                        tween(DownloadDurationMillis, easing = LinearEasing),
                                )
                            }
                            downloadState = DownloadState.Completed
                        }
                    } else {
                        onIntent(OnboardingIntent.Next)
                    }
                },
                corners = if (isIdle) Corners.start(36.dp, 10.dp) else Corners(36.dp),
                pressedCorners = if (isIdle) Corners.start(18.dp, 8.dp) else Corners(18.dp),
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                modifier = Modifier.weight(1f).fillMaxHeight(),
            ) {
                Icon(
                    if (isIdle) JalvioIcons.Download else JalvioIcons.ArrowForward,
                    contentDescription = null,
                )
                Text(
                    if (isIdle) {
                        stringResource(Res.string.onboarding_offline_download)
                    } else {
                        stringResource(Res.string.onboarding_continue)
                    },
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            AnimatedVisibility(
                visible = isIdle,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
            ) {
                SquishButton(
                    onClick = { onIntent(OnboardingIntent.Next) },
                    corners = Corners.end(36.dp, 10.dp),
                    pressedCorners = Corners.end(18.dp, 8.dp),
                    containerColor = colors.secondaryContainer,
                    contentColor = colors.onSecondaryContainer,
                    pressedScale = 0.96f,
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    Text(
                        stringResource(Res.string.onboarding_offline_later),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun OfflineIllustration(
    completed: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val motion = MaterialTheme.motionScheme
    val badgeMorph = rememberMorph(MaterialShapes.Circle, MaterialShapes.Cookie9Sided)
    val badgeMorphProgress by
        animateFloatAsState(if (completed) 1f else 0f, motion.defaultSpatialSpec())
    val badgeContainer by
        animateColorAsState(
            if (completed) colors.tertiary else colors.primary,
            motion.defaultEffectsSpec(),
        )

    Box(
        modifier.clearAndSetSemantics {},
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier.size(190.dp)
                .entrance(EntranceKind.Grow, 60)
                .polygonBackground(
                    MaterialShapes.Flower,
                    colors.tertiaryContainer,
                    rememberSpin(FlowerSpinMillis),
                )
        )
        Box(
            Modifier.entrance(EntranceKind.Grow, 260, motion.fastSpatialSpec())
                .size(96.dp)
                .morphBackground(
                    badgeMorph,
                    badgeContainer,
                    progress = { badgeMorphProgress },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (completed) JalvioIcons.Check else JalvioIcons.Download,
                contentDescription = null,
                tint = colors.onPrimary,
                modifier = Modifier.size(44.dp),
            )
        }
    }
}

@Composable
private fun PackCard(progress: Float, completed: Boolean) {
    val colors = MaterialTheme.colorScheme
    val sizeText =
        when {
            completed -> "64 MB ✓"
            progress > 0f -> "${(progress * 64).roundToInt()} / 64 MB"
            else -> stringResource(Res.string.onboarding_offline_pack_size)
        }
    val barColor by
        animateColorAsState(
            if (completed) colors.tertiary else colors.primary,
            MaterialTheme.motionScheme.defaultEffectsSpec(),
        )

    Column(
        Modifier.fillMaxWidth()
            .background(colors.surfaceContainer, RoundedCornerShape(28.dp, 28.dp, 8.dp, 8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(Res.string.onboarding_offline_pack),
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                sizeText,
                color = colors.onSurfaceVariant,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
            )
        }
        Text(
            stringResource(Res.string.onboarding_offline_pack_contents),
            color = colors.onSurfaceVariant,
            fontSize = 13.sp,
        )
        Box(
            Modifier.fillMaxWidth()
                .height(8.dp)
                .background(colors.surfaceContainerHighest, RoundedCornerShape(4.dp))
        ) {
            if (progress > 0f) {
                Box(
                    Modifier.fillMaxHeight()
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .background(barColor, RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

@Composable
private fun WifiOnlyRow(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(
        Modifier.fillMaxWidth()
            .background(colors.surfaceContainer, RoundedCornerShape(8.dp, 8.dp, 28.dp, 28.dp))
            .toggleable(checked, role = Role.Switch, onValueChange = onCheckedChange)
            .heightIn(min = 56.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            JalvioIcons.Wifi,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(22.dp),
        )
        Text(
            stringResource(Res.string.onboarding_offline_wifi_only),
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
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
}

private const val FlowerSpinMillis = 24_000
private const val DownloadDurationMillis = 2_400
