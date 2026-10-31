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
package dev.lscythe.app.jalvio.consent

import dev.lscythe.app.jalvio.BuildConfig
import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.lscythe.app.jalvio.core.monitoring.sentry.SentryConfig
import dev.lscythe.app.jalvio.core.monitoring.sentry.initializeSentry
import dev.lscythe.app.jalvio.di.ConsentOptionsBindings
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.OffByDefault
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOption.Unavailable
import dev.lscythe.app.jalvio.domain.preferences.model.ConsentOptions
import dev.lscythe.app.jalvio.domain.preferences.model.PrivacyConsent
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provides
import io.sentry.kotlin.multiplatform.Sentry

@ContributesTo(AppScope::class, replaces = [ConsentOptionsBindings::class])
@BindingContainer
object FossFullConsentOptionsBindings {
    @Provides
    fun provideConsentOptions(): ConsentOptions =
        ConsentOptions(analytics = Unavailable, crashReports = OffByDefault)
}

@Inject
@ContributesBinding(AppScope::class, replaces = [NoOpConsentApplier::class])
internal class FossFullConsentApplier(private val environment: BuildEnvironment) : ConsentApplier {
    override fun apply(consent: PrivacyConsent) {
        if (consent.crashReports) {
            initializeSentry(SentryConfig(dsn = BuildConfig.SENTRY_DSN, environment = environment))
        } else {
            Sentry.close()
        }
    }
}
