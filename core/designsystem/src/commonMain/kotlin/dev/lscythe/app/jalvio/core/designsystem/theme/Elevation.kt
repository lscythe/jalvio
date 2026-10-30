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
package dev.lscythe.app.jalvio.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

object JalvioElevation {
    val level1 =
        Shadow(
            radius = 8.dp,
            color = Color.Black.copy(alpha = 0.12f),
            offset = DpOffset(0.dp, 2.dp),
        )
    val level2 =
        Shadow(
            radius = 14.dp,
            color = Color.Black.copy(alpha = 0.18f),
            offset = DpOffset(0.dp, 4.dp),
        )
    val level3 =
        Shadow(
            radius = 20.dp,
            color = Color.Black.copy(alpha = 0.20f),
            offset = DpOffset(0.dp, 6.dp),
        )
}
