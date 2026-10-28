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

import co.touchlab.kermit.Logger
import dev.lscythe.app.jalvio.core.monitoring.KermitMonitor
import dev.lscythe.app.jalvio.core.monitoring.Monitor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.protocol.Breadcrumb

@Inject
@ContributesBinding(AppScope::class, replaces = [KermitMonitor::class])
internal class SentryMonitor(logger: Logger) : Monitor {
    private val kermitMonitor = KermitMonitor(logger)

    override fun recordError(error: Throwable, message: String?) {
        kermitMonitor.recordError(error, message)
        Sentry.captureException(error)
    }

    override fun breadcrumb(message: String) {
        kermitMonitor.breadcrumb(message)
        Sentry.addBreadcrumb(Breadcrumb(message = message))
    }
}
