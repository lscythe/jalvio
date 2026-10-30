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
package dev.lscythe.app.jalvio.core.testing.screenshot

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsNodeInteraction
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.captureScreenRoboImage

private const val SCREENSHOT_DIRECTORY = "src/androidHostTest/screenshots"

fun captureScreenshot(name: String, darkTheme: Boolean = false, content: @Composable () -> Unit) {
    captureRoboImage(filePath = screenshotPath(name)) {
        MaterialTheme(colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()) {
            Surface(content = content)
        }
    }
}

fun SemanticsNodeInteraction.captureScreenshot(name: String) {
    captureRoboImage(filePath = screenshotPath(name))
}

@OptIn(ExperimentalRoborazziApi::class)
fun captureScreenScreenshot(name: String) {
    captureScreenRoboImage(filePath = screenshotPath(name))
}

private fun screenshotPath(name: String) = "$SCREENSHOT_DIRECTORY/$name.png"
