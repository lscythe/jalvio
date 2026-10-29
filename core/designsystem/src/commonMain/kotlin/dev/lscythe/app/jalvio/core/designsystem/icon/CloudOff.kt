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

val JalvioIcons.CloudOff: ImageVector
    get() {
        if (_CloudOff != null) {
            return _CloudOff!!
        }
        _CloudOff =
            ImageVector.Builder(
                    name = "CloudOff",
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
                        moveTo(260f, 720f)
                        lineTo(606f, 720f)
                        lineTo(284f, 398f)
                        quadTo(282f, 409f, 281f, 419f)
                        quadTo(280f, 429f, 280f, 440f)
                        lineTo(260f, 440f)
                        quadTo(202f, 440f, 161f, 481f)
                        quadTo(120f, 522f, 120f, 580f)
                        quadTo(120f, 638f, 161f, 679f)
                        quadTo(202f, 720f, 260f, 720f)
                        close()
                        moveTo(445f, 559f)
                        quadTo(445f, 559f, 445f, 559f)
                        quadTo(445f, 559f, 445f, 559f)
                        quadTo(445f, 559f, 445f, 559f)
                        quadTo(445f, 559f, 445f, 559f)
                        lineTo(445f, 559f)
                        quadTo(445f, 559f, 445f, 559f)
                        quadTo(445f, 559f, 445f, 559f)
                        lineTo(445f, 559f)
                        lineTo(445f, 559f)
                        lineTo(445f, 559f)
                        close()
                        moveTo(864f, 750f)
                        lineTo(806f, 694f)
                        quadTo(823f, 680f, 831.5f, 661.5f)
                        quadTo(840f, 643f, 840f, 620f)
                        quadTo(840f, 578f, 811f, 549f)
                        quadTo(782f, 520f, 740f, 520f)
                        lineTo(680f, 520f)
                        lineTo(680f, 440f)
                        quadTo(680f, 357f, 621.5f, 298.5f)
                        quadTo(563f, 240f, 480f, 240f)
                        quadTo(453f, 240f, 428f, 246.5f)
                        quadTo(403f, 253f, 380f, 267f)
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
                        moveTo(593f, 481f)
                        lineTo(593f, 481f)
                        lineTo(593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        lineTo(593f, 481f)
                        lineTo(593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        quadTo(593f, 481f, 593f, 481f)
                        close()
                    }
                }
                .build()

        return _CloudOff!!
    }

@Suppress("ObjectPropertyName") private var _CloudOff: ImageVector? = null
