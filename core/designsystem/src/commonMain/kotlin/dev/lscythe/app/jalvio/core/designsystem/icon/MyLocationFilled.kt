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

val JalvioIcons.MyLocationFilled: ImageVector
    get() {
        if (_MyLocationFilled != null) {
            return _MyLocationFilled!!
        }
        _MyLocationFilled =
            ImageVector.Builder(
                    name = "MyLocationFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(440f, 878f)
                        lineTo(440f, 838f)
                        quadTo(315f, 824f, 225.5f, 734.5f)
                        quadTo(136f, 645f, 122f, 520f)
                        lineTo(82f, 520f)
                        quadTo(65f, 520f, 53.5f, 508.5f)
                        quadTo(42f, 497f, 42f, 480f)
                        quadTo(42f, 463f, 53.5f, 451.5f)
                        quadTo(65f, 440f, 82f, 440f)
                        lineTo(122f, 440f)
                        quadTo(136f, 315f, 225.5f, 225.5f)
                        quadTo(315f, 136f, 440f, 122f)
                        lineTo(440f, 82f)
                        quadTo(440f, 65f, 451.5f, 53.5f)
                        quadTo(463f, 42f, 480f, 42f)
                        quadTo(497f, 42f, 508.5f, 53.5f)
                        quadTo(520f, 65f, 520f, 82f)
                        lineTo(520f, 122f)
                        quadTo(645f, 136f, 734.5f, 225.5f)
                        quadTo(824f, 315f, 838f, 440f)
                        lineTo(878f, 440f)
                        quadTo(895f, 440f, 906.5f, 451.5f)
                        quadTo(918f, 463f, 918f, 480f)
                        quadTo(918f, 497f, 906.5f, 508.5f)
                        quadTo(895f, 520f, 878f, 520f)
                        lineTo(838f, 520f)
                        quadTo(824f, 645f, 734.5f, 734.5f)
                        quadTo(645f, 824f, 520f, 838f)
                        lineTo(520f, 878f)
                        quadTo(520f, 895f, 508.5f, 906.5f)
                        quadTo(497f, 918f, 480f, 918f)
                        quadTo(463f, 918f, 451.5f, 906.5f)
                        quadTo(440f, 895f, 440f, 878f)
                        close()
                        moveTo(480f, 760f)
                        quadTo(596f, 760f, 678f, 678f)
                        quadTo(760f, 596f, 760f, 480f)
                        quadTo(760f, 364f, 678f, 282f)
                        quadTo(596f, 200f, 480f, 200f)
                        quadTo(364f, 200f, 282f, 282f)
                        quadTo(200f, 364f, 200f, 480f)
                        quadTo(200f, 596f, 282f, 678f)
                        quadTo(364f, 760f, 480f, 760f)
                        close()
                        moveTo(480f, 640f)
                        quadTo(414f, 640f, 367f, 593f)
                        quadTo(320f, 546f, 320f, 480f)
                        quadTo(320f, 414f, 367f, 367f)
                        quadTo(414f, 320f, 480f, 320f)
                        quadTo(546f, 320f, 593f, 367f)
                        quadTo(640f, 414f, 640f, 480f)
                        quadTo(640f, 546f, 593f, 593f)
                        quadTo(546f, 640f, 480f, 640f)
                        close()
                    }
                }
                .build()

        return _MyLocationFilled!!
    }

@Suppress("ObjectPropertyName") private var _MyLocationFilled: ImageVector? = null
