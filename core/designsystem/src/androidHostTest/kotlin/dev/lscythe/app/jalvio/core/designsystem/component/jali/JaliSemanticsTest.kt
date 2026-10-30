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
package dev.lscythe.app.jalvio.core.designsystem.component.jali

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JaliSemanticsTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun jaliIsDecorativeUnlessDescribed() {
        composeRule.setContent {
            JalvioTheme {
                Column {
                    Jali(JaliMood.Calm, idle = false)
                    Jali(JaliMood.Thanks, idle = false, contentDescription = "Thanks for reporting")
                }
            }
        }
        composeRule
            .onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
            .assertCountEquals(1)
        composeRule
            .onNode(
                SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image) and
                    SemanticsMatcher.expectValue(
                        SemanticsProperties.ContentDescription,
                        listOf("Thanks for reporting"),
                    )
            )
            .assertExists()
    }

    @Test
    fun sizeIsClampedToTheDesignRange() {
        composeRule.setContent {
            JalvioTheme {
                Column {
                    Jali(JaliMood.Calm, Modifier.testTag("small"), size = 8.dp, idle = false)
                    Jali(JaliMood.Calm, Modifier.testTag("large"), size = 320.dp, idle = false)
                }
            }
        }
        composeRule.onNodeWithTag("small").assertWidthIsEqualTo(16.dp)
        composeRule.onNodeWithTag("large").assertWidthIsEqualTo(240.dp)
    }
}
