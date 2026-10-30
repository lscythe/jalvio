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

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class ThemeSettingsTest :
    BehaviorSpec({
        Given("each style") {
            Then("only tonal spot, neutral, vibrant and expressive support spec 2025") {
                ThemeStyle.entries.filter {
                    ThemeSpec.Spec2025 in it.supportedSpecs
                } shouldContainExactlyInAnyOrder
                    listOf(
                        ThemeStyle.TonalSpot,
                        ThemeStyle.Neutral,
                        ThemeStyle.Vibrant,
                        ThemeStyle.Expressive,
                    )
                ThemeStyle.entries.forAll { ThemeSpec.Spec2021 shouldBeIn it.supportedSpecs }
            }
        }
        Given("a 2021 only style with spec 2025 chosen") {
            Then("the effective spec is 2021 and the chosen spec is kept") {
                val settings = ThemeSettings(style = ThemeStyle.Rainbow, spec = ThemeSpec.Spec2025)
                settings.effectiveSpec shouldBe ThemeSpec.Spec2021
                settings.copy(style = ThemeStyle.TonalSpot).effectiveSpec shouldBe
                    ThemeSpec.Spec2025
            }
        }
        Given("the default settings") {
            Then("they are the Jalvio reset") {
                ThemeSettings() shouldBe
                    ThemeSettings(
                        mode = ThemeMode.System,
                        dynamicColor = false,
                        amoled = false,
                        colors = ThemeColors.Jalvio,
                        style = ThemeStyle.TonalSpot,
                        spec = ThemeSpec.Spec2025,
                        contrast = ThemeContrast.Standard,
                        reduceMotion = false,
                    )
            }
        }
        Given("each mode") {
            Then("light and dark are fixed and system follows the system") {
                ThemeSettings(mode = ThemeMode.Light).isDark(systemDark = true) shouldBe false
                ThemeSettings(mode = ThemeMode.Dark).isDark(systemDark = false) shouldBe true
                ThemeSettings(mode = ThemeMode.System).isDark(systemDark = true) shouldBe true
                ThemeSettings(mode = ThemeMode.System).isDark(systemDark = false) shouldBe false
            }
        }
    })
