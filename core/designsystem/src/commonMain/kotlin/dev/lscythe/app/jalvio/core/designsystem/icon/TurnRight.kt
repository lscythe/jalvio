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

val JalvioIcons.TurnRight: ImageVector
    get() {
        if (_TurnRight != null) {
            return _TurnRight!!
        }
        _TurnRight =
            ImageVector.Builder(
                    name = "TurnRight",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(280f, 760f)
                        lineTo(280f, 440f)
                        quadTo(280f, 407f, 303.5f, 383.5f)
                        quadTo(327f, 360f, 360f, 360f)
                        lineTo(688f, 360f)
                        lineTo(652f, 324f)
                        quadTo(641f, 313f, 641f, 296f)
                        quadTo(641f, 279f, 652f, 268f)
                        quadTo(663f, 257f, 680f, 257f)
                        quadTo(697f, 257f, 708f, 268f)
                        lineTo(812f, 372f)
                        quadTo(824f, 384f, 824f, 400f)
                        quadTo(824f, 416f, 812f, 428f)
                        lineTo(708f, 532f)
                        quadTo(697f, 543f, 680f, 543f)
                        quadTo(663f, 543f, 652f, 532f)
                        quadTo(641f, 521f, 641f, 504f)
                        quadTo(641f, 487f, 652f, 476f)
                        lineTo(688f, 440f)
                        lineTo(360f, 440f)
                        quadTo(360f, 440f, 360f, 440f)
                        quadTo(360f, 440f, 360f, 440f)
                        lineTo(360f, 760f)
                        quadTo(360f, 777f, 348.5f, 788.5f)
                        quadTo(337f, 800f, 320f, 800f)
                        quadTo(303f, 800f, 291.5f, 788.5f)
                        quadTo(280f, 777f, 280f, 760f)
                        close()
                    }
                }
                .build()

        return _TurnRight!!
    }

@Suppress("ObjectPropertyName") private var _TurnRight: ImageVector? = null
