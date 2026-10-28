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
package dev.lscythe.app.jalvio.convention.translation

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class TranslationParityTask : DefaultTask() {
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceFile: RegularFileProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val translationFile: RegularFileProperty

    @get:OutputFile abstract val report: RegularFileProperty

    @TaskAction
    fun check() {
        val parity =
            translationParity(
                sourceFile.get().asFile.readText(),
                translationFile.get().asFile.readText(),
            )
        if (!parity.isClean) {
            throw GradleException(
                buildString {
                    appendLine("Translation ${translationFile.get().asFile.path} is out of sync:")
                    appendLine(" - missing: ${parity.missing.sorted()}")
                    appendLine(" - extra: ${parity.extra.sorted()}")
                    append(" - placeholder mismatches: ${parity.placeholderMismatches.sorted()}")
                }
            )
        }
        report.get().asFile.apply {
            parentFile.mkdirs()
            writeText("ok\n")
        }
    }
}
