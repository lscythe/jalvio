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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lscythe.app.jalvio.core.permissions.PermissionStatus
import dev.lscythe.app.jalvio.domain.preferences.LanguageSettings
import dev.lscythe.app.jalvio.domain.preferences.UserPreferencesRepository
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.AllowLocation
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.Back
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.Finish
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.LocationAnswered
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.LocationStatusKnown
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.Next
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SelectCrowd
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SelectLanguage
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SetPrivacy
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SetWifiOnly
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SkipIntro
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.CrowdOptionUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.languageOptionsFor
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.nextStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.privacyLeadFor
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.privacyTogglesFor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class OnboardingViewModel(
    private val preferences: UserPreferencesRepository,
    private val languageSettings: LanguageSettings,
    private val consentOptions: ConsentOptions,
) : ViewModel() {
    private val history = MutableStateFlow(listOf(OnboardingStep.Welcome))
    private val crowd = MutableStateFlow(CrowdOptionUi.Seats)
    private val language = MutableStateFlow(languageSettings.current())
    private val wifiOnly = MutableStateFlow(true)
    private var locationGranted = false
    private var finished = false
    private val effectChannel = Channel<OnboardingEffect>(Channel.BUFFERED)

    val effects: Flow<OnboardingEffect> = effectChannel.receiveAsFlow()

    val state: StateFlow<OnboardingUiState> =
        combine(history, crowd, language, wifiOnly, preferences.preferences) {
                steps,
                crowd,
                language,
                wifiOnly,
                stored ->
                OnboardingUiState(
                    step = steps.last(),
                    canGoBack = steps.size > 1,
                    crowd = crowd,
                    languages = languageOptionsFor(language),
                    privacyToggles = privacyTogglesFor(consentOptions, stored.privacy),
                    privacyLead = privacyLeadFor(consentOptions),
                    wifiOnly = wifiOnly,
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OnboardingUiState())

    fun onIntent(intent: OnboardingIntent) {
        when (intent) {
            Next -> advance()
            SkipIntro -> show(OnboardingStep.Language)
            Back -> history.update { if (it.size > 1) it.dropLast(1) else it }
            is SelectCrowd -> crowd.value = intent.option
            is SelectLanguage -> selectLanguage(intent)
            is LocationStatusKnown -> locationGranted = intent.status == PermissionStatus.Granted
            AllowLocation ->
                viewModelScope.launch {
                    effectChannel.send(OnboardingEffect.RequestLocationPermission)
                }
            is LocationAnswered -> {
                locationGranted = intent.status == PermissionStatus.Granted
                advance()
            }
            is SetPrivacy -> savePrivacy(intent)
            is SetWifiOnly -> wifiOnly.value = intent.enabled
            Finish -> finish()
        }
    }

    private fun advance() {
        val current = history.value.last()
        val next = nextStep(current, hiddenSteps())
        if (next != current) show(next)
    }

    private fun show(step: OnboardingStep) {
        history.update { it + step }
    }

    private fun hiddenSteps(): Set<OnboardingStep> = buildSet {
        if (locationGranted) add(OnboardingStep.Location)
        if (!consentOptions.offersAny) add(OnboardingStep.Privacy)
    }

    private fun selectLanguage(intent: SelectLanguage) {
        languageSettings.set(intent.language)
        language.value = intent.language
    }

    private fun savePrivacy(intent: SetPrivacy) {
        viewModelScope.launch {
            when (intent.kind) {
                PrivacyKind.Analytics -> preferences.setAnalyticsConsent(intent.enabled)
                PrivacyKind.CrashReports -> preferences.setCrashReportsConsent(intent.enabled)
            }
        }
    }

    private fun finish() {
        if (finished) return
        finished = true
        viewModelScope.launch {
            preferences.setOnboardingDone()
            effectChannel.send(OnboardingEffect.Finished)
        }
    }
}
