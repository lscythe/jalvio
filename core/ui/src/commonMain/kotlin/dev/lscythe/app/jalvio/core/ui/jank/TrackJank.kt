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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow

private const val SCROLLING_STATE = "Scrolling=true"

@Composable
fun TrackJankState(key: String, value: String) {
    val recorder = LocalJankStateRecorder.current
    DisposableEffect(recorder, key, value) {
        recorder.putState(key, value)
        onDispose { recorder.removeState(key) }
    }
}

@Composable
fun TrackScrollJank(scrollableState: ScrollableState, stateName: String) {
    val recorder = LocalJankStateRecorder.current
    LaunchedEffect(recorder, scrollableState, stateName) {
        snapshotFlow { scrollableState.isScrollInProgress }
            .collect { scrolling ->
                if (scrolling) recorder.putState(stateName, SCROLLING_STATE)
                else recorder.removeState(stateName)
            }
    }
}
