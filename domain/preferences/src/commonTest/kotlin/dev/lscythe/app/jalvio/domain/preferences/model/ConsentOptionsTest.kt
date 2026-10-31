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
package dev.lscythe.app.jalvio.domain.preferences.model

import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OffByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OnByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.Unavailable
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class ConsentOptionsTest :
    BehaviorSpec({
        Given("no stored choice") {
            Then("an option on by default resolves to on") {
                OnByDefault.resolve(null).shouldBeTrue()
            }
            Then("an option off by default resolves to off") {
                OffByDefault.resolve(null).shouldBeFalse()
            }
            Then("an unavailable option resolves to off") {
                Unavailable.resolve(null).shouldBeFalse()
            }
        }
        Given("a stored choice") {
            Then("it beats a default of on") { OnByDefault.resolve(false).shouldBeFalse() }
            Then("it beats a default of off") { OffByDefault.resolve(true).shouldBeTrue() }
            Then("an unavailable option stays off") { Unavailable.resolve(true).shouldBeFalse() }
        }
        Given("the options a flavor offers") {
            Then("none offers nothing") { ConsentOptions.None.offersAny.shouldBeFalse() }
            Then("crash reports alone offer something") {
                ConsentOptions(analytics = Unavailable, crashReports = OffByDefault)
                    .offersAny
                    .shouldBeTrue()
            }
        }
    })
