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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

const val LargestPhoneWindow = "w430dp-h932dp"

enum class Phone(val widthDp: Int, val heightDp: Int, val fontScale: Float, val suffix: String) {
    Small(320, 640, 1f, "small"),
    Medium(360, 800, 1f, "medium"),
    Large(430, 932, 1f, "large"),
    SmallLargeFont(320, 640, 2f, "small_font2x");

    companion object {
        fun parameters(): List<Array<Any>> = entries.map { arrayOf(it) }
    }
}

fun ComposeContentTestRule.capturePhoneScreenshot(
    name: String,
    phone: Phone,
    content: @Composable () -> Unit,
) =
    captureOnPhone(name, phone) {
        MaterialTheme(colorScheme = lightColorScheme()) { Surface(content = content) }
    }

fun ComposeContentTestRule.captureOnPhone(
    name: String,
    phone: Phone,
    content: @Composable () -> Unit,
) {
    mainClock.autoAdvance = false
    releaseEarlierNativeGraphics()
    setContent {
        val density = LocalDensity.current.density
        CompositionLocalProvider(LocalDensity provides Density(density, phone.fontScale)) {
            Box(
                Modifier.size(phone.widthDp.dp, phone.heightDp.dp).testTag(PhoneTag),
                propagateMinConstraints = true,
            ) {
                content()
            }
        }
    }
    onNodeWithTag(PhoneTag).captureScreenshot("${name}_${phone.suffix}")
}

private fun releaseEarlierNativeGraphics() =
    repeat(NativeReleasePasses) {
        System.gc()
        System.runFinalization()
    }

private const val PhoneTag = "phone"
private const val NativeReleasePasses = 3
