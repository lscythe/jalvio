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
package dev.lscythe.app.jalvio.core.common.geo

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class GeoPointTest :
    BehaviorSpec({
        Given("one degree of longitude on the equator") {
            Then("it is about 111 195 m") {
                GeoPoint(0.0, 0.0).distanceMetersTo(GeoPoint(0.0, 1.0)) shouldBe
                    (111_195.0 plusOrMinus 1.0)
            }
        }
        Given("the same point") {
            Then("distance is zero") {
                GeoPoint(-6.1754, 106.8272).distanceMetersTo(GeoPoint(-6.1754, 106.8272)) shouldBe
                    0.0
            }
        }
        Given("points across the antimeridian") {
            Then("the short way is used") {
                GeoPoint(0.0, 179.5).distanceMetersTo(GeoPoint(0.0, -179.5)) shouldBe
                    (111_195.0 plusOrMinus 1.0)
            }
        }
        Given("a radius check") {
            Then("the boundary is inclusive") {
                GeoPoint(0.0, 0.0).isWithin(111_195.1, of = GeoPoint(0.0, 1.0)).shouldBeTrue()
                GeoPoint(0.0, 0.0).isWithin(111_000.0, of = GeoPoint(0.0, 1.0)).shouldBeFalse()
            }
        }
    })
