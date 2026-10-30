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
package dev.lscythe.app.jalvio.core.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val JalvioIcons.Straight: ImageVector
    get() {
        if (_Straight != null) {
            return _Straight!!
        }
        _Straight =
            ImageVector.Builder(
                    name = "Straight",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(440f, 273f)
                        lineTo(404f, 309f)
                        quadTo(393f, 320f, 376.5f, 320f)
                        quadTo(360f, 320f, 348f, 308f)
                        quadTo(337f, 297f, 337f, 280f)
                        quadTo(337f, 263f, 348f, 252f)
                        lineTo(452f, 148f)
                        quadTo(464f, 136f, 480f, 136f)
                        quadTo(496f, 136f, 508f, 148f)
                        lineTo(612f, 252f)
                        quadTo(623f, 263f, 623.5f, 279.5f)
                        quadTo(624f, 296f, 612f, 308f)
                        quadTo(601f, 319f, 584f, 319f)
                        quadTo(567f, 319f, 556f, 308f)
                        lineTo(520f, 273f)
                        lineTo(520f, 800f)
                        quadTo(520f, 817f, 508.5f, 828.5f)
                        quadTo(497f, 840f, 480f, 840f)
                        quadTo(463f, 840f, 451.5f, 828.5f)
                        quadTo(440f, 817f, 440f, 800f)
                        lineTo(440f, 273f)
                        close()
                    }
                }
                .build()

        return _Straight!!
    }

@Suppress("ObjectPropertyName") private var _Straight: ImageVector? = null
