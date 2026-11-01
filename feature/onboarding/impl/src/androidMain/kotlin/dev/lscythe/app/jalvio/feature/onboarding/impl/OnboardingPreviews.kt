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

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.lscythe.app.jalvio.core.designsystem.preview.JalvioPreviews
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyLeadUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyToggleUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.languageOptionsFor

internal fun previewState(step: OnboardingStep) =
    OnboardingUiState(
        step = step,
        canGoBack = step != OnboardingStep.Welcome,
        languages = languageOptionsFor(AppLanguage.Indonesian),
        privacyToggles =
            listOf(
                PrivacyToggleUi(PrivacyKind.Analytics, checked = true),
                PrivacyToggleUi(PrivacyKind.CrashReports, checked = true),
            ),
        privacyLead = PrivacyLeadUi(onByDefault = true, single = false),
    )

internal fun fossFullPrivacyState() =
    previewState(OnboardingStep.Privacy)
        .copy(
            privacyToggles = listOf(PrivacyToggleUi(PrivacyKind.CrashReports, checked = false)),
            privacyLead = PrivacyLeadUi(onByDefault = false, single = true),
        )

private class StepProvider : PreviewParameterProvider<OnboardingStep> {
    override val values = OnboardingStep.entries.asSequence()
}

@JalvioPreviews
@Composable
private fun OnboardingScreenPreview(@PreviewParameter(StepProvider::class) step: OnboardingStep) =
    JalvioTheme(ThemeSettings(reduceMotion = true)) {
        OnboardingScreen(previewState(step), onIntent = {})
    }
