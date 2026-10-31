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
package dev.lscythe.app.jalvio.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dev.lscythe.app.jalvio.domain.preferences.UserPreferencesRepository
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.UserPreferences
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(AppScope::class)
internal class DataStoreUserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    private val options: ConsentOptions,
) : UserPreferencesRepository {
    override val preferences: Flow<UserPreferences> =
        dataStore.data
            .catch { error -> if (error is IOException) emit(emptyPreferences()) else throw error }
            .map { it.toStored().toDomain(options) }
            .distinctUntilChanged()

    override suspend fun setOnboardingDone() = save(PreferenceKeys.OnboardingDone, true)

    override suspend fun setAnalyticsConsent(enabled: Boolean) =
        save(PreferenceKeys.ConsentAnalytics, enabled)

    override suspend fun setCrashReportsConsent(enabled: Boolean) =
        save(PreferenceKeys.ConsentCrashReports, enabled)

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        try {
            dataStore.edit { it[key] = value }
        } catch (_: IOException) {}
    }
}
