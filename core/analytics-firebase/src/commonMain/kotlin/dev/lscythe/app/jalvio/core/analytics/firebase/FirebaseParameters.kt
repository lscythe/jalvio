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
package dev.lscythe.app.jalvio.core.analytics.firebase

import dev.lscythe.app.jalvio.core.analytics.AnalyticsEvent

private const val MAX_NAME_LENGTH = 40
private const val MAX_VALUE_LENGTH = 100
private const val LETTER_PREFIX = "jalvio_"
private val INVALID_NAME_CHARACTERS = Regex("[^A-Za-z0-9_]")

internal fun AnalyticsEvent.toFirebaseParameters(): Pair<String, Map<String, String>> =
    firebaseName(type) to
        extras.associate { firebaseName(it.key) to it.value.take(MAX_VALUE_LENGTH) }

private fun firebaseName(raw: String): String {
    val sanitized = raw.replace(INVALID_NAME_CHARACTERS, "_")
    val startsWithLetter = sanitized.firstOrNull()?.isLetter() == true
    return (if (startsWithLetter) sanitized else LETTER_PREFIX + sanitized).take(MAX_NAME_LENGTH)
}
