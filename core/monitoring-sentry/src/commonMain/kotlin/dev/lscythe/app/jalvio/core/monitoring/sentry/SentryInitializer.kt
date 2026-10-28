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
package dev.lscythe.app.jalvio.core.monitoring.sentry

import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import io.sentry.kotlin.multiplatform.OptionsConfiguration
import io.sentry.kotlin.multiplatform.Sentry

data class SentryConfig(val dsn: String, val environment: BuildEnvironment)

fun initializeSentry(
    config: SentryConfig,
    start: (OptionsConfiguration) -> Unit = Sentry::init,
): Boolean {
    if (config.dsn.isBlank()) return false
    start { options ->
        options.dsn = config.dsn
        options.environment = config.environment.name.lowercase()
    }
    return true
}
