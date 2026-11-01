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
package dev.lscythe.app.jalvio

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.lscythe.app.jalvio.di.AndroidAppGraph
import dev.zacsweers.metro.createGraphFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class FirstLaunchTest {
    @get:Rule val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val graph =
        createGraphFactory<AndroidAppGraph.Factory>()
            .create(RuntimeEnvironment.getApplication(), BuildEnvironment.Debug)

    @Test
    fun aFirstLaunchOpensOnWelcomeAfterTheSplash() {
        composeRule.setContent { JalvioApp(graph) }

        composeRule.onNodeWithText(WelcomeTitle).assertIsDisplayed()
    }

    @Test
    fun getStartedMovesOnAndBackReturnsToWelcome() {
        composeRule.setContent { JalvioApp(graph) }

        composeRule.onNodeWithText(GetStarted).performClick()
        composeRule.onNodeWithText(CommunityTitle).assertIsDisplayed()

        composeRule.runOnUiThread { composeRule.activity.onBackPressedDispatcher.onBackPressed() }
        composeRule.onNodeWithText(WelcomeTitle).assertIsDisplayed()
    }

    @Test
    fun theStepSurvivesTheActivityBeingRecreated() {
        val restoration = StateRestorationTester(composeRule)
        restoration.setContent { JalvioApp(graph) }
        composeRule.onNodeWithText(GetStarted).performClick()
        composeRule.onNodeWithText(CommunityTitle).assertIsDisplayed()

        restoration.emulateSavedInstanceStateRestore()

        composeRule.onNodeWithText(CommunityTitle).assertIsDisplayed()
    }
}

private const val WelcomeTitle = "Ride smarter, together."
private const val CommunityTitle = "Riders keep each other posted."
private const val GetStarted = "Get started"
