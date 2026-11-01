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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.DoubleTapGuard
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.AccountStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.CommunityStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.DoneStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.IndependentStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.LanguageStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.LocationStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.OfflineStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.PrivacyStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.content.WelcomeStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OnboardingScreen(
    state: OnboardingUiState,
    onIntent: (OnboardingIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val motion = MaterialTheme.motionScheme
    AnimatedContent(
        targetState = state.step,
        modifier = modifier.fillMaxSize(),
        transitionSpec = {
            fadeIn(motion.defaultEffectsSpec()) togetherWith fadeOut(motion.fastEffectsSpec())
        },
        label = "onboarding step",
    ) { step ->
        DoubleTapGuard {
            when (step) {
                OnboardingStep.Welcome -> WelcomeStep(state, onIntent)
                OnboardingStep.Community -> CommunityStep(state, onIntent)
                OnboardingStep.Independent -> IndependentStep(onIntent)
                OnboardingStep.Language -> LanguageStep(state, onIntent)
                OnboardingStep.Location -> LocationStep(onIntent)
                OnboardingStep.Privacy -> PrivacyStep(state, onIntent)
                OnboardingStep.Account -> AccountStep(onIntent)
                OnboardingStep.Offline -> OfflineStep(state, onIntent)
                OnboardingStep.Done -> DoneStep(onIntent)
            }
        }
    }
}
