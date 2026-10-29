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
package dev.lscythe.app.jalvio.convention

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import org.gradle.api.tasks.testing.Test
import org.gradle.testfixtures.ProjectBuilder

class RobolectricConventionPluginTest :
    BehaviorSpec({
        Given("a project with the robolectric convention") {
            val project = ProjectBuilder.builder().build()
            project.pluginManager.apply(RobolectricConventionPlugin::class.java)
            val test = project.tasks.register("unitTest", Test::class.java).get()

            Then("tests may load robolectric's native runtime without warnings") {
                test.allJvmArgs shouldContain "--enable-native-access=ALL-UNNAMED"
            }
        }
    })
