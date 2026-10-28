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

internal val FOSS_FORBIDDEN_GROUP_PREFIXES =
    listOf("com.google.firebase", "com.google.android.gms", "io.sentry")

internal fun forbiddenArtifacts(
    coordinates: List<String>,
    forbiddenGroupPrefixes: List<String>,
): List<String> = coordinates.filter { coordinate ->
    val group = coordinate.substringBefore(':')
    forbiddenGroupPrefixes.any { prefix -> group == prefix || group.startsWith("$prefix.") }
}
