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
package dev.lscythe.app.jalvio.data.catalog

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Inject
internal class RegionSelectionStore(private val dataStore: DataStore<Preferences>) {
    val selected: Flow<RegionId?> =
        dataStore.data
            .map { preferences -> preferences[SELECTED_REGION]?.let(::RegionId) }
            .distinctUntilChanged()

    suspend fun select(id: RegionId?) {
        dataStore.edit { preferences ->
            if (id == null) preferences.remove(SELECTED_REGION)
            else preferences[SELECTED_REGION] = id.value
        }
    }
}

private val SELECTED_REGION = stringPreferencesKey("catalog_selected_region")
