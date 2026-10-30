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
package dev.lscythe.app.jalvio.core.designsystem.component.toast

import dev.lscythe.app.jalvio.core.designsystem.icon.BookmarkRemove
import dev.lscythe.app.jalvio.core.designsystem.icon.CheckCircle
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.Notifications
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

class JalvioToastHostStateTest :
    BehaviorSpec({
        Given("a toast host state") {
            Then("a toast stays until it is dismissed, since the host owns the timeout") {
                runTest {
                    val state = JalvioToastHostState()
                    val result = async {
                        state.show("Posted", ToastTone.Done, JalvioIcons.CheckCircle)
                    }
                    runCurrent()
                    state.currentToast?.message shouldBe "Posted"
                    advanceTimeBy(60_000)
                    runCurrent()
                    state.currentToast.shouldNotBeNull().dismiss()
                    runCurrent()
                    result.await() shouldBe ToastResult.Dismissed
                    state.currentToast.shouldBeNull()
                }
            }

            Then("toasts queue one at a time") {
                runTest {
                    val state = JalvioToastHostState()
                    backgroundScope.launch {
                        state.show("A", ToastTone.Info, JalvioIcons.Notifications)
                    }
                    backgroundScope.launch {
                        state.show("B", ToastTone.Info, JalvioIcons.Notifications)
                    }
                    runCurrent()
                    state.currentToast?.message shouldBe "A"
                    state.currentToast.shouldNotBeNull().dismiss()
                    runCurrent()
                    state.currentToast?.message shouldBe "B"
                }
            }

            Then("dismiss ends the toast early") {
                runTest {
                    val state = JalvioToastHostState()
                    val result = async {
                        state.show("A", ToastTone.Info, JalvioIcons.Notifications)
                    }
                    runCurrent()
                    state.currentToast.shouldNotBeNull().dismiss()
                    runCurrent()
                    result.await() shouldBe ToastResult.Dismissed
                    currentTime shouldBe 0
                }
            }

            Then("the action ends the toast with ActionPerformed") {
                runTest {
                    val state = JalvioToastHostState()
                    val result = async {
                        state.show(
                            "Removed",
                            ToastTone.Action,
                            JalvioIcons.BookmarkRemove,
                            action = "Undo",
                        )
                    }
                    runCurrent()
                    state.currentToast.shouldNotBeNull().performAction()
                    runCurrent()
                    result.await() shouldBe ToastResult.ActionPerformed
                }
            }

            Then("a cancelled caller closes its toast and the next one shows") {
                runTest {
                    val state = JalvioToastHostState()
                    val first = backgroundScope.launch {
                        state.show("A", ToastTone.Info, JalvioIcons.Notifications)
                    }
                    backgroundScope.launch {
                        state.show("B", ToastTone.Info, JalvioIcons.Notifications)
                    }
                    runCurrent()
                    first.cancel()
                    runCurrent()
                    state.currentToast?.message shouldBe "B"
                }
            }
        }
    })
