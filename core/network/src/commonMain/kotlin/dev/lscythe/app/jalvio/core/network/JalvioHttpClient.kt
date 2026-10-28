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

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger as KtorLogger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

private const val HTTP_LOG_TAG = "JalvioHttp"

fun createJalvioHttpClient(engine: HttpClientEngine, baseUrl: ApiBaseUrl): HttpClient =
    HttpClient(engine) {
        install(ContentNegotiation) {
            json(JalvioJson, ContentType.Application.Json)
            json(JalvioJson, ContentType.Application.ProblemJson)
        }
        install(WebSockets)
        install(Logging) {
            level = LogLevel.INFO
            logger =
                object : KtorLogger {
                    override fun log(message: String) {
                        Logger.withTag(HTTP_LOG_TAG).i { message }
                    }
                }
        }
        defaultRequest { url(baseUrl.value) }
    }
