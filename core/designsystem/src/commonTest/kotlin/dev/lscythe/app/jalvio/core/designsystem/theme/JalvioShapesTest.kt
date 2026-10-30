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
package dev.lscythe.app.jalvio.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class JalvioShapesTest :
    BehaviorSpec({
        Given("a joined group") {
            Then("a single item rounds every corner") {
                JalvioShapes.groupItem(0, 1) shouldBe RoundedCornerShape(20.dp)
            }
            Then("the first of three rounds its top") {
                JalvioShapes.groupItem(0, 3) shouldBe RoundedCornerShape(20.dp, 20.dp, 6.dp, 6.dp)
            }
            Then("the middle of three is inner on every corner") {
                JalvioShapes.groupItem(1, 3) shouldBe RoundedCornerShape(6.dp)
            }
            Then("the last of three rounds its bottom") {
                JalvioShapes.groupItem(2, 3) shouldBe RoundedCornerShape(6.dp, 6.dp, 20.dp, 20.dp)
            }
            Then("a group of two has a top and a bottom") {
                JalvioShapes.groupItem(0, 2, outer = 24.dp) shouldBe
                    RoundedCornerShape(24.dp, 24.dp, 6.dp, 6.dp)
                JalvioShapes.groupItem(1, 2, outer = 24.dp) shouldBe
                    RoundedCornerShape(6.dp, 6.dp, 24.dp, 24.dp)
            }
            Then("an index outside the group is rejected") {
                shouldThrow<IllegalArgumentException> { JalvioShapes.groupItem(3, 3) }
                shouldThrow<IllegalArgumentException> { JalvioShapes.groupItem(0, 0) }
            }
        }
    })
