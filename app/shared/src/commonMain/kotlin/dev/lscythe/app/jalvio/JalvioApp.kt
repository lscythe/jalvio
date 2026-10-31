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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.lscythe.app.jalvio.core.designsystem.component.splash.JalvioSplash
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.di.JalvioGraph

@Composable
fun JalvioApp(graph: JalvioGraph, splashStarted: Boolean = true) {
    JalvioTheme {
        Box(Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.fillMaxSize()) {}
            var splashVisible by rememberSaveable { mutableStateOf(true) }
            if (splashVisible) {
                JalvioSplash(started = splashStarted, onFinished = { splashVisible = false })
            }
        }
    }
}
