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
package dev.lscythe.app.jalvio.config

import dev.lscythe.app.jalvio.core.common.environment.BuildEnvironment
import dev.lscythe.app.jalvio.core.map.MapStyleUrl
import dev.lscythe.app.jalvio.core.network.ApiBaseUrl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class EndpointsTest :
    BehaviorSpec({
        Given("each build environment") {
            Then("the matching generated endpoints are chosen") {
                JalvioEndpoints.forEnvironment(BuildEnvironment.Debug) shouldBe
                    JalvioEndpoints.debug
                JalvioEndpoints.forEnvironment(BuildEnvironment.Release) shouldBe
                    JalvioEndpoints.release
            }
        }
        Given("the committed release endpoints") {
            Then("they are valid https URLs") {
                ApiBaseUrl(JalvioEndpoints.release.apiBaseUrl).value shouldStartWith "https://"
                MapStyleUrl(JalvioEndpoints.release.mapStyleUrl).value shouldStartWith "https://"
            }
        }
    })
