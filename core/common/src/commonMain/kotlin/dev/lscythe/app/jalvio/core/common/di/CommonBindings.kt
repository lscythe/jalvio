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
package dev.lscythe.app.jalvio.core.common.di

import dev.lscythe.app.jalvio.core.common.coroutines.ApplicationScope
import dev.lscythe.app.jalvio.core.common.dispatchers.Dispatcher
import dev.lscythe.app.jalvio.core.common.dispatchers.JalvioDispatcher
import dev.lscythe.app.jalvio.core.common.dispatchers.platformIoDispatcher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@ContributesTo(AppScope::class)
@BindingContainer
object CommonBindings {
    @Provides
    @Dispatcher(JalvioDispatcher.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(JalvioDispatcher.IO)
    fun provideIoDispatcher(): CoroutineDispatcher = platformIoDispatcher

    @Provides
    @SingleIn(AppScope::class)
    @ApplicationScope
    fun provideApplicationScope(
        @Dispatcher(JalvioDispatcher.Default) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides fun provideClock(): Clock = Clock.System
}
