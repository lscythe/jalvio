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

import app.cash.turbine.test
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class DataStoreUserPreferencesRepositoryTest :
    BehaviorSpec({
        Given("a corrupt preferences file") {
            val repository =
                DataStoreUserPreferencesRepository(CorruptDataStore(), playstoreOptions)

            Then("the flavor defaults are emitted") {
                repository.preferences.first() shouldBe
                    UserPreferences(onboardingDone = false, privacy = PrivacyConsent(true, true))
            }
            Then("saving a choice does not fail") {
                shouldNotThrowAny {
                    repository.setAnalyticsConsent(false)
                    repository.setCrashReportsConsent(false)
                    repository.setOnboardingDone()
                }
            }
        }
        Given("an empty store") {
            val repository =
                DataStoreUserPreferencesRepository(InMemoryDataStore(), playstoreOptions)

            When("onboarding is marked done and analytics turned off") {
                Then("the preferences follow") {
                    repository.preferences.test {
                        awaitItem() shouldBe
                            UserPreferences(
                                false,
                                PrivacyConsent(analytics = true, crashReports = true),
                            )
                        repository.setOnboardingDone()
                        awaitItem().onboardingDone shouldBe true
                        repository.setAnalyticsConsent(false)
                        awaitItem() shouldBe
                            UserPreferences(
                                true,
                                PrivacyConsent(analytics = false, crashReports = true),
                            )
                        repository.setCrashReportsConsent(false)
                        awaitItem().privacy shouldBe PrivacyConsent(false, false)
                    }
                }
            }
        }
    })
