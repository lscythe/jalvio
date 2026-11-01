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
package dev.lscythe.app.jalvio.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.shouldBe
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first

class PreferencesDataStoreFactoryTest :
    BehaviorSpec({
        Given("a preferences file that is not a preferences file") {
            val file = File(tempdir(), PREFERENCES_FILE).apply { writeBytes(Garbage) }
            val scope = CoroutineScope(SupervisorJob())
            val store = createPreferencesDataStore(file.absolutePath, scope, Dispatchers.IO)
            afterSpec { scope.cancel() }

            Then("it reads as empty") { store.data.first() shouldBe emptyPreferences() }
            Then("it can be written again") {
                store.edit { it[Flag] = true }
                store.data.first()[Flag] shouldBe true
            }
        }
    })

private val Flag = booleanPreferencesKey("flag")
private val Garbage = ByteArray(8) { 0xFF.toByte() }
