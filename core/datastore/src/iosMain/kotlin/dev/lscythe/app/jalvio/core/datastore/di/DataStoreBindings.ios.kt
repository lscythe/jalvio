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
package dev.lscythe.app.jalvio.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.lscythe.app.jalvio.core.common.coroutines.ApplicationScope
import dev.lscythe.app.jalvio.core.common.dispatchers.Dispatcher
import dev.lscythe.app.jalvio.core.common.dispatchers.JalvioDispatcher
import dev.lscythe.app.jalvio.core.datastore.PREFERENCES_FILE
import dev.lscythe.app.jalvio.core.datastore.createPreferencesDataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@ContributesTo(AppScope::class)
@BindingContainer
object DataStoreBindings {
    @Provides
    @SingleIn(AppScope::class)
    fun providePreferencesDataStore(
        @ApplicationScope applicationScope: CoroutineScope,
        @Dispatcher(JalvioDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): DataStore<Preferences> =
        createPreferencesDataStore(
            path = "${documentDirectoryPath()}/$PREFERENCES_FILE",
            applicationScope = applicationScope,
            ioDispatcher = ioDispatcher,
        )

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectoryPath(): String =
        requireNotNull(
            NSFileManager.defaultManager
                .URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                ?.path
        )
}
