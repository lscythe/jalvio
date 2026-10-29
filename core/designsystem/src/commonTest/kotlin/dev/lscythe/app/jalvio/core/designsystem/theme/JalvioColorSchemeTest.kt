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

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class JalvioColorSchemeTest :
    BehaviorSpec({
        Given("every offered style and spec") {
            Then("a scheme builds in light and dark for Jalvio and custom colors") {
                val colors =
                    listOf(
                        ThemeColors.Jalvio,
                        ThemeColors.Custom(Color(0xFFD94F8A)),
                        ThemeColors.Custom(Color.Black),
                        ThemeColors.Custom(Color.White),
                        ThemeColors.Custom(Color.Gray),
                        ThemeColors.Custom(Color.Transparent),
                    )
                ThemeStyle.entries.forAll { style ->
                    ThemeSpec.entries.forAll { spec ->
                        colors.forAll { themeColors ->
                            listOf(false, true).forAll { dark ->
                                shouldNotThrowAny {
                                    jalvioColorScheme(
                                        ThemeSettings(
                                            colors = themeColors,
                                            style = style,
                                            spec = spec,
                                        ),
                                        dark,
                                        null,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Given("dynamic color with a platform scheme") {
            Then("the platform scheme is used") {
                val phone = lightColorScheme(primary = Color.Magenta)
                jalvioColorScheme(ThemeSettings(dynamicColor = true), dark = false, phone)
                    .primary shouldBe Color.Magenta
            }
        }
        Given("dynamic color without a platform scheme") {
            Then("the Jalvio scheme is used") {
                jalvioColorScheme(ThemeSettings(dynamicColor = true), dark = false, null)
                    .toString() shouldBe
                    jalvioColorScheme(ThemeSettings(), dark = false, null).toString()
            }
        }
        Given("dynamic color with AMOLED in dark") {
            Then("the platform scheme's surfaces are black") {
                jalvioColorScheme(
                        ThemeSettings(dynamicColor = true, amoled = true),
                        dark = true,
                        darkColorScheme(),
                    )
                    .surface shouldBe Color.Black
            }
        }
        Given("AMOLED in light mode") {
            Then("surfaces stay light") {
                jalvioColorScheme(ThemeSettings(amoled = true), dark = false, null)
                    .surface shouldNotBe Color.Black
            }
        }
        Given("toAmoled") {
            Then("background and surfaces are black and other roles are unchanged") {
                val amoled = darkColorScheme().toAmoled()
                listOf(
                        amoled.background,
                        amoled.surface,
                        amoled.surfaceDim,
                        amoled.surfaceContainerLowest,
                        amoled.surfaceContainerLow,
                        amoled.surfaceContainer,
                        amoled.surfaceContainerHigh,
                        amoled.surfaceContainerHighest,
                    )
                    .forAll { it shouldBe Color.Black }
                amoled.primary shouldBe darkColorScheme().primary
            }
        }
    })
