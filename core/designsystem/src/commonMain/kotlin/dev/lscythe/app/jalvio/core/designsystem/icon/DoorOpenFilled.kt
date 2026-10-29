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

val JalvioIcons.DoorOpenFilled: ImageVector
    get() {
        if (_DoorOpenFilled != null) {
            return _DoorOpenFilled!!
        }
        _DoorOpenFilled =
            ImageVector.Builder(
                    name = "DoorOpenFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(160f, 840f)
                        quadTo(143f, 840f, 131.5f, 828.5f)
                        quadTo(120f, 817f, 120f, 800f)
                        quadTo(120f, 783f, 131.5f, 771.5f)
                        quadTo(143f, 760f, 160f, 760f)
                        lineTo(200f, 760f)
                        lineTo(200f, 200f)
                        quadTo(200f, 167f, 223.5f, 143.5f)
                        quadTo(247f, 120f, 280f, 120f)
                        lineTo(680f, 120f)
                        quadTo(713f, 120f, 736.5f, 143.5f)
                        quadTo(760f, 167f, 760f, 200f)
                        lineTo(760f, 760f)
                        lineTo(800f, 760f)
                        quadTo(817f, 760f, 828.5f, 771.5f)
                        quadTo(840f, 783f, 840f, 800f)
                        quadTo(840f, 817f, 828.5f, 828.5f)
                        quadTo(817f, 840f, 800f, 840f)
                        lineTo(160f, 840f)
                        close()
                        moveTo(680f, 760f)
                        lineTo(680f, 200f)
                        quadTo(680f, 200f, 680f, 200f)
                        quadTo(680f, 200f, 680f, 200f)
                        lineTo(500f, 200f)
                        lineTo(500f, 156f)
                        quadTo(544f, 164f, 572f, 197f)
                        quadTo(600f, 230f, 600f, 274f)
                        lineTo(600f, 718f)
                        quadTo(600f, 747f, 581f, 769.5f)
                        quadTo(562f, 792f, 533f, 797f)
                        lineTo(533f, 760f)
                        lineTo(680f, 760f)
                        close()
                        moveTo(440f, 520f)
                        quadTo(457f, 520f, 468.5f, 508.5f)
                        quadTo(480f, 497f, 480f, 480f)
                        quadTo(480f, 463f, 468.5f, 451.5f)
                        quadTo(457f, 440f, 440f, 440f)
                        quadTo(423f, 440f, 411.5f, 451.5f)
                        quadTo(400f, 463f, 400f, 480f)
                        quadTo(400f, 497f, 411.5f, 508.5f)
                        quadTo(423f, 520f, 440f, 520f)
                        close()
                    }
                }
                .build()

        return _DoorOpenFilled!!
    }

@Suppress("ObjectPropertyName") private var _DoorOpenFilled: ImageVector? = null
