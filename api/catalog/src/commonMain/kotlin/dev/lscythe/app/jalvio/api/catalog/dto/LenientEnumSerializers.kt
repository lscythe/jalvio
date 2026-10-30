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
package dev.lscythe.app.jalvio.api.catalog.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal abstract class LenientEnumSerializer<T : Enum<T>>(
    serialName: String,
    private val entries: List<T>,
    private val wireOf: (T) -> String,
    private val unknown: T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): T {
        val wire = decoder.decodeString()
        return entries.firstOrNull { wireOf(it) == wire } ?: unknown
    }

    override fun serialize(encoder: Encoder, value: T) = encoder.encodeString(wireOf(value))
}

internal object StopKindSerializer :
    LenientEnumSerializer<StopKindDto>(
        "StopKindDto",
        StopKindDto.entries,
        StopKindDto::wire,
        StopKindDto.Unknown,
    )

internal object ServiceDaySerializer :
    LenientEnumSerializer<ServiceDayDto>(
        "ServiceDayDto",
        ServiceDayDto.entries,
        ServiceDayDto::wire,
        ServiceDayDto.Unknown,
    )
