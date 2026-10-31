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

import androidx.compose.runtime.Immutable
import dev.lscythe.app.jalvio.core.permissions.PermissionStatus
import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.CrowdOptionUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.LanguageOptionUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyLeadUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyToggleUi

@Immutable
data class OnboardingUiState(
    val step: OnboardingStep = OnboardingStep.Welcome,
    val canGoBack: Boolean = false,
    val crowd: CrowdOptionUi = CrowdOptionUi.Seats,
    val languages: List<LanguageOptionUi> = emptyList(),
    val privacyToggles: List<PrivacyToggleUi> = emptyList(),
    val privacyLead: PrivacyLeadUi = PrivacyLeadUi(onByDefault = false, single = false),
    val wifiOnly: Boolean = true,
)

sealed interface OnboardingIntent {
    data object Next : OnboardingIntent

    data object SkipIntro : OnboardingIntent

    data object Back : OnboardingIntent

    data class SelectCrowd(val option: CrowdOptionUi) : OnboardingIntent

    data class SelectLanguage(val language: AppLanguage) : OnboardingIntent

    data class LocationStatusKnown(val status: PermissionStatus) : OnboardingIntent

    data object AllowLocation : OnboardingIntent

    data class LocationAnswered(val status: PermissionStatus) : OnboardingIntent

    data class SetPrivacy(val kind: PrivacyKind, val enabled: Boolean) : OnboardingIntent

    data class SetWifiOnly(val enabled: Boolean) : OnboardingIntent

    data object Finish : OnboardingIntent
}

sealed interface OnboardingEffect {
    data object RequestLocationPermission : OnboardingEffect

    data object Finished : OnboardingEffect
}
