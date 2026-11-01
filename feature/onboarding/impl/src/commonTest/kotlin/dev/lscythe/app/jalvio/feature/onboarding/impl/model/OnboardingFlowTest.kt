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

import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Account
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Community
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Done
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Independent
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Language
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Location
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Offline
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Privacy
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.OnboardingStep.Welcome
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class OnboardingFlowTest :
    BehaviorSpec({
        Given("no hidden steps") {
            Then("the steps follow the design order") {
                generateSequence(Welcome) { step ->
                        nextStep(step, emptySet()).takeUnless { it == step }
                    }
                    .toList() shouldContainExactly
                    listOf(
                        Welcome,
                        Community,
                        Independent,
                        Language,
                        Location,
                        Privacy,
                        Account,
                        Offline,
                        Done,
                    )
            }
            Then("Done stays on Done") { nextStep(Done, emptySet()) shouldBe Done }
        }
        Given("hidden steps") {
            Then("granted location is skipped") {
                nextStep(Language, setOf(Location)) shouldBe Privacy
            }
            Then("an empty privacy step is skipped") {
                nextStep(Location, setOf(Privacy)) shouldBe Account
            }
            Then("both are skipped together") {
                nextStep(Language, setOf(Location, Privacy)) shouldBe Account
            }
        }
    })
