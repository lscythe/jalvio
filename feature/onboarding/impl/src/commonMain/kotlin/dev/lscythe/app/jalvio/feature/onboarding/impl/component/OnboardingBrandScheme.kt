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

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

internal val ColorScheme.isDark: Boolean
    get() = surface.luminance() < DarkLuminance

internal val ColorScheme.cardSurface: Color
    get() = if (isDark) surfaceContainerHigh else Color.White

private const val DarkLuminance = 0.5f

@Immutable
internal data class OnboardingBrandScheme(
    val container: Color,
    val onContainer: Color,
    val body: Color,
    val button: Color,
    val onButton: Color,
    val activeDot: Color,
    val inactiveDot: Color,
    val cardBackground: Color,
    val cardContent: Color,
    val localAccountBackground: Color,
    val localAccountContent: Color,
    val skipButton: Color,
    val lockText: Color,
    val topBarButton: Color,
    val topBarButtonContent: Color,
    val topBarTrack: Color,
    val topBarFill: Color,
    val decorationShape: Color,
)

@Composable
internal fun rememberOnboardingBrandScheme(): OnboardingBrandScheme {
    val colors = MaterialTheme.colorScheme
    return remember(colors) {
        if (colors.isDark) {
            OnboardingBrandScheme(
                container = colors.primaryContainer,
                onContainer = colors.onPrimaryContainer,
                body = colors.primary,
                button = colors.tertiary,
                onButton = colors.onTertiary,
                activeDot = colors.tertiary,
                inactiveDot = colors.onPrimaryContainer.copy(alpha = 0.35f),
                cardBackground = colors.surfaceContainerHigh,
                cardContent = colors.onSurface,
                localAccountBackground = colors.secondaryContainer,
                localAccountContent = colors.onSecondaryContainer,
                skipButton = colors.tertiary,
                lockText = colors.onPrimaryContainer.copy(alpha = 0.7f),
                topBarButton = colors.surfaceContainerHighest,
                topBarButtonContent = colors.onSurface,
                topBarTrack = colors.primary.copy(alpha = 0.25f),
                topBarFill = colors.tertiary,
                decorationShape = colors.primary.copy(alpha = 0.2f),
            )
        } else {
            OnboardingBrandScheme(
                container = colors.primary,
                onContainer = colors.onPrimary,
                body = colors.primaryContainer,
                button = colors.tertiaryContainer,
                onButton = colors.onTertiaryContainer,
                activeDot = colors.tertiaryContainer,
                inactiveDot = colors.onPrimary.copy(alpha = 0.4f),
                cardBackground = Color.White,
                cardContent = colors.onSurface,
                localAccountBackground = colors.secondaryContainer,
                localAccountContent = colors.onSecondaryContainer,
                skipButton = colors.tertiaryContainer,
                lockText = colors.primaryContainer,
                topBarButton = colors.onPrimary.copy(alpha = 0.2f),
                topBarButtonContent = colors.onPrimary,
                topBarTrack = colors.onPrimary.copy(alpha = 0.2f),
                topBarFill = colors.tertiaryContainer,
                decorationShape = colors.primaryContainer,
            )
        }
    }
}

@Immutable
internal data class OnboardingTertiaryScheme(
    val container: Color,
    val onContainer: Color,
    val button: Color,
    val onButton: Color,
    val activeDot: Color,
    val inactiveDot: Color,
    val chipBackground: Color,
    val chipContent: Color,
    val chipIconContainer: Color,
    val chipIconContent: Color,
)

@Composable
internal fun rememberOnboardingTertiaryScheme(): OnboardingTertiaryScheme {
    val colors = MaterialTheme.colorScheme
    return remember(colors) {
        if (colors.isDark) {
            OnboardingTertiaryScheme(
                container = colors.onTertiaryContainer,
                onContainer = colors.tertiaryContainer,
                button = colors.tertiaryContainer,
                onButton = colors.onTertiaryContainer,
                activeDot = colors.tertiaryContainer,
                inactiveDot = colors.tertiaryContainer.copy(alpha = 0.35f),
                chipBackground = colors.surfaceContainerHigh,
                chipContent = colors.onSurface,
                chipIconContainer = colors.tertiaryContainer,
                chipIconContent = colors.onTertiaryContainer,
            )
        } else {
            OnboardingTertiaryScheme(
                container = colors.tertiaryContainer,
                onContainer = colors.onTertiaryContainer,
                button = colors.onTertiaryContainer,
                onButton = colors.tertiaryContainer,
                activeDot = colors.tertiary,
                inactiveDot = colors.tertiary.copy(alpha = 0.35f),
                chipBackground = Color.White,
                chipContent = colors.onSurface,
                chipIconContainer = colors.tertiaryContainer,
                chipIconContent = colors.tertiary,
            )
        }
    }
}
