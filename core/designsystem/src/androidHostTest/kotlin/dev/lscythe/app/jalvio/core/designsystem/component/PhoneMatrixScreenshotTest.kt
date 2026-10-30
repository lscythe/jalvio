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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.component.jali.Jali
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliMood
import dev.lscythe.app.jalvio.core.designsystem.component.toast.JalvioToast
import dev.lscythe.app.jalvio.core.designsystem.component.toast.ToastData
import dev.lscythe.app.jalvio.core.designsystem.component.toast.ToastDuration
import dev.lscythe.app.jalvio.core.designsystem.component.toast.ToastTone
import dev.lscythe.app.jalvio.core.designsystem.icon.CloudOffFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.NotificationsFilled
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioLayout
import dev.lscythe.app.jalvio.core.designsystem.theme.RouteTone
import dev.lscythe.app.jalvio.core.designsystem.theme.captureThemedPhoneScreenshot
import dev.lscythe.app.jalvio.core.testing.screenshot.LargestPhoneWindow
import dev.lscythe.app.jalvio.core.testing.screenshot.Phone
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = LargestPhoneWindow)
class PhoneMatrixScreenshotTest(private val phone: Phone) {
    @get:Rule val composeRule = createComposeRule()

    @OptIn(ExperimentalLayoutApi::class)
    @Test
    fun crowd() =
        composeRule.captureThemedPhoneScreenshot("Crowd_phones", phone) {
            Screen {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CrowdLevel.entries.forEach { CrowdChip(it) }
                }
                (CrowdLevel.entries + null).forEach { CrowdDots(it, showLabel = true) }
            }
        }

    @OptIn(ExperimentalLayoutApi::class)
    @Test
    fun routeBadges() =
        composeRule.captureThemedPhoneScreenshot("RouteBadge_phones", phone) {
            Screen {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    RouteBadgeSize.entries.forEach { RouteBadge("13E", RouteTone.Primary, it) }
                    RouteBadge("JAK.10A", RouteTone.Tertiary, RouteBadgeSize.Row)
                }
            }
        }

    @Test
    fun liveDot() =
        composeRule.captureThemedPhoneScreenshot("LiveDot_phones", phone) {
            Screen { LiveDot(label = "Live · updated 12 s ago") }
        }

    @Test
    fun toasts() =
        composeRule.captureThemedPhoneScreenshot("Toast_phones", phone) {
            Screen {
                JalvioToast(
                    ToastData(
                        "Bus 13E is arriving at Halte Blok M",
                        ToastTone.Info,
                        JalvioIcons.NotificationsFilled,
                        action = null,
                        duration = ToastDuration.Default,
                    )
                )
                JalvioToast(
                    ToastData(
                        "You're offline. Live arrivals come back when you reconnect.",
                        ToastTone.Error,
                        JalvioIcons.CloudOffFilled,
                        action = "Try again",
                        duration = ToastDuration.Default,
                    )
                )
            }
        }

    @Test
    fun jali() =
        composeRule.captureThemedPhoneScreenshot("Jali_phones", phone) {
            Screen {
                Jali(JaliMood.Thanks, Modifier.align(Alignment.CenterHorizontally), idle = false)
                Text("Thanks for the report", style = MaterialTheme.typography.headlineLarge)
                Text(
                    "Riders on corridor 1 now know how crowded the bus is.",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

    companion object {
        @JvmStatic @Parameters(name = "{0}") fun phones() = Phone.parameters()
    }
}

@Composable
private fun Screen(content: @Composable ColumnScope.() -> Unit) =
    Column(
        Modifier.padding(JalvioLayout.screenMargin),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
