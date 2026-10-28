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
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.sentry.kotlin.multiplatform.SentryOptions

class SentryInitializerTest :
    BehaviorSpec({
        Given("a blank DSN") {
            Then("Sentry is not initialised") {
                var started = false
                initializeSentry(SentryConfig(dsn = " ", environment = BuildEnvironment.Debug)) {
                        started = true
                    }
                    .shouldBeFalse()
                started.shouldBeFalse()
            }
        }
        Given("a DSN") {
            Then("Sentry is initialised with the DSN and environment") {
                val options = SentryOptions()
                initializeSentry(
                        SentryConfig(
                            dsn = "https://key@o0.ingest.sentry.io/0",
                            environment = BuildEnvironment.Release,
                        )
                    ) { configure ->
                        configure(options)
                    }
                    .shouldBeTrue()
                options.dsn shouldBe "https://key@o0.ingest.sentry.io/0"
                options.environment shouldBe "release"
            }
        }
    })
