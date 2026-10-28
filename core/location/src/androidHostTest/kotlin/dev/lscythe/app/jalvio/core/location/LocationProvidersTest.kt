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
package dev.lscythe.app.jalvio.core.location

import android.location.LocationManager.FUSED_PROVIDER
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly

class LocationProvidersTest :
    BehaviorSpec({
        Given("API 31 or later with the fused provider enabled") {
            Then("only the fused provider is used") {
                locationProvidersFor(
                    sdkInt = 31,
                    enabled = listOf(GPS_PROVIDER, NETWORK_PROVIDER, FUSED_PROVIDER),
                ) shouldContainExactly listOf(FUSED_PROVIDER)
            }
        }
        Given("API 31 or later without the fused provider") {
            Then("gps and network are used") {
                locationProvidersFor(sdkInt = 33, enabled = listOf(NETWORK_PROVIDER, GPS_PROVIDER))
                    .shouldContainExactly(GPS_PROVIDER, NETWORK_PROVIDER)
            }
        }
        Given("API 30 with a fused provider listed") {
            Then("gps and network are used") {
                locationProvidersFor(
                    sdkInt = 30,
                    enabled = listOf(FUSED_PROVIDER, GPS_PROVIDER, NETWORK_PROVIDER),
                ) shouldContainExactly listOf(GPS_PROVIDER, NETWORK_PROVIDER)
            }
        }
        Given("only the network provider enabled") {
            Then("only network is used") {
                locationProvidersFor(sdkInt = 30, enabled = listOf(NETWORK_PROVIDER))
                    .shouldContainExactly(NETWORK_PROVIDER)
            }
        }
        Given("no provider enabled") {
            Then("nothing is used") {
                locationProvidersFor(sdkInt = 34, enabled = emptyList()).shouldBeEmpty()
            }
        }
    })
