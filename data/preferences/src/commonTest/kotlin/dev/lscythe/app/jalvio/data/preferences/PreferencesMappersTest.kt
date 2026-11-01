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

import androidx.datastore.preferences.core.preferencesOf
import dev.lscythe.app.jalvio.data.preferences.model.StoredPreferences
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

private val nothingStored =
    StoredPreferences(onboardingDone = null, consentAnalytics = null, consentCrashReports = null)

class PreferencesMappersTest :
    BehaviorSpec({
        Given("nothing stored") {
            When("the flavor offers both options on by default") {
                Then("onboarding is not done and both are on") {
                    nothingStored.toDomain(playstoreOptions) shouldBe
                        UserPreferences(
                            onboardingDone = false,
                            privacy = PrivacyConsent(true, true),
                        )
                }
            }
            When("the flavor offers crash reports off by default") {
                Then("both are off") {
                    nothingStored.toDomain(fossFullOptions).privacy shouldBe
                        PrivacyConsent(analytics = false, crashReports = false)
                }
            }
        }
        Given("stored values") {
            Then("they beat the flavor defaults") {
                StoredPreferences(true, false, true).toDomain(playstoreOptions) shouldBe
                    UserPreferences(onboardingDone = true, privacy = PrivacyConsent(false, true))
            }
        }
        Given("a stored true for options the flavor does not offer") {
            Then("both stay off") {
                StoredPreferences(null, true, true).toDomain(ConsentOptions.None).privacy shouldBe
                    PrivacyConsent(analytics = false, crashReports = false)
            }
        }
        Given("preferences with only onboarding done") {
            Then("the consent values are missing") {
                preferencesOf(PreferenceKeys.OnboardingDone to true).toStored() shouldBe
                    StoredPreferences(
                        onboardingDone = true,
                        consentAnalytics = null,
                        consentCrashReports = null,
                    )
            }
        }
    })
