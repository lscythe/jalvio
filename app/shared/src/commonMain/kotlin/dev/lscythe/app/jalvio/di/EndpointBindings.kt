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
package dev.lscythe.app.jalvio.di

import dev.lscythe.app.jalvio.config.JalvioEndpoints
import dev.lscythe.app.jalvio.config.forEnvironment
import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.lscythe.app.jalvio.core.map.MapStyleUrl
import dev.lscythe.app.jalvio.core.network.ApiBaseUrl
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
@BindingContainer
object EndpointBindings {
    @Provides
    fun provideApiBaseUrl(environment: BuildEnvironment): ApiBaseUrl =
        ApiBaseUrl(JalvioEndpoints.forEnvironment(environment).apiBaseUrl)

    @Provides
    fun provideMapStyleUrl(environment: BuildEnvironment): MapStyleUrl =
        MapStyleUrl(JalvioEndpoints.forEnvironment(environment).mapStyleUrl)
}
