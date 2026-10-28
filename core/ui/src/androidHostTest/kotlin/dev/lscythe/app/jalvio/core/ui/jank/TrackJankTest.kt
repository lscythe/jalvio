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
package dev.lscythe.app.jalvio.core.ui.jank

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TrackJankTest {
    @get:Rule val composeRule = createComposeRule()

    private val recorder = RecordingJankStateRecorder()

    @Test
    fun jankStateIsPutWhileComposedAndRemovedOnDispose() {
        var shown by mutableStateOf(true)
        composeRule.setContent {
            CompositionLocalProvider(LocalJankStateRecorder provides recorder) {
                if (shown) TrackJankState("screen", "home")
            }
        }

        recorder.states shouldBe mapOf("screen" to "home")

        shown = false
        composeRule.waitForIdle()

        recorder.states.shouldBeEmpty()
    }

    @Test
    fun scrollJankIsTrackedOnlyWhileScrolling() {
        val scrollableState = ScrollableState { it }
        lateinit var scope: CoroutineScope
        composeRule.setContent {
            scope = rememberCoroutineScope()
            CompositionLocalProvider(LocalJankStateRecorder provides recorder) {
                TrackScrollJank(scrollableState, "home:list")
            }
        }

        recorder.states.shouldBeEmpty()

        val scrolling = scope.launch { scrollableState.scroll { awaitCancellation() } }
        composeRule.waitForIdle()

        recorder.states shouldBe mapOf("home:list" to "Scrolling=true")

        scrolling.cancel()
        composeRule.waitForIdle()

        recorder.states.shouldBeEmpty()
    }
}

private class RecordingJankStateRecorder : JankStateRecorder {
    val states = mutableMapOf<String, String>()

    override fun putState(key: String, value: String) {
        states[key] = value
    }

    override fun removeState(key: String) {
        states.remove(key)
    }
}
