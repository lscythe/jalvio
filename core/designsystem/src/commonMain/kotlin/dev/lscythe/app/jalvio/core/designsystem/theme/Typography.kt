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

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.generated.resources.Res
import dev.lscythe.app.jalvio.core.designsystem.generated.resources.roboto_flex
import org.jetbrains.compose.resources.Font

private const val SquashedWidth = 25f
private const val CondensedWidth = 28f
private const val NarrowWidth = 40f
private const val NormalWidth = 100f
private val Weights = listOf(400, 700, 800, 850, 900)

private const val DisplayLineHeight = 1.05f
private const val TitleLineHeight = 1.25f
private const val BodyLineHeight = 1.4f

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun rememberJalvioTypography(): Typography {
    val squashed = robotoFlex(SquashedWidth)
    val condensed = robotoFlex(CondensedWidth)
    val narrow = robotoFlex(NarrowWidth)
    val normal = robotoFlex(NormalWidth)
    return remember(squashed, condensed, narrow, normal) {
        Typography(
            displayLarge = condensed.style(72, 850, DisplayLineHeight),
            displayMedium = condensed.style(56, 850, DisplayLineHeight),
            displaySmall = condensed.style(48, 850, DisplayLineHeight),
            headlineLarge = condensed.style(36, 850, DisplayLineHeight),
            headlineMedium = narrow.style(24, 850, DisplayLineHeight),
            headlineSmall = narrow.style(20, 850, DisplayLineHeight),
            titleLarge = normal.style(18, 800, TitleLineHeight),
            titleMedium = normal.style(16, 800, TitleLineHeight),
            titleSmall = normal.style(14, 800, TitleLineHeight),
            bodyLarge = normal.style(14, 400, BodyLineHeight),
            bodyMedium = normal.style(13, 400, BodyLineHeight),
            bodySmall = normal.style(12, 400, BodyLineHeight),
            labelLarge = normal.style(15, 700, BodyLineHeight),
            labelMedium = normal.style(12, 700, BodyLineHeight),
            labelSmall = normal.style(11, 700, BodyLineHeight),
            displayLargeEmphasized = squashed.style(72, 900, DisplayLineHeight),
            displayMediumEmphasized = squashed.style(56, 900, DisplayLineHeight),
            displaySmallEmphasized = squashed.style(48, 900, DisplayLineHeight),
            headlineLargeEmphasized = squashed.style(36, 900, DisplayLineHeight),
            headlineMediumEmphasized = squashed.style(24, 900, DisplayLineHeight),
            headlineSmallEmphasized = squashed.style(20, 900, DisplayLineHeight),
            titleLargeEmphasized = narrow.style(18, 850, TitleLineHeight),
            titleMediumEmphasized = narrow.style(16, 850, TitleLineHeight),
            titleSmallEmphasized = narrow.style(14, 850, TitleLineHeight),
            bodyLargeEmphasized = normal.style(15, 700, BodyLineHeight),
            bodyMediumEmphasized = normal.style(14, 700, BodyLineHeight),
            bodySmallEmphasized = normal.style(12, 700, BodyLineHeight),
            labelLargeEmphasized = normal.style(15, 800, BodyLineHeight),
            labelMediumEmphasized = normal.style(12, 800, BodyLineHeight),
            labelSmallEmphasized = normal.style(11, 800, BodyLineHeight),
        )
    }
}

@Composable
private fun robotoFlex(width: Float): FontFamily =
    FontFamily(
        Weights.map { weight ->
            Font(
                resource = Res.font.roboto_flex,
                weight = FontWeight(weight),
                variationSettings =
                    FontVariation.Settings(
                        FontVariation.weight(weight),
                        FontVariation.width(width),
                    ),
            )
        }
    )

private fun FontFamily.style(size: Int, weight: Int, lineHeight: Float) =
    TextStyle(
        fontFamily = this,
        fontWeight = FontWeight(weight),
        fontSize = size.sp,
        lineHeight = (size * lineHeight).sp,
        letterSpacing = 0.sp,
    )
