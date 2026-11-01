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

import dev.lscythe.app.jalvio.domain.preferences.LanguageSettings
import dev.lscythe.app.jalvio.domain.preferences.UserPreferencesRepository
import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OffByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OnByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.Unavailable
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal val playstoreOptions = ConsentOptions(analytics = OnByDefault, crashReports = OnByDefault)
internal val fossFullOptions = ConsentOptions(analytics = Unavailable, crashReports = OffByDefault)

internal class FakeUserPreferencesRepository(consent: PrivacyConsent) : UserPreferencesRepository {
    val state = MutableStateFlow(UserPreferences(onboardingDone = false, privacy = consent))
    var onboardingDoneCalls = 0
        private set

    override val preferences = state

    override suspend fun setOnboardingDone() {
        onboardingDoneCalls++
        state.update { it.copy(onboardingDone = true) }
    }

    override suspend fun setAnalyticsConsent(enabled: Boolean) {
        state.update { it.copy(privacy = it.privacy.copy(analytics = enabled)) }
    }

    override suspend fun setCrashReportsConsent(enabled: Boolean) {
        state.update { it.copy(privacy = it.privacy.copy(crashReports = enabled)) }
    }
}

internal class FakeLanguageSettings(var language: AppLanguage) : LanguageSettings {
    val chosen = mutableListOf<AppLanguage>()

    override fun current(): AppLanguage = language

    override fun set(language: AppLanguage) {
        chosen += language
        this.language = language
    }
}
