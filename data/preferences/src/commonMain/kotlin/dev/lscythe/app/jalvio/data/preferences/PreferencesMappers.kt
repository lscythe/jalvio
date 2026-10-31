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
package dev.lscythe.app.jalvio.data.preferences

import androidx.datastore.preferences.core.Preferences
import dev.lscythe.app.jalvio.data.preferences.model.StoredPreferences
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import dev.lscythe.app.jalvio.domain.preferences.model.resolve

internal fun Preferences.toStored(): StoredPreferences =
    StoredPreferences(
        onboardingDone = this[PreferenceKeys.OnboardingDone],
        consentAnalytics = this[PreferenceKeys.ConsentAnalytics],
        consentCrashReports = this[PreferenceKeys.ConsentCrashReports],
    )

internal fun StoredPreferences.toDomain(options: ConsentOptions): UserPreferences =
    UserPreferences(
        onboardingDone = onboardingDone ?: false,
        privacy =
            PrivacyConsent(
                analytics = options.analytics.resolve(consentAnalytics),
                crashReports = options.crashReports.resolve(consentCrashReports),
            ),
    )
