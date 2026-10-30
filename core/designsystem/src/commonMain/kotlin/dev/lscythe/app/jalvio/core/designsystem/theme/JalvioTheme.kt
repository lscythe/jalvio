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
package dev.lscythe.app.jalvio.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalReduceMotion: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { false }

internal val LocalJalvioColors = staticCompositionLocalOf {
    jalvioColors(jalvioColorScheme(ThemeSettings(), dark = false, platformScheme = null), false)
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun JalvioTheme(settings: ThemeSettings = ThemeSettings(), content: @Composable () -> Unit) {
    val dark = settings.isDark(isSystemInDarkTheme())
    val platformScheme = platformDynamicColorScheme(dark)
    val colorScheme =
        remember(settings, dark, platformScheme) {
            jalvioColorScheme(settings, dark, platformScheme)
        }
    val jalvioColors = remember(colorScheme, dark) { jalvioColors(colorScheme, dark) }
    CompositionLocalProvider(
        LocalJalvioColors provides jalvioColors,
        LocalReduceMotion provides settings.reduceMotion,
    ) {
        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            typography = rememberJalvioTypography(),
            shapes = JalvioMaterialShapes,
            motionScheme =
                if (settings.reduceMotion) MotionScheme.standard() else MotionScheme.expressive(),
            content = content,
        )
    }
}

object JalvioTheme {
    val colors: JalvioColors
        @Composable @ReadOnlyComposable get() = LocalJalvioColors.current
}
