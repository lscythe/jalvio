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
import androidx.compose.material3.ListItemShapes
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SegmentedListShapesTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun restingItemsUseTheJoinedGroupCorners() {
        lateinit var shapes: List<ListItemShapes>
        composeRule.setContent {
            JalvioTheme { shapes = List(3) { JalvioShapes.segmentedListItem(it, 3) } }
        }
        shapes.map { it.shape } shouldBe
            listOf(
                RoundedCornerShape(20.dp, 20.dp, 6.dp, 6.dp),
                RoundedCornerShape(6.dp),
                RoundedCornerShape(6.dp, 6.dp, 20.dp, 20.dp),
            )
    }
}
