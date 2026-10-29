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

val JalvioIcons.ShieldFilled: ImageVector
    get() {
        if (_ShieldFilled != null) {
            return _ShieldFilled!!
        }
        _ShieldFilled =
            ImageVector.Builder(
                    name = "ShieldFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 876f)
                        quadTo(473f, 876f, 467f, 875f)
                        quadTo(461f, 874f, 455f, 872f)
                        quadTo(320f, 827f, 240f, 705.5f)
                        quadTo(160f, 584f, 160f, 444f)
                        lineTo(160f, 255f)
                        quadTo(160f, 230f, 174.5f, 210f)
                        quadTo(189f, 190f, 212f, 181f)
                        lineTo(452f, 91f)
                        quadTo(466f, 86f, 480f, 86f)
                        quadTo(494f, 86f, 508f, 91f)
                        lineTo(748f, 181f)
                        quadTo(771f, 190f, 785.5f, 210f)
                        quadTo(800f, 230f, 800f, 255f)
                        lineTo(800f, 444f)
                        quadTo(800f, 584f, 720f, 705.5f)
                        quadTo(640f, 827f, 505f, 872f)
                        quadTo(499f, 874f, 493f, 875f)
                        quadTo(487f, 876f, 480f, 876f)
                        close()
                    }
                }
                .build()

        return _ShieldFilled!!
    }

@Suppress("ObjectPropertyName") private var _ShieldFilled: ImageVector? = null
