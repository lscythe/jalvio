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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.lscythe.app.jalvio.core.designsystem.component.splash.JalvioSplash
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalScreenRevealed
import dev.lscythe.app.jalvio.core.navigation.Navigator
import dev.lscythe.app.jalvio.di.JalvioGraph
import dev.lscythe.app.jalvio.feature.onboarding.impl.navigation.onboardingEntry
import dev.lscythe.app.jalvio.navigation.MapKey
import dev.lscythe.app.jalvio.navigation.MapScreen
import dev.lscythe.app.jalvio.navigation.startKeyFor
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import kotlinx.coroutines.flow.first

@Composable
fun JalvioApp(graph: JalvioGraph, splashStarted: Boolean = true) {
    CompositionLocalProvider(LocalMetroViewModelFactory provides graph.metroViewModelFactory) {
        JalvioTheme {
            Box(Modifier.fillMaxSize()) {
                Surface(modifier = Modifier.fillMaxSize()) {}
                val startKey by
                    produceState<NavKey?>(null, graph) {
                        value = startKeyFor(graph.userPreferencesRepository.preferences.first())
                    }
                var splashVisible by rememberSaveable { mutableStateOf(true) }
                CompositionLocalProvider(LocalScreenRevealed provides !splashVisible) {
                    startKey?.let { JalvioNavigation(graph, it) }
                }
                if (splashVisible) {
                    JalvioSplash(started = splashStarted, onFinished = { splashVisible = false })
                }
            }
        }
    }
}

@Composable
private fun JalvioNavigation(graph: JalvioGraph, startKey: NavKey) {
    val navigator = remember(startKey) { Navigator(startKey) }
    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.goBack() },
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        entryProvider =
            entryProvider {
                onboardingEntry(onFinished = { navigator.resetTo(MapKey) })
                entry<MapKey> { MapScreen(graph.mapStyleUrl) }
            },
    )
}
