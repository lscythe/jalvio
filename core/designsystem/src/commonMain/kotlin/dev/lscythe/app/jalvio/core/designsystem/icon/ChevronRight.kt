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

val JalvioIcons.ChevronRight: ImageVector
    get() {
        if (_ChevronRight != null) {
            return _ChevronRight!!
        }
        _ChevronRight =
            ImageVector.Builder(
                    name = "ChevronRight",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                    autoMirror = true,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(504f, 480f)
                        lineTo(348f, 324f)
                        quadTo(337f, 313f, 337f, 296f)
                        quadTo(337f, 279f, 348f, 268f)
                        quadTo(359f, 257f, 376f, 257f)
                        quadTo(393f, 257f, 404f, 268f)
                        lineTo(588f, 452f)
                        quadTo(594f, 458f, 596.5f, 465f)
                        quadTo(599f, 472f, 599f, 480f)
                        quadTo(599f, 488f, 596.5f, 495f)
                        quadTo(594f, 502f, 588f, 508f)
                        lineTo(404f, 692f)
                        quadTo(393f, 703f, 376f, 703f)
                        quadTo(359f, 703f, 348f, 692f)
                        quadTo(337f, 681f, 337f, 664f)
                        quadTo(337f, 647f, 348f, 636f)
                        lineTo(504f, 480f)
                        close()
                    }
                }
                .build()

        return _ChevronRight!!
    }

@Suppress("ObjectPropertyName") private var _ChevronRight: ImageVector? = null
