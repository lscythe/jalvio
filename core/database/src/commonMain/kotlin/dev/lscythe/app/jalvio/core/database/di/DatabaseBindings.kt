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
package dev.lscythe.app.jalvio.core.database.di

import androidx.room3.RoomDatabase
import dev.lscythe.app.jalvio.core.common.dispatchers.Dispatcher
import dev.lscythe.app.jalvio.core.common.dispatchers.JalvioDispatcher
import dev.lscythe.app.jalvio.core.database.JalvioDatabase
import dev.lscythe.app.jalvio.core.database.buildJalvioDatabase
import dev.lscythe.app.jalvio.core.database.example.ExampleDao
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher

@ContributesTo(AppScope::class)
@BindingContainer
object DatabaseBindings {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(
        builder: RoomDatabase.Builder<JalvioDatabase>,
        @Dispatcher(JalvioDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): JalvioDatabase = buildJalvioDatabase(builder, ioDispatcher)

    @Provides fun provideExampleDao(database: JalvioDatabase): ExampleDao = database.exampleDao()
}
