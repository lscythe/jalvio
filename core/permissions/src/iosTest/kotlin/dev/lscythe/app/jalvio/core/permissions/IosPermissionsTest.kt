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

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusEphemeral
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNAuthorizationStatusProvisional

class IosPermissionsTest :
    BehaviorSpec({
        Given("a location authorization") {
            Then("always and when in use are granted") {
                locationPermissionStatus(kCLAuthorizationStatusAuthorizedAlways) shouldBe
                    PermissionStatus.Granted
                locationPermissionStatus(kCLAuthorizationStatusAuthorizedWhenInUse) shouldBe
                    PermissionStatus.Granted
            }
            Then("denied and restricted are denied") {
                locationPermissionStatus(kCLAuthorizationStatusDenied) shouldBe
                    PermissionStatus.Denied
                locationPermissionStatus(kCLAuthorizationStatusRestricted) shouldBe
                    PermissionStatus.Denied
            }
            Then("not determined is not requested") {
                locationPermissionStatus(kCLAuthorizationStatusNotDetermined) shouldBe
                    PermissionStatus.NotRequested
            }
        }
        Given("a notification authorization") {
            Then("authorized, provisional and ephemeral are granted") {
                notificationPermissionStatus(UNAuthorizationStatusAuthorized) shouldBe
                    PermissionStatus.Granted
                notificationPermissionStatus(UNAuthorizationStatusProvisional) shouldBe
                    PermissionStatus.Granted
                notificationPermissionStatus(UNAuthorizationStatusEphemeral) shouldBe
                    PermissionStatus.Granted
            }
            Then("denied is denied") {
                notificationPermissionStatus(UNAuthorizationStatusDenied) shouldBe
                    PermissionStatus.Denied
            }
            Then("not determined is not requested") {
                notificationPermissionStatus(UNAuthorizationStatusNotDetermined) shouldBe
                    PermissionStatus.NotRequested
            }
        }
    })
