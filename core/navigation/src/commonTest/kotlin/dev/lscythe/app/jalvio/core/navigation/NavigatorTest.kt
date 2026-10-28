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
package dev.lscythe.app.jalvio.core.navigation

import androidx.navigation3.runtime.NavKey
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly

private object StartKey : NavKey

private object DetailKey : NavKey

class NavigatorTest :
    BehaviorSpec({
        Given("a navigator at the start key") {
            Then("navigate pushes and goBack pops") {
                val navigator = Navigator(StartKey)
                navigator.navigate(DetailKey)
                navigator.backStack shouldContainExactly listOf(StartKey, DetailKey)
                navigator.goBack().shouldBeTrue()
                navigator.backStack shouldContainExactly listOf(StartKey)
            }
        }
        Given("only the start key") {
            Then("goBack refuses and keeps it") {
                val navigator = Navigator(StartKey)
                navigator.goBack().shouldBeFalse()
                navigator.backStack shouldContainExactly listOf(StartKey)
            }
        }
    })
