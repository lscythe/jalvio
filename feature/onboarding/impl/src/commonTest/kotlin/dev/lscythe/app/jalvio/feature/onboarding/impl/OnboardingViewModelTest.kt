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

import app.cash.turbine.test
import dev.lscythe.app.jalvio.core.permissions.PermissionStatus
import dev.lscythe.app.jalvio.core.testing.MainDispatcherExtension
import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.AllowLocation
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.Back
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.Finish
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.LocationAnswered
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.LocationStatusKnown
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.Next
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SelectCrowd
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SelectLanguage
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SetPrivacy
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent.SkipIntro
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.CrowdOptionUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Account
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Community
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Done
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Language
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Location
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Privacy
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Welcome
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyLeadUi
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyToggleUi
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

private class OnboardingHarness(
    options: ConsentOptions,
    consent: PrivacyConsent,
    private val scope: TestScope,
) {
    val preferences = FakeUserPreferencesRepository(consent)
    val languages = FakeLanguageSettings(AppLanguage.English)
    val viewModel = OnboardingViewModel(preferences, languages, options)

    val state
        get() = viewModel.state.value

    fun send(vararg intents: OnboardingIntent) {
        intents.forEach(viewModel::onIntent)
        scope.runCurrent()
    }

    fun goTo(step: OnboardingStep) {
        while (state.step != step) send(Next)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun onboardingTest(
    options: ConsentOptions = playstoreOptions,
    consent: PrivacyConsent = PrivacyConsent(analytics = true, crashReports = true),
    body: suspend OnboardingHarness.() -> Unit,
) = runTest {
    val harness = OnboardingHarness(options, consent, this)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
        harness.viewModel.state.collect {}
    }
    runCurrent()
    harness.body()
}

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest :
    BehaviorSpec({
        extension(MainDispatcherExtension(UnconfinedTestDispatcher()))

        Given("a fresh start") {
            Then("the rider is on Welcome and cannot go back") {
                onboardingTest {
                    state.step shouldBe Welcome
                    state.canGoBack.shouldBeFalse()
                }
            }
            When("Next is sent eight times") {
                Then("every step is shown in order") {
                    onboardingTest {
                        val seen = (1..8).map { send(Next).let { state.step } }
                        seen shouldContainExactly OnboardingStep.entries.drop(1)
                    }
                }
            }
            When("the intro is skipped from Community") {
                Then("the rider lands on Language and Back returns to Community") {
                    onboardingTest {
                        send(Next, SkipIntro)
                        state.step shouldBe Language
                        send(Back)
                        state.step shouldBe Community
                    }
                }
            }
        }
        Given("location already granted") {
            Then("the Location step is skipped and Back skips it too") {
                onboardingTest {
                    send(LocationStatusKnown(PermissionStatus.Granted))
                    goTo(Language)
                    send(Next)
                    state.step shouldBe Privacy
                    send(Back)
                    state.step shouldBe Language
                }
            }
        }
        Given("the Location step") {
            When("the rider allows location") {
                Then("the permission is requested") {
                    onboardingTest {
                        goTo(Location)
                        viewModel.effects.test {
                            send(AllowLocation)
                            awaitItem() shouldBe OnboardingEffect.RequestLocationPermission
                        }
                    }
                }
            }
            When("the permission is denied") {
                Then("the rider moves on to Privacy") {
                    onboardingTest {
                        goTo(Location)
                        send(LocationAnswered(PermissionStatus.Denied))
                        state.step shouldBe Privacy
                    }
                }
            }
            When("the permission is granted") {
                Then("the rider moves on to Privacy") {
                    onboardingTest {
                        goTo(Location)
                        send(LocationAnswered(PermissionStatus.Granted))
                        state.step shouldBe Privacy
                    }
                }
            }
        }
        Given("FOSS options") {
            Then("Privacy is skipped and Back from Account returns to Location") {
                onboardingTest(options = ConsentOptions.None) {
                    goTo(Location)
                    send(Next)
                    state.step shouldBe Account
                    state.privacyToggles.shouldBeEmpty()
                    send(Back)
                    state.step shouldBe Location
                }
            }
        }
        Given("FOSS Full options") {
            Then("the Privacy step shows one crash reports card that is off") {
                onboardingTest(
                    options = fossFullOptions,
                    consent = PrivacyConsent(analytics = false, crashReports = false),
                ) {
                    goTo(Privacy)
                    state.privacyToggles shouldContainExactly
                        listOf(PrivacyToggleUi(PrivacyKind.CrashReports, checked = false))
                    state.privacyLead shouldBe PrivacyLeadUi(onByDefault = false, single = true)
                }
            }
        }
        Given("a rider who turned crash reports off before") {
            Then("the crash reports card stays off") {
                onboardingTest(consent = PrivacyConsent(analytics = true, crashReports = false)) {
                    goTo(Privacy)
                    state.privacyToggles shouldContainExactly
                        listOf(
                            PrivacyToggleUi(PrivacyKind.Analytics, checked = true),
                            PrivacyToggleUi(PrivacyKind.CrashReports, checked = false),
                        )
                }
            }
        }
        Given("the Privacy step") {
            When("the rider flips both switches") {
                Then("both choices are saved and shown") {
                    onboardingTest {
                        goTo(Privacy)
                        send(SetPrivacy(PrivacyKind.Analytics, enabled = false))
                        send(SetPrivacy(PrivacyKind.CrashReports, enabled = false))
                        preferences.state.value.privacy shouldBe PrivacyConsent(false, false)
                        state.privacyToggles.map { it.checked } shouldContainExactly
                            listOf(false, false)
                    }
                }
            }
        }
        Given("the Language step") {
            When("the rider picks Bahasa Indonesia") {
                Then("the app language changes and the step stays") {
                    onboardingTest {
                        goTo(Language)
                        send(SelectLanguage(AppLanguage.Indonesian))
                        languages.chosen shouldContainExactly listOf(AppLanguage.Indonesian)
                        state.languages.single { it.selected }.language shouldBe
                            AppLanguage.Indonesian
                        state.step shouldBe Language
                    }
                }
            }
        }
        Given("the Done step") {
            When("Open the map is tapped twice") {
                Then("onboarding is saved and Finished is emitted once") {
                    onboardingTest {
                        goTo(Done)
                        viewModel.effects.test {
                            send(Finish, Finish)
                            awaitItem() shouldBe OnboardingEffect.Finished
                            expectNoEvents()
                        }
                        preferences.onboardingDoneCalls shouldBeGreaterThanOrEqual 1
                        preferences.state.value.onboardingDone shouldBe true
                    }
                }
            }
        }
        Given("the Community demo card") {
            When("the rider picks Packed") {
                Then("the demo shows it and nothing is saved") {
                    onboardingTest {
                        goTo(Community)
                        send(SelectCrowd(CrowdOptionUi.Packed))
                        state.crowd shouldBe CrowdOptionUi.Packed
                        preferences.state.value.privacy shouldBe PrivacyConsent(true, true)
                    }
                }
            }
        }
    })
