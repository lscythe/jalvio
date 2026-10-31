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

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import dev.lscythe.app.jalvio.core.designsystem.R
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class BrandColorResourcesTest {
    @Test
    fun theLauncherIconIsAWhiteJOnTheBrandGreen() {
        assertSoftly {
            color(R.color.jalvio_icon_background) shouldBe JalvioBrandScheme.tertiary
            color(R.color.jalvio_icon_hook) shouldBe Color.White
        }
    }

    @Test fun jaliIsTheBrandBlueInLightMode() = assertJaliIsTheBrandBlue()

    @Config(qualifiers = "night")
    @Test
    fun jaliStaysTheBrandBlueInDarkMode() = assertJaliIsTheBrandBlue()

    @Test fun theLightSystemSplashUsesTheLightSurface() = assertSplashSurface(dark = false)

    @Config(qualifiers = "night")
    @Test
    fun theDarkSystemSplashUsesTheDarkSurface() = assertSplashSurface(dark = true)

    private fun assertJaliIsTheBrandBlue() {
        assertSoftly {
            color(R.color.jalvio_jali_body) shouldBe JalvioBrandScheme.primaryContainer
            color(R.color.jalvio_jali_eyes) shouldBe JalvioBrandScheme.onPrimaryContainer
        }
    }

    private fun assertSplashSurface(dark: Boolean) {
        color(R.color.jalvio_splash_background) shouldBe
            jalvioColorScheme(ThemeSettings(), dark = dark, platformScheme = null).surface
    }

    private fun color(@ColorRes id: Int) = Color(RuntimeEnvironment.getApplication().getColor(id))
}
