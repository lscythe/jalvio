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

val JalvioIcons.ArrowForward: ImageVector
    get() {
        if (_ArrowForward != null) {
            return _ArrowForward!!
        }
        _ArrowForward =
            ImageVector.Builder(
                    name = "ArrowForward",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(647f, 520f)
                        lineTo(200f, 520f)
                        quadTo(183f, 520f, 171.5f, 508.5f)
                        quadTo(160f, 497f, 160f, 480f)
                        quadTo(160f, 463f, 171.5f, 451.5f)
                        quadTo(183f, 440f, 200f, 440f)
                        lineTo(647f, 440f)
                        lineTo(451f, 244f)
                        quadTo(439f, 232f, 439.5f, 216f)
                        quadTo(440f, 200f, 452f, 188f)
                        quadTo(464f, 177f, 480f, 176.5f)
                        quadTo(496f, 176f, 508f, 188f)
                        lineTo(772f, 452f)
                        quadTo(778f, 458f, 780.5f, 465f)
                        quadTo(783f, 472f, 783f, 480f)
                        quadTo(783f, 488f, 780.5f, 495f)
                        quadTo(778f, 502f, 772f, 508f)
                        lineTo(508f, 772f)
                        quadTo(497f, 783f, 480.5f, 783f)
                        quadTo(464f, 783f, 452f, 772f)
                        quadTo(440f, 760f, 440f, 743.5f)
                        quadTo(440f, 727f, 452f, 715f)
                        lineTo(647f, 520f)
                        close()
                    }
                }
                .build()

        return _ArrowForward!!
    }

@Suppress("ObjectPropertyName") private var _ArrowForward: ImageVector? = null
