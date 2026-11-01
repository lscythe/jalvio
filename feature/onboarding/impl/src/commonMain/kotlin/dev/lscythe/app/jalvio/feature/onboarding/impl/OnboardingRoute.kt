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
package dev.lscythe.app.jalvio.feature.onboarding.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import dev.lscythe.app.jalvio.core.permissions.AppPermission
import dev.lscythe.app.jalvio.core.permissions.rememberPermissionRequester
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel

@Composable
fun OnboardingRoute(
    onFinished: () -> Unit,
    viewModel: OnboardingViewModel = assistedMetroViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissions = rememberPermissionRequester()
    val finish by rememberUpdatedState(onFinished)

    LaunchedEffect(viewModel) {
        viewModel.onIntent(
            OnboardingIntent.LocationStatusKnown(permissions.status(AppPermission.Location))
        )
        viewModel.effects.collect { effect ->
            when (effect) {
                OnboardingEffect.RequestLocationPermission ->
                    viewModel.onIntent(
                        OnboardingIntent.LocationAnswered(
                            permissions.request(AppPermission.Location)
                        )
                    )
                OnboardingEffect.Finished -> finish()
            }
        }
    }

    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        isBackEnabled = state.canGoBack,
        onBackCompleted = { viewModel.onIntent(OnboardingIntent.Back) },
    )
    OnboardingScreen(state, viewModel::onIntent)
}
