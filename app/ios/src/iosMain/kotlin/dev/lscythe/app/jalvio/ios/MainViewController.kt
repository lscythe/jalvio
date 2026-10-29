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
package dev.lscythe.app.jalvio.ios

import androidx.compose.ui.window.ComposeUIViewController
import dev.lscythe.app.jalvio.JalvioApp
import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.zacsweers.metro.createGraphFactory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform
import platform.UIKit.UIViewController

@OptIn(ExperimentalNativeApi::class)
fun MainViewController(): UIViewController {
    val environment =
        if (Platform.isDebugBinary) BuildEnvironment.Debug else BuildEnvironment.Release
    val graph = createGraphFactory<IosAppGraph.Factory>().create(environment)
    return ComposeUIViewController { JalvioApp(graph) }
}
