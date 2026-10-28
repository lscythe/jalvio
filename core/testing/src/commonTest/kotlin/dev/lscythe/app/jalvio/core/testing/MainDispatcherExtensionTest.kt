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
package dev.lscythe.app.jalvio.core.testing

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherExtensionTest :
    BehaviorSpec({
        val mainDispatcher = MainDispatcherExtension()
        extension(mainDispatcher)

        Given("a spec using the main dispatcher extension") {
            When("a coroutine is launched on Dispatchers.Main") {
                var ran = false
                CoroutineScope(Dispatchers.Main).launch { ran = true }

                Then("it waits for the test dispatcher scheduler") {
                    ran.shouldBeFalse()
                    mainDispatcher.dispatcher.scheduler.advanceUntilIdle()
                    ran.shouldBeTrue()
                }
            }
        }
    })
