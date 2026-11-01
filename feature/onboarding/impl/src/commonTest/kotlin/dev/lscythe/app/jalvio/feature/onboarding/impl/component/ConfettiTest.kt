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
package dev.lscythe.app.jalvio.feature.onboarding.impl.component

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class ConfettiTest :
    BehaviorSpec({
        Given("the Done celebration") {
            val pieces = confettiPieces()
            Then("it throws twenty pieces") { pieces shouldHaveSize 20 }
            Then("every third piece is large and the rest are small") {
                pieces[0] shouldBe ConfettiPiece(0f, 20f, shape = 0, color = 0, delayMillis = 300)
                pieces[5] shouldBe ConfettiPiece(104f, 14f, shape = 1, color = 1, delayMillis = 340)
            }
            Then("the last piece leaves 120 ms after the first") {
                pieces.maxOf { it.delayMillis } shouldBe 420
            }
        }
    })
