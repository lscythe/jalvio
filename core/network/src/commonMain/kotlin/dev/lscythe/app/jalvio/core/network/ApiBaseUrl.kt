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
package dev.lscythe.app.jalvio.core.network

import io.ktor.http.URLProtocol
import io.ktor.http.Url
import kotlin.jvm.JvmInline

@JvmInline
value class ApiBaseUrl(val value: String) {
    init {
        require(value.endsWith("/")) { "Base URL must end with '/': $value" }
        val url = runCatching { Url(value) }.getOrNull()
        require(
            url != null &&
                url.host.isNotEmpty() &&
                (url.protocol == URLProtocol.HTTP || url.protocol == URLProtocol.HTTPS) &&
                value.startsWith("${url.protocol.name}://")
        ) {
            "Base URL must be an absolute http or https URL: $value"
        }
    }
}
