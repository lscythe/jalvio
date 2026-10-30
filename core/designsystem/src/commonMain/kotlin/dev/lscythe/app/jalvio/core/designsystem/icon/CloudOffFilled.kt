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

val JalvioIcons.CloudOffFilled: ImageVector
    get() {
        if (_CloudOffFilled != null) {
            return _CloudOffFilled!!
        }
        _CloudOffFilled =
            ImageVector.Builder(
                    name = "CloudOffFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(260f, 800f)
                        quadTo(168f, 800f, 104f, 736f)
                        quadTo(40f, 672f, 40f, 580f)
                        quadTo(40f, 503f, 87.5f, 443f)
                        quadTo(135f, 383f, 210f, 366f)
                        quadTo(213f, 358f, 216f, 350.5f)
                        quadTo(219f, 343f, 222f, 334f)
                        lineTo(84f, 196f)
                        quadTo(73f, 185f, 73f, 168f)
                        quadTo(73f, 151f, 84f, 140f)
                        quadTo(95f, 129f, 112f, 129f)
                        quadTo(129f, 129f, 140f, 140f)
                        lineTo(820f, 820f)
                        quadTo(831f, 831f, 831.5f, 847.5f)
                        quadTo(832f, 864f, 820f, 876f)
                        quadTo(809f, 887f, 792.5f, 887.5f)
                        quadTo(776f, 888f, 764f, 877f)
                        lineTo(686f, 800f)
                        lineTo(260f, 800f)
                        close()
                        moveTo(864f, 750f)
                        lineTo(322f, 209f)
                        quadTo(357f, 185f, 396.5f, 172.5f)
                        quadTo(436f, 160f, 480f, 160f)
                        quadTo(597f, 160f, 678.5f, 241.5f)
                        quadTo(760f, 323f, 760f, 440f)
                        lineTo(760f, 440f)
                        lineTo(760f, 440f)
                        quadTo(829f, 448f, 874.5f, 499.5f)
                        quadTo(920f, 551f, 920f, 620f)
                        quadTo(920f, 659f, 905f, 692.5f)
                        quadTo(890f, 726f, 864f, 750f)
                        close()
                    }
                }
                .build()

        return _CloudOffFilled!!
    }

@Suppress("ObjectPropertyName") private var _CloudOffFilled: ImageVector? = null
