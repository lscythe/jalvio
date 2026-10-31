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
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.feature.onboarding.impl.fossFullOptions
import dev.lscythe.app.jalvio.feature.onboarding.impl.playstoreOptions
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class OnboardingUiMappersTest :
    BehaviorSpec({
        Given("Play Store options") {
            Then("both toggles show the stored consent") {
                privacyTogglesFor(
                    playstoreOptions,
                    PrivacyConsent(true, false),
                ) shouldContainExactly
                    listOf(
                        PrivacyToggleUi(PrivacyKind.Analytics, checked = true),
                        PrivacyToggleUi(PrivacyKind.CrashReports, checked = false),
                    )
            }
            Then("the lead says both are on") {
                privacyLeadFor(playstoreOptions) shouldBe
                    PrivacyLeadUi(onByDefault = true, single = false)
            }
        }
        Given("FOSS Full options") {
            Then("only the crash reports toggle shows") {
                privacyTogglesFor(
                    fossFullOptions,
                    PrivacyConsent(false, false),
                ) shouldContainExactly
                    listOf(PrivacyToggleUi(PrivacyKind.CrashReports, checked = false))
            }
            Then("the lead says the one option stays off") {
                privacyLeadFor(fossFullOptions) shouldBe
                    PrivacyLeadUi(onByDefault = false, single = true)
            }
        }
        Given("no options") {
            Then("no toggles show") {
                privacyTogglesFor(ConsentOptions.None, PrivacyConsent(false, false)).shouldBeEmpty()
            }
        }
        Given("Indonesian selected") {
            Then("English comes first and Indonesian is selected") {
                languageOptionsFor(AppLanguage.Indonesian) shouldContainExactly
                    listOf(
                        LanguageOptionUi(
                            AppLanguage.English,
                            name = "English",
                            sample = "Next bus in 2 min",
                            selected = false,
                        ),
                        LanguageOptionUi(
                            AppLanguage.Indonesian,
                            name = "Bahasa Indonesia",
                            sample = "Bus berikutnya 2 menit lagi",
                            selected = true,
                        ),
                    )
            }
        }
    })
