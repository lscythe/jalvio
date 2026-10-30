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

val JalvioIcons.ArrowOutward: ImageVector
    get() {
        if (_ArrowOutward != null) {
            return _ArrowOutward!!
        }
        _ArrowOutward =
            ImageVector.Builder(
                    name = "ArrowOutward",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                    autoMirror = true,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(640f, 336f)
                        lineTo(284f, 692f)
                        quadTo(273f, 703f, 256f, 703f)
                        quadTo(239f, 703f, 228f, 692f)
                        quadTo(217f, 681f, 217f, 664f)
                        quadTo(217f, 647f, 228f, 636f)
                        lineTo(584f, 280f)
                        lineTo(280f, 280f)
                        quadTo(263f, 280f, 251.5f, 268.5f)
                        quadTo(240f, 257f, 240f, 240f)
                        quadTo(240f, 223f, 251.5f, 211.5f)
                        quadTo(263f, 200f, 280f, 200f)
                        lineTo(680f, 200f)
                        quadTo(697f, 200f, 708.5f, 211.5f)
                        quadTo(720f, 223f, 720f, 240f)
                        lineTo(720f, 640f)
                        quadTo(720f, 657f, 708.5f, 668.5f)
                        quadTo(697f, 680f, 680f, 680f)
                        quadTo(663f, 680f, 651.5f, 668.5f)
                        quadTo(640f, 657f, 640f, 640f)
                        lineTo(640f, 336f)
                        close()
                    }
                }
                .build()

        return _ArrowOutward!!
    }

@Suppress("ObjectPropertyName") private var _ArrowOutward: ImageVector? = null
