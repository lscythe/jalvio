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

import dev.lscythe.app.jalvio.consent.ConsentEnforcer
import dev.lscythe.app.jalvio.core.analytics.AnalyticsHelper
import dev.lscythe.app.jalvio.core.monitoring.Monitor
import dev.lscythe.app.jalvio.core.sync.SyncEngine
import dev.lscythe.app.jalvio.domain.catalog.CatalogRepository
import dev.lscythe.app.jalvio.domain.catalog.CatalogSync
import dev.lscythe.app.jalvio.domain.preferences.UserPreferencesRepository
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions

interface JalvioGraph {
    val analyticsHelper: AnalyticsHelper
    val monitor: Monitor
    val syncEngine: SyncEngine
    val catalogSync: CatalogSync
    val catalogRepository: CatalogRepository
    val userPreferencesRepository: UserPreferencesRepository
    val consentOptions: ConsentOptions
    val consentEnforcer: ConsentEnforcer
}
