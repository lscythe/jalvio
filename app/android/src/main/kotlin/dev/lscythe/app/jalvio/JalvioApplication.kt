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
import androidx.work.Configuration
import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.lscythe.app.jalvio.di.AndroidAppGraph
import dev.zacsweers.metro.createGraphFactory

class JalvioApplication : Application(), Configuration.Provider {
    val environment: BuildEnvironment =
        if (BuildConfig.DEBUG) BuildEnvironment.Debug else BuildEnvironment.Release

    val graph: AndroidAppGraph by lazy {
        createGraphFactory<AndroidAppGraph.Factory>().create(this, environment)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(graph.syncWorkerFactory).build()

    override fun onCreate() {
        super.onCreate()
        initializeMonitoring(environment)
        graph.dailySync.schedule()
        graph.syncEngine.requestSync()
    }
}
