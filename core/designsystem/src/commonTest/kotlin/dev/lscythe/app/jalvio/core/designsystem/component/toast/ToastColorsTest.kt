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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.designsystem.theme.jalvioColorScheme
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual

class ToastColorsTest :
    BehaviorSpec({
        Given("every toast tone in light and dark") {
            val toneColors =
                listOf(false, true).flatMap { dark ->
                    val scheme = jalvioColorScheme(ThemeSettings(), dark, platformScheme = null)
                    ToastTone.entries.map { it.colors(scheme) }
                }

            Then("the message keeps 4.5:1 contrast over the countdown band") {
                toneColors.forEach {
                    contrast(it.onContainer, it.band) shouldBeGreaterThanOrEqual 4.5
                }
            }

            Then("the band moves away from the message color, so it never lowers contrast") {
                toneColors.forEach {
                    contrast(it.onContainer, it.band) shouldBeGreaterThan
                        contrast(it.onContainer, it.container)
                }
            }
        }
    })

private fun contrast(first: Color, second: Color): Double {
    val lighter = maxOf(first.luminance(), second.luminance()).toDouble()
    val darker = minOf(first.luminance(), second.luminance()).toDouble()
    return (lighter + 0.05) / (darker + 0.05)
}
