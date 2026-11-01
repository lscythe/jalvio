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
package dev.lscythe.app.jalvio.consent

import dev.lscythe.app.jalvio.domain.preferences.UserPreferencesRepository
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

private class FakeUserPreferencesRepository(initial: UserPreferences) : UserPreferencesRepository {
    val state = MutableStateFlow(initial)

    override val preferences = state

    override suspend fun setOnboardingDone() = state.update { it.copy(onboardingDone = true) }

    override suspend fun setAnalyticsConsent(enabled: Boolean) = state.update {
        it.copy(privacy = it.privacy.copy(analytics = enabled))
    }

    override suspend fun setCrashReportsConsent(enabled: Boolean) = state.update {
        it.copy(privacy = it.privacy.copy(crashReports = enabled))
    }
}

private class RecordingApplier : ConsentApplier {
    val applied = mutableListOf<PrivacyConsent>()

    override fun apply(consent: PrivacyConsent) {
        applied += consent
    }
}

class ConsentEnforcerTest :
    BehaviorSpec({
        Given("stored consent with both on") {
            When("the enforcer starts") {
                Then("the consent is applied once") {
                    runTest {
                        val repository = FakeUserPreferencesRepository(bothOn)
                        val applier = RecordingApplier()
                        ConsentEnforcer(repository, applier, backgroundScope).start()
                        runCurrent()

                        applier.applied shouldContainExactly listOf(PrivacyConsent(true, true))
                    }
                }
            }
            When("the rider turns analytics off") {
                Then("the new consent is applied") {
                    runTest {
                        val repository = FakeUserPreferencesRepository(bothOn)
                        val applier = RecordingApplier()
                        ConsentEnforcer(repository, applier, backgroundScope).start()
                        runCurrent()
                        repository.setAnalyticsConsent(false)
                        runCurrent()

                        applier.applied shouldContainExactly
                            listOf(PrivacyConsent(true, true), PrivacyConsent(false, true))
                    }
                }
            }
            When("only onboarding changes") {
                Then("consent is not applied again") {
                    runTest {
                        val repository = FakeUserPreferencesRepository(bothOn)
                        val applier = RecordingApplier()
                        ConsentEnforcer(repository, applier, backgroundScope).start()
                        runCurrent()
                        repository.setOnboardingDone()
                        runCurrent()

                        applier.applied shouldContainExactly listOf(PrivacyConsent(true, true))
                    }
                }
            }
        }
    })

private val bothOn = UserPreferences(onboardingDone = false, privacy = PrivacyConsent(true, true))
