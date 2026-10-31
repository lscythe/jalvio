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

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OffByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OnByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.Unavailable
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

internal val playstoreOptions = ConsentOptions(analytics = OnByDefault, crashReports = OnByDefault)
internal val fossFullOptions = ConsentOptions(analytics = Unavailable, crashReports = OffByDefault)

internal class InMemoryDataStore : DataStore<Preferences> {
    private val state = MutableStateFlow(emptyPreferences())

    override val data: Flow<Preferences> = state

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences =
        transform(state.value).also { state.value = it }
}

internal class CorruptDataStore : DataStore<Preferences> {
    override val data: Flow<Preferences> = flow { throw CorruptionException("corrupt") }

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences =
        throw CorruptionException("corrupt")
}
