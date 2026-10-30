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
package dev.lscythe.app.jalvio.core.designsystem.component.toast

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.Close
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioElevation
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioShapes
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.toast_close
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun JalvioToastHost(state: JalvioToastHostState, modifier: Modifier = Modifier) {
    val motion = MaterialTheme.motionScheme
    AnimatedContent(
        targetState = state.currentToast,
        modifier = modifier.fillMaxWidth(),
        transitionSpec = {
            (slideInVertically(motion.defaultSpatialSpec()) { it } +
                    fadeIn(motion.defaultEffectsSpec()))
                .togetherWith(
                    slideOutVertically(motion.defaultSpatialSpec()) { it } +
                        fadeOut(motion.defaultEffectsSpec())
                )
                .using(SizeTransform(clip = false))
        },
        contentAlignment = Alignment.BottomCenter,
    ) { toast ->
        if (toast != null) {
            JalvioToast(
                toast,
                Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 114.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun JalvioToast(toast: ToastData, modifier: Modifier = Modifier) {
    val colors = toast.tone.colors(MaterialTheme.colorScheme)
    val shape = MaterialTheme.shapes.extraLarge
    Surface(
        modifier =
            modifier.fillMaxWidth().dropShadow(shape, JalvioElevation.level3).semantics {
                liveRegion = LiveRegionMode.Polite
            },
        shape = shape,
        color = colors.container,
        contentColor = colors.onContainer,
    ) {
        Box(Modifier.height(IntrinsicSize.Min)) {
            CountdownBand(toast, colors.band, Modifier.matchParentSize())
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        Modifier.size(40.dp)
                            .clip(JalvioShapes.toast.toShape())
                            .background(colors.accent),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(toast.icon, contentDescription = null, tint = colors.onAccent)
                    }
                    Text(
                        toast.message,
                        style = MaterialTheme.typography.bodyLargeEmphasized,
                        modifier = Modifier.weight(1f),
                    )
                    if (toast.action == null) {
                        IconButton(onClick = toast::dismiss) {
                            Icon(JalvioIcons.Close, stringResource(Res.string.toast_close))
                        }
                    }
                }
                if (toast.action != null) {
                    Button(
                        onClick = toast::performAction,
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = colors.accent,
                                contentColor = colors.onAccent,
                            ),
                    ) {
                        Text(toast.action)
                    }
                }
            }
        }
    }
}

@Composable
private fun CountdownBand(toast: ToastData, color: Color, modifier: Modifier) {
    val timeoutMillis = rememberTimeoutMillis(toast)
    val remaining = remember(toast) { Animatable(1f) }
    LaunchedEffect(toast) {
        launch { remaining.animateTo(0f, tween(timeoutMillis, easing = LinearEasing)) }
        delay(timeoutMillis.toLong())
        toast.dismiss()
    }
    Box(
        modifier.drawBehind {
            val width = size.width * remaining.value
            val left = if (layoutDirection == LayoutDirection.Rtl) size.width - width else 0f
            drawRect(color, topLeft = Offset(left, 0f), size = Size(width, size.height))
        }
    )
}

@Composable
private fun rememberTimeoutMillis(toast: ToastData): Int {
    val accessibilityManager = LocalAccessibilityManager.current
    return remember(toast, accessibilityManager) {
        val original = toast.duration.value.inWholeMilliseconds
        val recommended =
            accessibilityManager?.calculateRecommendedTimeoutMillis(
                original,
                containsIcons = true,
                containsText = true,
                containsControls = true,
            ) ?: original
        recommended.coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
    }
}

internal class ToneColors(
    val accent: Color,
    val onAccent: Color,
    val container: Color,
    val onContainer: Color,
) {
    val band: Color =
        lerp(
            container,
            if (onContainer.luminance() < container.luminance()) Color.White else Color.Black,
            BandShift,
        )
}

internal fun ToastTone.colors(scheme: ColorScheme): ToneColors =
    when (this) {
        ToastTone.Done ->
            ToneColors(
                scheme.tertiary,
                scheme.onTertiary,
                scheme.tertiaryContainer,
                scheme.onTertiaryContainer,
            )
        ToastTone.Info ->
            ToneColors(
                scheme.primary,
                scheme.onPrimary,
                scheme.primaryContainer,
                scheme.onPrimaryContainer,
            )
        ToastTone.Action ->
            ToneColors(
                scheme.secondary,
                scheme.onSecondary,
                scheme.secondaryContainer,
                scheme.onSecondaryContainer,
            )
        ToastTone.Error ->
            ToneColors(scheme.error, scheme.onError, scheme.errorContainer, scheme.onErrorContainer)
    }

private const val BandShift = 0.15f
