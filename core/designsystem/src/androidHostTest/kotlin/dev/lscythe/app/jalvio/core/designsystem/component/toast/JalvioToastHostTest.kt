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

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import dev.lscythe.app.jalvio.core.designsystem.icon.CheckCircleFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JalvioToastHostTest {
    @get:Rule val composeRule = createComposeRule()

    private val state = JalvioToastHostState()
    private var result: ToastResult? = null

    @Test
    fun toastClosesAfterItsDuration() {
        showToast(accessibilityManager = null)
        composeRule.mainClock.advanceTimeBy(3_900)
        assertNotNull(state.currentToast)
        composeRule.mainClock.advanceTimeBy(200)
        assertNull(state.currentToast)
        assertEquals(ToastResult.Dismissed, result)
    }

    @Test
    fun accessibilityServicesExtendTheDuration() {
        showToast(accessibilityManager = ScaledTimeouts(factor = 3))
        composeRule.mainClock.advanceTimeBy(11_900)
        assertNotNull(state.currentToast)
        composeRule.mainClock.advanceTimeBy(200)
        assertNull(state.currentToast)
    }

    @Test
    fun toastIsAPoliteLiveRegion() {
        showToast(accessibilityManager = null)
        composeRule.mainClock.advanceTimeBy(100)
        composeRule
            .onNode(
                SemanticsMatcher.expectValue(SemanticsProperties.LiveRegion, LiveRegionMode.Polite)
            )
            .assertExists()
    }

    private fun showToast(accessibilityManager: AccessibilityManager?) {
        composeRule.mainClock.autoAdvance = false
        composeRule.setContent {
            CompositionLocalProvider(LocalAccessibilityManager provides accessibilityManager) {
                JalvioTheme {
                    JalvioToastHost(state)
                    LaunchedEffect(Unit) {
                        result = state.show("Posted", ToastTone.Done, JalvioIcons.CheckCircleFilled)
                    }
                }
            }
        }
    }
}

private class ScaledTimeouts(private val factor: Long) : AccessibilityManager {
    override fun calculateRecommendedTimeoutMillis(
        originalTimeoutMillis: Long,
        containsIcons: Boolean,
        containsText: Boolean,
        containsControls: Boolean,
    ): Long = originalTimeoutMillis * factor
}
