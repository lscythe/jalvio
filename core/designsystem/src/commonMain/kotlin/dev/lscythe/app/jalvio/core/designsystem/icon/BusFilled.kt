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

val JalvioIcons.BusFilled: ImageVector
    get() {
        if (_BusFilled != null) {
            return _BusFilled!!
        }
        _BusFilled =
            ImageVector.Builder(
                    name = "BusFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(320f, 760f)
                        lineTo(320f, 780f)
                        quadTo(320f, 805f, 302.5f, 822.5f)
                        quadTo(285f, 840f, 260f, 840f)
                        quadTo(235f, 840f, 217.5f, 822.5f)
                        quadTo(200f, 805f, 200f, 780f)
                        lineTo(200f, 718f)
                        quadTo(182f, 698f, 171f, 673.5f)
                        quadTo(160f, 649f, 160f, 620f)
                        lineTo(160f, 240f)
                        quadTo(160f, 157f, 237f, 118.5f)
                        quadTo(314f, 80f, 480f, 80f)
                        quadTo(652f, 80f, 726f, 117f)
                        quadTo(800f, 154f, 800f, 240f)
                        lineTo(800f, 620f)
                        quadTo(800f, 649f, 789f, 673.5f)
                        quadTo(778f, 698f, 760f, 718f)
                        lineTo(760f, 780f)
                        quadTo(760f, 805f, 742.5f, 822.5f)
                        quadTo(725f, 840f, 700f, 840f)
                        quadTo(675f, 840f, 657.5f, 822.5f)
                        quadTo(640f, 805f, 640f, 780f)
                        lineTo(640f, 760f)
                        lineTo(320f, 760f)
                        close()
                        moveTo(240f, 400f)
                        lineTo(720f, 400f)
                        lineTo(720f, 280f)
                        lineTo(240f, 280f)
                        lineTo(240f, 400f)
                        close()
                        moveTo(340f, 640f)
                        quadTo(365f, 640f, 382.5f, 622.5f)
                        quadTo(400f, 605f, 400f, 580f)
                        quadTo(400f, 555f, 382.5f, 537.5f)
                        quadTo(365f, 520f, 340f, 520f)
                        quadTo(315f, 520f, 297.5f, 537.5f)
                        quadTo(280f, 555f, 280f, 580f)
                        quadTo(280f, 605f, 297.5f, 622.5f)
                        quadTo(315f, 640f, 340f, 640f)
                        close()
                        moveTo(620f, 640f)
                        quadTo(645f, 640f, 662.5f, 622.5f)
                        quadTo(680f, 605f, 680f, 580f)
                        quadTo(680f, 555f, 662.5f, 537.5f)
                        quadTo(645f, 520f, 620f, 520f)
                        quadTo(595f, 520f, 577.5f, 537.5f)
                        quadTo(560f, 555f, 560f, 580f)
                        quadTo(560f, 605f, 577.5f, 622.5f)
                        quadTo(595f, 640f, 620f, 640f)
                        close()
                    }
                }
                .build()

        return _BusFilled!!
    }

@Suppress("ObjectPropertyName") private var _BusFilled: ImageVector? = null
