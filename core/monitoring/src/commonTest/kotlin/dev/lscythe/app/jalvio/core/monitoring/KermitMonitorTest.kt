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

import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.TestLogWriter
import co.touchlab.kermit.loggerConfigInit
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalKermitApi::class)
class KermitMonitorTest :
    BehaviorSpec({
        Given("a recorded error") {
            Then("Kermit receives it at error severity with the message") {
                val writer = TestLogWriter(Severity.Verbose)
                val boom = IllegalStateException("boom")
                KermitMonitor(Logger(loggerConfigInit(writer), "Jalvio"))
                    .recordError(boom, "sync failed")
                val entry = writer.logs.single()
                entry.severity shouldBe Severity.Error
                entry.message shouldBe "sync failed"
                entry.throwable shouldBe boom
            }
        }
        Given("an error without a message") {
            Then("the error's own message is logged") {
                val writer = TestLogWriter(Severity.Verbose)
                KermitMonitor(Logger(loggerConfigInit(writer), "Jalvio"))
                    .recordError(IllegalStateException("boom"))
                writer.logs.single().message shouldBe "boom"
            }
        }
        Given("a breadcrumb") {
            Then("Kermit receives it at info severity") {
                val writer = TestLogWriter(Severity.Verbose)
                KermitMonitor(Logger(loggerConfigInit(writer), "Jalvio")).breadcrumb("opened home")
                val entry = writer.logs.single()
                entry.severity shouldBe Severity.Info
                entry.message shouldBe "opened home"
            }
        }
    })
