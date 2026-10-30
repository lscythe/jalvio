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
package dev.lscythe.app.jalvio.domain.catalog.model

import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

private val jakarta = GeoBounds(GeoPoint(-6.45, 106.6), GeoPoint(-6.05, 107.05))

class GeoBoundsTest :
    BehaviorSpec({
        Given("Jakarta's bounds") {
            Then("a point inside is contained") { (GeoPoint(-6.2, 106.8) in jakarta) shouldBe true }
            Then("a point on the edge is contained") {
                (GeoPoint(-6.45, 107.05) in jakarta) shouldBe true
            }
            Then("a point too far south is not") {
                (GeoPoint(-6.5, 106.8) in jakarta) shouldBe false
            }
            Then("a point too far east is not") {
                (GeoPoint(-6.2, 107.1) in jakarta) shouldBe false
            }
        }
    })
