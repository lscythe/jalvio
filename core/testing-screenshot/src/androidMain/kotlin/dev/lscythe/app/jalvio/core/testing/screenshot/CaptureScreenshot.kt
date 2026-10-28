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

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import com.github.takahirom.roborazzi.captureRoboImage

private const val SCREENSHOT_DIRECTORY = "src/androidHostTest/screenshots"

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun captureScreenshot(name: String, darkTheme: Boolean = false, content: @Composable () -> Unit) {
    captureRoboImage(filePath = "$SCREENSHOT_DIRECTORY/$name.png") {
        MaterialExpressiveTheme(
            colorScheme = if (darkTheme) darkColorScheme() else expressiveLightColorScheme()
        ) {
            Surface(content = content)
        }
    }
}
