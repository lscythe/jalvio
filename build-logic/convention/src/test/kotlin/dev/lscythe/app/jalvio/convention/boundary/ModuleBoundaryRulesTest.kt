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
package dev.lscythe.app.jalvio.convention.boundary

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.string.shouldContain

class ModuleBoundaryRulesTest :
    BehaviorSpec({
        fun dep(from: String, to: String, conf: String = "commonMainImplementation") =
            ModuleDependency(from, to, conf)

        Given("allowed dependencies") {
            Then("no violations") {
                boundaryViolations(
                        listOf(
                            dep(":feature:home:impl", ":feature:stop:api"),
                            dep(":feature:home:impl", ":domain:catalog"),
                            dep(":feature:home:impl", ":core:map"),
                            dep(":feature:home:api", ":core:navigation"),
                            dep(":domain:live", ":domain:catalog"),
                            dep(":domain:live", ":core:common"),
                            dep(":data:catalog", ":domain:catalog"),
                            dep(":data:catalog", ":api:catalog"),
                            dep(":data:catalog", ":core:database"),
                            dep(":api:catalog", ":core:network"),
                            dep(":core:network", ":core:common"),
                            dep(":app:shared", ":data:catalog"),
                        )
                    )
                    .shouldBeEmpty()
            }
        }
        Given("a feature depending on data") {
            Then("one violation naming both modules") {
                val violations =
                    boundaryViolations(listOf(dep(":feature:home:impl", ":data:catalog")))
                violations shouldHaveSize 1
                violations.single() shouldContain ":feature:home:impl -> :data:catalog"
            }
        }
        Given("forbidden edges in platform source sets and api") {
            Then("each is reported") {
                val violations =
                    boundaryViolations(
                        listOf(
                            dep(":feature:home:impl", ":data:catalog", "androidMainImplementation"),
                            dep(":domain:catalog", ":core:network", "iosMainApi"),
                            dep(":data:catalog", ":domain:live"),
                            dep(":core:ui", ":domain:catalog"),
                            dep(":feature:home:api", ":core:ui"),
                            dep(":feature:home:impl", ":feature:stop:impl"),
                            dep(":api:catalog", ":core:database"),
                        )
                    )
                violations shouldHaveSize 7
                violations.map { it.substringBefore(" (") } shouldContainExactly
                    listOf(
                        ":feature:home:impl -> :data:catalog",
                        ":domain:catalog -> :core:network",
                        ":data:catalog -> :domain:live",
                        ":core:ui -> :domain:catalog",
                        ":feature:home:api -> :core:ui",
                        ":feature:home:impl -> :feature:stop:impl",
                        ":api:catalog -> :core:database",
                    )
            }
        }
        Given("a violation") {
            Then("it reads from, to, configuration and rule") {
                boundaryViolations(listOf(dep(":core:ui", ":domain:catalog", "commonMainApi")))
                    .shouldHaveSingleElement {
                        it.startsWith(":core:ui -> :domain:catalog (commonMainApi): ")
                    }
            }
        }
        Given("test configurations") {
            Then("they are ignored") {
                boundaryViolations(
                        listOf(
                            dep(":domain:catalog", ":core:testing", "commonTestImplementation"),
                            dep(
                                ":feature:home:impl",
                                ":core:testing-screenshot",
                                "androidHostTestImplementation",
                            ),
                        )
                    )
                    .shouldBeEmpty()
            }
        }
        Given("a data module contributing sync work") {
            Then("it may use core:sync but still not core:monitoring") {
                boundaryViolations(
                        listOf(
                            dep(":data:catalog", ":core:sync"),
                            dep(":data:catalog", ":core:monitoring"),
                        )
                    )
                    .map { it.substringBefore(" (") } shouldContainExactly
                    listOf(":data:catalog -> :core:monitoring")
            }
        }
        Given("a module referencing itself, as kover does") {
            Then("it is not a violation") {
                boundaryViolations(
                        listOf(
                            dep(":feature:home:impl", ":feature:home:impl", "kover"),
                            dep(":feature:home:api", ":feature:home:api", "kover"),
                        )
                    )
                    .shouldBeEmpty()
            }
        }
    })
