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
package dev.lscythe.app.jalvio.convention

import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

private val generatedAndPreviewClasses =
    listOf(
        "*.R",
        "*.R$*",
        "*.BuildConfig",
        "*.Manifest*",
        "*.generated.*",
        "*.*Preview*",
        "*.*Previews*",
        "*.*Factory*",
        "*_Impl",
        "*_Impl$*",
        "*DatabaseConstructor",
        "dev.lscythe.app.jalvio.core.designsystem.icon.*",
    )

private val dependencyWiringClasses = listOf("*.di.*", "*Bindings", "*.NoOp*")

private val appEntryPointClasses =
    listOf(
        "dev.lscythe.app.jalvio.JalvioApplication",
        "dev.lscythe.app.jalvio.MainActivity",
        "dev.lscythe.app.jalvio.MonitoringKt",
        "dev.lscythe.app.jalvio.JalvioAppKt",
        "dev.lscythe.app.jalvio.ComposableSingletons\$JalvioAppKt",
    )

private val platformAdapterClasses =
    listOf(
        "dev.lscythe.app.jalvio.core.analytics.firebase.FirebaseAnalyticsHelper",
        "dev.lscythe.app.jalvio.core.common.dispatchers.PlatformIoDispatcher_androidKt",
        "dev.lscythe.app.jalvio.core.database.DatabasePathsKt",
        "dev.lscythe.app.jalvio.core.location.LocationManagerLocationProvider*",
        "dev.lscythe.app.jalvio.core.map.JalvioMapKt",
        "dev.lscythe.app.jalvio.core.monitoring.sentry.SentryMonitor",
        "dev.lscythe.app.jalvio.core.network.PlatformHttpEngine_androidKt",
        "dev.lscythe.app.jalvio.core.network.monitor.ConnectivityManagerNetworkMonitor*",
        "dev.lscythe.app.jalvio.core.permissions.AndroidPermissionRequester*",
        "dev.lscythe.app.jalvio.core.permissions.PermissionRequester_androidKt",
        "dev.lscythe.app.jalvio.core.ui.jank.ViewJankStateRecorder*",
    )

private val testSupportClasses = listOf("dev.lscythe.app.jalvio.core.testing.*")

private val roomSchemaAnnotations = listOf("androidx.room3.Database", "androidx.room3.Entity")

internal fun Project.configureKover() {
    pluginManager.apply("org.jetbrains.kotlinx.kover")
    extensions.configure<KoverProjectExtension> {
        reports {
            filters {
                excludes {
                    classes(
                        generatedAndPreviewClasses +
                            dependencyWiringClasses +
                            appEntryPointClasses +
                            platformAdapterClasses +
                            testSupportClasses
                    )
                    annotatedBy(*roomSchemaAnnotations.toTypedArray())
                }
            }
        }
    }
}
