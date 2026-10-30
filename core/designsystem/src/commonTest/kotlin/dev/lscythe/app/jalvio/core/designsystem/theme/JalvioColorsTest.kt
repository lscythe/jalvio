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

import androidx.compose.ui.graphics.Color
import com.materialkolor.palettes.TonalPalette
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class JalvioColorsTest :
    BehaviorSpec({
        val scheme = jalvioColorScheme(ThemeSettings(), dark = false, platformScheme = null)

        Given("the Jalvio light scheme") {
            Then("calm, standing, packed and info use their scheme roles") {
                val colors = jalvioColors(scheme, dark = false)
                colors.calm shouldBe
                    MeaningColor(
                        scheme.tertiary,
                        scheme.tertiaryContainer,
                        scheme.onTertiaryContainer,
                    )
                colors.standing shouldBe
                    MeaningColor(
                        scheme.secondary,
                        scheme.secondaryContainer,
                        scheme.onSecondaryContainer,
                    )
                colors.packed shouldBe
                    MeaningColor(scheme.error, scheme.errorContainer, scheme.onErrorContainer)
                colors.info shouldBe
                    MeaningColor(scheme.primary, scheme.primaryContainer, scheme.onPrimaryContainer)
                colors.arriving shouldBe colors.calm
            }
            Then("busy is amber tones 40, 90, 10 in light and 80, 30, 90 in dark") {
                val amber = TonalPalette.fromInt(0xFFA87600.toInt())
                jalvioColors(scheme, dark = false).busy shouldBe
                    MeaningColor(
                        Color(amber.tone(40)),
                        Color(amber.tone(90)),
                        Color(amber.tone(10)),
                    )
                jalvioColors(scheme, dark = true).busy shouldBe
                    MeaningColor(
                        Color(amber.tone(80)),
                        Color(amber.tone(30)),
                        Color(amber.tone(90)),
                    )
            }
            Then("route tones resolve to their roles") {
                RouteTone.Primary.container(scheme) shouldBe scheme.primary
                RouteTone.Primary.content(scheme) shouldBe scheme.onPrimary
                RouteTone.Secondary.container(scheme) shouldBe scheme.secondary
                RouteTone.Secondary.content(scheme) shouldBe scheme.onSecondary
                RouteTone.Tertiary.container(scheme) shouldBe scheme.tertiary
                RouteTone.Tertiary.content(scheme) shouldBe scheme.onTertiary
                RouteTone.TertiaryContainer.container(scheme) shouldBe scheme.tertiaryContainer
                RouteTone.TertiaryContainer.content(scheme) shouldBe scheme.onTertiaryContainer
            }
        }
        Given("a custom pink seed") {
            Then("busy stays the same amber") {
                val pink =
                    jalvioColorScheme(
                        ThemeSettings(colors = ThemeColors.Custom(Color(0xFFD94F8A))),
                        dark = false,
                        platformScheme = null,
                    )
                jalvioColors(pink, dark = false).busy shouldBe
                    jalvioColors(scheme, dark = false).busy
            }
        }
    })
