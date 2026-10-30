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
package dev.lscythe.app.jalvio.core.designsystem.component

import androidx.compose.ui.graphics.Color
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.designsystem.theme.jalvioColorScheme
import dev.lscythe.app.jalvio.core.designsystem.theme.jalvioColors
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CrowdLevelTest :
    BehaviorSpec({
        Given("each crowd level") {
            val colors =
                jalvioColors(
                    jalvioColorScheme(ThemeSettings(), dark = false, platformScheme = null),
                    dark = false,
                )

            Then("dots fill 1, 2, 2 and 3") {
                CrowdLevel.entries.map { it.filledDots } shouldBe listOf(1, 2, 2, 3)
            }

            Then("levels use calm, busy, standing and packed colors") {
                CrowdLevel.entries.map { it.meaning(colors) } shouldBe
                    listOf(colors.calm, colors.busy, colors.standing, colors.packed)
            }

            Then("dots fill in the level's accent and the rest stay empty") {
                val empty = Color.Gray
                crowdDotColors(CrowdLevel.Calm, colors, empty) shouldBe
                    listOf(colors.calm.accent, empty, empty)
                crowdDotColors(CrowdLevel.Busy, colors, empty) shouldBe
                    listOf(colors.busy.accent, colors.busy.accent, empty)
                crowdDotColors(CrowdLevel.Standing, colors, empty) shouldBe
                    listOf(colors.standing.accent, colors.standing.accent, empty)
                crowdDotColors(CrowdLevel.Packed, colors, empty) shouldBe
                    List(3) { colors.packed.accent }
            }

            Then("no reports leaves every dot empty") {
                crowdDotColors(null, colors, Color.Gray) shouldBe List(3) { Color.Gray }
            }
        }
    })
