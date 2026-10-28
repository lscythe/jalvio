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
package dev.lscythe.app.jalvio.core.permissions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class AndroidPermissionsTest :
    BehaviorSpec({
        Given("the location permission") {
            Then("it needs fine and coarse location") {
                androidPermissionsFor(AppPermission.Location, sdkInt = 36)
                    .shouldContainExactly(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            }
        }
        Given("the notifications permission") {
            Then("it needs nothing below API 33") {
                androidPermissionsFor(AppPermission.Notifications, sdkInt = 32).shouldBeEmpty()
            }
            Then("it needs POST_NOTIFICATIONS from API 33") {
                androidPermissionsFor(AppPermission.Notifications, sdkInt = 33)
                    .shouldContainExactly(POST_NOTIFICATIONS)
            }
        }
        Given("a permission that needs nothing") {
            Then("it is granted") {
                permissionStatus(
                    emptyList(),
                    isGranted = { false },
                    shouldShowRationale = { false },
                ) shouldBe PermissionStatus.Granted
            }
        }
        Given("only coarse location granted") {
            Then("location is granted") {
                permissionStatus(
                    listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                    isGranted = { it == ACCESS_COARSE_LOCATION },
                    shouldShowRationale = { false },
                ) shouldBe PermissionStatus.Granted
            }
        }
        Given("nothing granted") {
            Then("a rationale means denied") {
                permissionStatus(
                    listOf(POST_NOTIFICATIONS),
                    isGranted = { false },
                    shouldShowRationale = { true },
                ) shouldBe PermissionStatus.Denied
            }
            Then("no rationale means not requested") {
                permissionStatus(
                    listOf(POST_NOTIFICATIONS),
                    isGranted = { false },
                    shouldShowRationale = { false },
                ) shouldBe PermissionStatus.NotRequested
            }
        }
        Given("a request result") {
            Then("any grant is granted") {
                requestResultStatus(
                    mapOf(ACCESS_FINE_LOCATION to false, ACCESS_COARSE_LOCATION to true)
                ) shouldBe PermissionStatus.Granted
            }
            Then("no grant or a dismissed dialog is denied") {
                requestResultStatus(mapOf(POST_NOTIFICATIONS to false)) shouldBe
                    PermissionStatus.Denied
                requestResultStatus(emptyMap()) shouldBe PermissionStatus.Denied
            }
        }
    })
