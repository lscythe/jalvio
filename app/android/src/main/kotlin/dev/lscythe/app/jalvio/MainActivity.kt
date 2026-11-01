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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowCompat
import androidx.core.view.doOnPreDraw

class MainActivity : AppCompatActivity() {
    private var systemSplashGone by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setOnExitAnimationListener(::removeSystemSplash)
        super.onCreate(savedInstanceState)
        WindowCompat.enableEdgeToEdge(window)
        window.decorView.doOnPreDraw { decor ->
            decor.postDelayed({ systemSplashGone = true }, SystemSplashTimeoutMillis)
        }
        val graph = (application as JalvioApplication).graph
        setContent { JalvioApp(graph, splashStarted = systemSplashGone) }
    }

    private fun removeSystemSplash(splash: SplashScreenViewProvider) {
        val iconEnd = splash.iconAnimationStartMillis + splash.iconAnimationDurationMillis
        val remaining = (iconEnd - System.currentTimeMillis()).coerceAtLeast(0)
        splash.view.postDelayed(
            {
                splash.remove()
                systemSplashGone = true
            },
            remaining,
        )
    }
}

private const val SystemSplashTimeoutMillis = 1_000L
