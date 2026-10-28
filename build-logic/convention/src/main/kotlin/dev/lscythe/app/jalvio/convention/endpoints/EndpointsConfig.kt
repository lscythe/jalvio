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

data class EndpointValues(val apiBaseUrl: String, val mapStyleUrl: String)

data class EndpointsConfig(val debug: EndpointValues, val release: EndpointValues)

const val OVERRIDE_PREFIX = "jalvio."

fun resolveEndpoints(file: Map<String, String>, overrides: Map<String, String>): EndpointsConfig {
    fun value(key: String): String =
        overrides[OVERRIDE_PREFIX + key]
            ?: file[key]
            ?: throw IllegalArgumentException("Missing endpoint configuration key: $key")

    fun apiBaseUrl(key: String, requireHttps: Boolean): String =
        value(key).also {
            requireScheme(key, it, requireHttps)
            require(it.endsWith("/")) { "$key must end with a trailing slash: $it" }
        }

    fun mapStyleUrl(key: String, requireHttps: Boolean): String =
        value(key).also { requireScheme(key, it, requireHttps) }

    return EndpointsConfig(
        debug =
            EndpointValues(
                apiBaseUrl = apiBaseUrl("api.baseUrl.debug", requireHttps = false),
                mapStyleUrl = mapStyleUrl("map.styleUrl.debug", requireHttps = false),
            ),
        release =
            EndpointValues(
                apiBaseUrl = apiBaseUrl("api.baseUrl.release", requireHttps = true),
                mapStyleUrl = mapStyleUrl("map.styleUrl.release", requireHttps = true),
            ),
    )
}

private fun requireScheme(key: String, url: String, requireHttps: Boolean) {
    val allowed = if (requireHttps) listOf("https://") else listOf("https://", "http://")
    require(allowed.any(url::startsWith)) {
        "$key must start with ${allowed.joinToString(" or ")}: $url"
    }
}

fun renderEndpointsSource(packageName: String, config: EndpointsConfig): String = buildString {
    appendLine("package $packageName")
    appendLine()
    appendLine("internal object JalvioEndpoints {")
    appendLine("    val debug: Endpoints = ${config.debug.render()}")
    appendLine("    val release: Endpoints = ${config.release.render()}")
    appendLine("}")
}

private fun EndpointValues.render(): String =
    "Endpoints(apiBaseUrl = ${apiBaseUrl.asKotlinString()}, " +
        "mapStyleUrl = ${mapStyleUrl.asKotlinString()})"

private fun String.asKotlinString(): String = buildString {
    append('"')
    for (char in this@asKotlinString) {
        when (char) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '$' -> append("\\$")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            else -> append(char)
        }
    }
    append('"')
}
