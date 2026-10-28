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
package dev.lscythe.app.jalvio.convention.foss

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly

class FossClasspathTest :
    BehaviorSpec({
        val forbidden = listOf("com.google.firebase", "com.google.android.gms", "io.sentry")

        Given("a clean classpath") {
            Then("nothing is reported") {
                forbiddenArtifacts(
                        listOf(
                            "androidx.core:core:1.19.0",
                            "com.google.guava:guava:33.0.0",
                            "io.ktor:ktor-client-core:3.5.2",
                        ),
                        forbidden,
                    )
                    .shouldBeEmpty()
            }
        }

        Given("a transitive Google artifact") {
            Then("it is reported with its coordinate") {
                forbiddenArtifacts(
                    listOf(
                        "androidx.core:core:1.19.0",
                        "com.google.android.gms:play-services-basement:18.5.0",
                    ),
                    forbidden,
                ) shouldContainExactly
                    listOf("com.google.android.gms:play-services-basement:18.5.0")
            }
        }

        Given("a group that only shares a prefix string") {
            Then("it is not reported") {
                forbiddenArtifacts(listOf("io.sentryish:tool:1.0"), forbidden).shouldBeEmpty()
            }
        }
    })
