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
package dev.lscythe.app.jalvio.domain.catalog

import dev.lscythe.app.jalvio.core.common.geo.GeoPoint
import dev.lscythe.app.jalvio.domain.catalog.model.GeoBounds
import dev.lscythe.app.jalvio.domain.catalog.model.Region
import dev.lscythe.app.jalvio.domain.catalog.model.RegionId
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.TimeZone

private val jakarta =
    Region(
        id = RegionId("jakarta"),
        name = "Jakarta",
        timeZone = TimeZone.of("Asia/Jakarta"),
        bounds = GeoBounds(GeoPoint(-6.45, 106.6), GeoPoint(-6.05, 107.05)),
        hasLiveBuses = true,
    )
private val bandung =
    Region(
        id = RegionId("bandung"),
        name = "Bandung",
        timeZone = TimeZone.of("Asia/Jakarta"),
        bounds = GeoBounds(GeoPoint(-7.05, 107.5), GeoPoint(-6.8, 107.75)),
        hasLiveBuses = false,
    )
private val inBandung = GeoPoint(-6.91, 107.61)
private val inTheSea = GeoPoint(-5.0, 110.0)

class RegionChoiceTest :
    BehaviorSpec({
        Given("a stored region that is still served") {
            Then("it is kept even when the rider is in another region") {
                chooseRegion(listOf(jakarta, bandung), RegionId("jakarta"), inBandung) shouldBe
                    RegionChoice.Chosen(jakarta)
            }
        }
        Given("a stored region that is no longer served") {
            Then("the only region left is chosen") {
                chooseRegion(listOf(bandung), RegionId("jakarta"), null) shouldBe
                    RegionChoice.Chosen(bandung)
            }
        }
        Given("one region and nothing stored") {
            Then("it is chosen without a location") {
                chooseRegion(listOf(jakarta), null, null) shouldBe RegionChoice.Chosen(jakarta)
            }
        }
        Given("several regions and nothing stored") {
            Then("the one containing the rider is chosen") {
                chooseRegion(listOf(jakarta, bandung), null, inBandung) shouldBe
                    RegionChoice.Chosen(bandung)
            }
            Then("the rider is asked when outside every region") {
                chooseRegion(listOf(jakarta, bandung), null, inTheSea) shouldBe
                    RegionChoice.Ask(listOf(jakarta, bandung))
            }
            Then("the rider is asked when there is no location") {
                chooseRegion(listOf(jakarta, bandung), null, null) shouldBe
                    RegionChoice.Ask(listOf(jakarta, bandung))
            }
        }
        Given("no regions at all") {
            Then("none is served") {
                chooseRegion(emptyList(), RegionId("jakarta"), inBandung) shouldBe
                    RegionChoice.NoneServed
            }
        }
    })
