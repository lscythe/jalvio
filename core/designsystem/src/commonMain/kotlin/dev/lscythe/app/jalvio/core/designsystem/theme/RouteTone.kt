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

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

enum class RouteTone {
    Primary,
    Secondary,
    Tertiary,
    TertiaryContainer,
}

fun RouteTone.container(scheme: ColorScheme): Color =
    when (this) {
        RouteTone.Primary -> scheme.primary
        RouteTone.Secondary -> scheme.secondary
        RouteTone.Tertiary -> scheme.tertiary
        RouteTone.TertiaryContainer -> scheme.tertiaryContainer
    }

fun RouteTone.content(scheme: ColorScheme): Color =
    when (this) {
        RouteTone.Primary -> scheme.onPrimary
        RouteTone.Secondary -> scheme.onSecondary
        RouteTone.Tertiary -> scheme.onTertiary
        RouteTone.TertiaryContainer -> scheme.onTertiaryContainer
    }
