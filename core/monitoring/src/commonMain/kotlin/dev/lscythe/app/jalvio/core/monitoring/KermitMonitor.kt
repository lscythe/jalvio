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
package dev.lscythe.app.jalvio.core.monitoring

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject

private const val UNKNOWN_ERROR = "Unexpected error"

@Inject
@ContributesBinding(AppScope::class)
class KermitMonitor(private val logger: Logger) : Monitor {
    override fun recordError(error: Throwable, message: String?) {
        logger.e(error) { message ?: error.message ?: UNKNOWN_ERROR }
    }

    override fun breadcrumb(message: String) {
        logger.i { message }
    }
}
