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

import android.app.Application
import dev.lscythe.app.jalvio.core.analytics.NoOpAnalyticsHelper
import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.lscythe.app.jalvio.core.monitoring.KermitMonitor
import dev.lscythe.app.jalvio.core.sync.SyncEngine
import dev.lscythe.app.jalvio.core.sync.di.SyncBindings
import dev.lscythe.app.jalvio.domain.catalog.CatalogRepository
import dev.lscythe.app.jalvio.domain.catalog.CatalogSync
import dev.zacsweers.metro.createGraphFactory
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class FossGraphTest {
    @Test
    fun analyticsIsNoOpAndMonitoringIsKermit() {
        val graph =
            createGraphFactory<AndroidAppGraph.Factory>()
                .create(RuntimeEnvironment.getApplication(), BuildEnvironment.Debug)

        graph.analyticsHelper.shouldBeInstanceOf<NoOpAnalyticsHelper>()
        graph.monitor.shouldBeInstanceOf<KermitMonitor>()
    }

    @Test
    fun theCatalogSyncsThroughTheEngine() {
        val graph =
            createGraphFactory<AndroidAppGraph.Factory>()
                .create(RuntimeEnvironment.getApplication(), BuildEnvironment.Debug)

        graph.catalogSync.shouldBeInstanceOf<CatalogSync>()
        graph.catalogRepository.shouldBeInstanceOf<CatalogRepository>()
        graph.syncEngine.shouldBeInstanceOf<SyncEngine>()
        (graph as SyncBindings).syncables.map { it.name } shouldContain "catalog"
    }
}
