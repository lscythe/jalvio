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
class OnboardingScreenScreenshotTest(
    private val step: OnboardingStep,
    private val dark: Boolean,
) {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun step() {
        val theme = if (dark) "dark" else "light"
        composeRule.captureOnPhone("onboarding_${step.name.lowercase()}_$theme", Phone.Medium) {
            StillOnboarding(previewState(step), dark)
        }
    }

    companion object {
        @JvmStatic
        @Parameters(name = "{0} dark={1}")
        fun steps(): List<Array<Any>> = ShownSteps.flatMap { step ->
            listOf(false, true).map { arrayOf(step, it) }
        }

        private val ShownSteps =
            listOf(
                OnboardingStep.Welcome,
                OnboardingStep.Community,
                OnboardingStep.Independent,
                OnboardingStep.Language,
            )
    }
}
