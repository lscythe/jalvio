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
package dev.lscythe.app.jalvio.navigation

import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import dev.lscythe.app.jalvio.feature.onboarding.api.OnboardingKey
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class StartDestinationTest :
    BehaviorSpec({
        val consent = PrivacyConsent(analytics = false, crashReports = false)

        Given("a rider who has not finished onboarding") {
            Then("the app starts on onboarding") {
                startKeyFor(UserPreferences(onboardingDone = false, consent)) shouldBe OnboardingKey
            }
        }
        Given("a rider who finished onboarding") {
            Then("the app starts on the map") {
                startKeyFor(UserPreferences(onboardingDone = true, consent)) shouldBe MapKey
            }
        }
    })
