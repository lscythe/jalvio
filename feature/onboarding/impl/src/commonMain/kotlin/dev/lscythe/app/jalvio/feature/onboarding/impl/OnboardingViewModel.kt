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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
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
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingProgress
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.languageOptionsFor
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.nextStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.privacyLeadFor
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.privacyTogglesFor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@AssistedInject
class OnboardingViewModel(
    @Assisted private val savedState: SavedStateHandle,
    private val preferences: UserPreferencesRepository,
    private val languageSettings: LanguageSettings,
    private val consentOptions: ConsentOptions,
) : ViewModel() {
    private val progress = MutableStateFlow(restoreProgress())
    private val language = MutableStateFlow(languageSettings.current())
    private var locationGranted = false
    private var locationRequested = false
    private var finished = false
    private val effectChannel = Channel<OnboardingEffect>(Channel.BUFFERED)

    val effects: Flow<OnboardingEffect> = effectChannel.receiveAsFlow()

    val state: StateFlow<OnboardingUiState> =
        combine(progress, language, preferences.preferences) { progress, language, stored ->
                OnboardingUiState(
                    step = progress.history.last(),
                    canGoBack = progress.history.size > 1,
                    crowd = progress.crowd,
                    languages = languageOptionsFor(language),
                    privacyToggles = privacyTogglesFor(consentOptions, stored.privacy),
                    privacyLead = privacyLeadFor(consentOptions),
                    wifiOnly = progress.wifiOnly,
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OnboardingUiState())

    fun onIntent(intent: OnboardingIntent) {
        when (intent) {
            Next -> advance()
            SkipIntro -> show(OnboardingStep.Language)
            Back ->
                updateProgress {
                    if (it.history.size > 1) it.copy(history = it.history.dropLast(1)) else it
                }
            is SelectCrowd -> updateProgress { it.copy(crowd = intent.option) }
            is SelectLanguage -> selectLanguage(intent)
            is LocationStatusKnown -> locationGranted = intent.status == PermissionStatus.Granted
            AllowLocation -> requestLocation()
            is LocationAnswered -> answerLocation(intent.status)
            is SetPrivacy -> savePrivacy(intent)
            is SetWifiOnly -> updateProgress { it.copy(wifiOnly = intent.enabled) }
            Finish -> finish()
        }
    }

    private fun restoreProgress(): OnboardingProgress =
        savedState.get<String>(ProgressKey)?.let { Json.decodeFromString(it) }
            ?: OnboardingProgress()

    private fun updateProgress(transform: (OnboardingProgress) -> OnboardingProgress) {
        val updated = progress.updateAndGet(transform)
        savedState[ProgressKey] = Json.encodeToString(updated)
    }

    private fun advance() {
        val current = progress.value.history.last()
        val next = nextStep(current, hiddenSteps())
        if (next != current) show(next)
    }

    private fun show(step: OnboardingStep) {
        updateProgress { it.copy(history = it.history + step) }
    }

    private fun hiddenSteps(): Set<OnboardingStep> = buildSet {
        if (locationGranted) add(OnboardingStep.Location)
        if (!consentOptions.offersAny) add(OnboardingStep.Privacy)
    }

    private fun requestLocation() {
        if (locationRequested || progress.value.history.last() != OnboardingStep.Location) return
        locationRequested = true
        viewModelScope.launch { effectChannel.send(OnboardingEffect.RequestLocationPermission) }
    }

    private fun answerLocation(status: PermissionStatus) {
        locationRequested = false
        locationGranted = status == PermissionStatus.Granted
        if (progress.value.history.last() == OnboardingStep.Location) advance()
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

    @AssistedFactory
    @ViewModelAssistedFactoryKey(OnboardingViewModel::class)
    @ContributesIntoMap(AppScope::class)
    fun interface Factory : ViewModelAssistedFactory {
        override fun create(extras: CreationExtras): OnboardingViewModel =
            create(extras.createSavedStateHandle())

        fun create(@Assisted savedState: SavedStateHandle): OnboardingViewModel
    }
}

private const val ProgressKey = "onboarding_progress"
