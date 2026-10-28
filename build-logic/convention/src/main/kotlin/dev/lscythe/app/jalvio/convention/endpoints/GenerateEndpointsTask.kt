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
package dev.lscythe.app.jalvio.convention.endpoints

import java.util.Properties
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class GenerateEndpointsTask : DefaultTask() {
    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val propertiesFile: RegularFileProperty

    @get:Input abstract val overrides: MapProperty<String, String>

    @get:Input abstract val packageName: Property<String>

    @get:OutputDirectory abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val source =
            renderEndpointsSource(packageName.get(), resolveEndpoints(loadFile(), overrides.get()))
        val target =
            outputDirectory.get().asFile.resolve(packageName.get().replace('.', '/')).apply {
                mkdirs()
            }
        target.resolve("JalvioEndpoints.kt").writeText(source)
    }

    private fun loadFile(): Map<String, String> {
        val properties = Properties()
        propertiesFile.get().asFile.inputStream().use(properties::load)
        return properties.stringPropertyNames().associateWith(properties::getProperty)
    }
}
