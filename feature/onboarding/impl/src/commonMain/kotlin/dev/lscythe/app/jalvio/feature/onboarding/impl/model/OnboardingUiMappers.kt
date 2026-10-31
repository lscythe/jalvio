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
package dev.lscythe.app.jalvio.feature.onboarding.impl.model

import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent

private val languageSamples =
    mapOf(
        AppLanguage.English to ("English" to "Next bus in 2 min"),
        AppLanguage.Indonesian to ("Bahasa Indonesia" to "Bus berikutnya 2 menit lagi"),
    )

internal fun languageOptionsFor(selected: AppLanguage): List<LanguageOptionUi> =
    AppLanguage.entries.map { language ->
        val (name, sample) = languageSamples.getValue(language)
        LanguageOptionUi(language, name, sample, selected = language == selected)
    }

internal fun privacyTogglesFor(
    options: ConsentOptions,
    consent: PrivacyConsent,
): List<PrivacyToggleUi> = buildList {
    if (options.analytics != ConsentOption.Unavailable) {
        add(PrivacyToggleUi(PrivacyKind.Analytics, consent.analytics))
    }
    if (options.crashReports != ConsentOption.Unavailable) {
        add(PrivacyToggleUi(PrivacyKind.CrashReports, consent.crashReports))
    }
}

internal fun privacyLeadFor(options: ConsentOptions): PrivacyLeadUi {
    val offered =
        listOf(options.analytics, options.crashReports).filter { it != ConsentOption.Unavailable }
    return PrivacyLeadUi(
        onByDefault = offered.isNotEmpty() && offered.all { it == ConsentOption.OnByDefault },
        single = offered.size == 1,
    )
}
