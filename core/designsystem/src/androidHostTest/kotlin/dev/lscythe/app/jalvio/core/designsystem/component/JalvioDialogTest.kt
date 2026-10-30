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
package dev.lscythe.app.jalvio.core.designsystem.component

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.CloudOffFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import io.kotest.matchers.comparables.shouldBeGreaterThanOrEqualTo
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class JalvioDialogTest {
    @get:Rule val composeRule = createComposeRule()

    private var confirmed = 0
    private var dismissed = 0

    @Test
    fun confirmRunsTheActionOnly() {
        showDialog()
        composeRule.onNodeWithText(CONFIRM).performClick()
        confirmed shouldBe 1
        dismissed shouldBe 0
    }

    @Test
    fun safeChoiceDismisses() {
        showDialog()
        composeRule.onNodeWithText(DISMISS).performClick()
        confirmed shouldBe 0
        dismissed shouldBe 1
    }

    @Test
    fun titleIsAHeading() {
        showDialog()
        composeRule
            .onNode(hasText(TITLE) and SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
            .assertExists()
    }

    @Test
    @Config(qualifiers = "w320dp-h640dp")
    fun buttonsStayInsideTheDialogWhenTheMessageIsLongerThanTheWindow() {
        RuntimeEnvironment.setFontScale(2f)
        showDialog(
            text = List(6) { "Your saved places are removed from this phone." }.joinToString(" ")
        )
        val dialog = composeRule.onNode(isDialog()).getUnclippedBoundsInRoot()
        listOf(CONFIRM, DISMISS).forEach { label ->
            val node = composeRule.onNodeWithText(label).assertHeightIsAtLeast(56.dp)
            val button = node.getUnclippedBoundsInRoot()
            button.top shouldBeGreaterThanOrEqualTo dialog.top
            button.bottom shouldBeLessThanOrEqualTo dialog.bottom
        }
    }

    private fun showDialog(text: String = "Your saved places are removed from this phone.") =
        composeRule.setContent {
            JalvioTheme {
                JalvioDialog(
                    icon = JalvioIcons.CloudOffFilled,
                    title = TITLE,
                    text = text,
                    confirmLabel = CONFIRM,
                    onConfirm = { confirmed++ },
                    dismissLabel = DISMISS,
                    onDismissRequest = { dismissed++ },
                    tone = DialogTone.Error,
                )
            }
        }
}

private const val TITLE = "Delete your account?"
private const val CONFIRM = "Delete everything"
private const val DISMISS = "Keep my account"
