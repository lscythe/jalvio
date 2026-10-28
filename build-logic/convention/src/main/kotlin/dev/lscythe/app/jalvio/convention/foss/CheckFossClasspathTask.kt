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

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class CheckFossClasspathTask : DefaultTask() {
    @get:Input abstract val resolvedCoordinates: ListProperty<String>

    @get:Input abstract val forbiddenGroupPrefixes: ListProperty<String>

    @get:OutputFile abstract val report: RegularFileProperty

    @TaskAction
    fun check() {
        val forbidden = forbiddenArtifacts(resolvedCoordinates.get(), forbiddenGroupPrefixes.get())
        if (forbidden.isNotEmpty()) {
            throw GradleException(
                "Forbidden artifacts on the foss classpath:\n" +
                    forbidden.joinToString("\n") { " - $it" }
            )
        }
        report.get().asFile.apply {
            parentFile.mkdirs()
            writeText("ok\n")
        }
    }
}
