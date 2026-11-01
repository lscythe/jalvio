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
package dev.lscythe.app.jalvio.feature.onboarding.impl

import androidx.compose.ui.test.junit4.createComposeRule
import dev.lscythe.app.jalvio.core.testing.screenshot.LargestPhoneWindow
import dev.lscythe.app.jalvio.core.testing.screenshot.Phone
import dev.lscythe.app.jalvio.core.testing.screenshot.captureOnPhone
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep
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
class OnboardingPhoneScreenshotTest(private val phone: Phone) {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun welcome() =
        composeRule.captureOnPhone("onboarding_welcome", phone) {
            StillOnboarding(previewState(OnboardingStep.Welcome))
        }

    @Test
    fun language() =
        composeRule.captureOnPhone("onboarding_language", phone) {
            StillOnboarding(previewState(OnboardingStep.Language))
        }

    @Test
    fun privacy() =
        composeRule.captureOnPhone("onboarding_privacy", phone) {
            StillOnboarding(previewState(OnboardingStep.Privacy))
        }

    @Test
    fun offline() =
        composeRule.captureOnPhone("onboarding_offline", phone) {
            StillOnboarding(previewState(OnboardingStep.Offline))
        }

    companion object {
        @JvmStatic @Parameters(name = "{0}") fun phones() = Phone.parameters()
    }
}
