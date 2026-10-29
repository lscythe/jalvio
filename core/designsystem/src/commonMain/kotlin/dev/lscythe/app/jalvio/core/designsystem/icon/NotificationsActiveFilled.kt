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

val JalvioIcons.NotificationsActiveFilled: ImageVector
    get() {
        if (_NotificationsActiveFilled != null) {
            return _NotificationsActiveFilled!!
        }
        _NotificationsActiveFilled =
            ImageVector.Builder(
                    name = "NotificationsActiveFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(200f, 760f)
                        quadTo(183f, 760f, 171.5f, 748.5f)
                        quadTo(160f, 737f, 160f, 720f)
                        quadTo(160f, 703f, 171.5f, 691.5f)
                        quadTo(183f, 680f, 200f, 680f)
                        lineTo(240f, 680f)
                        lineTo(240f, 400f)
                        quadTo(240f, 317f, 290f, 252.5f)
                        quadTo(340f, 188f, 420f, 168f)
                        lineTo(420f, 140f)
                        quadTo(420f, 115f, 437.5f, 97.5f)
                        quadTo(455f, 80f, 480f, 80f)
                        quadTo(505f, 80f, 522.5f, 97.5f)
                        quadTo(540f, 115f, 540f, 140f)
                        lineTo(540f, 168f)
                        quadTo(620f, 188f, 670f, 252.5f)
                        quadTo(720f, 317f, 720f, 400f)
                        lineTo(720f, 680f)
                        lineTo(760f, 680f)
                        quadTo(777f, 680f, 788.5f, 691.5f)
                        quadTo(800f, 703f, 800f, 720f)
                        quadTo(800f, 737f, 788.5f, 748.5f)
                        quadTo(777f, 760f, 760f, 760f)
                        lineTo(200f, 760f)
                        close()
                        moveTo(480f, 880f)
                        quadTo(447f, 880f, 423.5f, 856.5f)
                        quadTo(400f, 833f, 400f, 800f)
                        lineTo(560f, 800f)
                        quadTo(560f, 833f, 536.5f, 856.5f)
                        quadTo(513f, 880f, 480f, 880f)
                        close()
                        moveTo(120f, 400f)
                        quadTo(103f, 400f, 91.5f, 387f)
                        quadTo(80f, 374f, 82f, 357f)
                        quadTo(90f, 282f, 124f, 217.5f)
                        quadTo(158f, 153f, 211f, 105f)
                        quadTo(224f, 94f, 240.5f, 95f)
                        quadTo(257f, 96f, 267f, 110f)
                        quadTo(277f, 124f, 275f, 140f)
                        quadTo(273f, 156f, 260f, 168f)
                        quadTo(221f, 205f, 196f, 254f)
                        quadTo(171f, 303f, 163f, 360f)
                        quadTo(161f, 377f, 149f, 388.5f)
                        quadTo(137f, 400f, 120f, 400f)
                        close()
                        moveTo(840f, 400f)
                        quadTo(823f, 400f, 811f, 388.5f)
                        quadTo(799f, 377f, 797f, 360f)
                        quadTo(789f, 303f, 764f, 254f)
                        quadTo(739f, 205f, 700f, 168f)
                        quadTo(687f, 156f, 685f, 140f)
                        quadTo(683f, 124f, 693f, 110f)
                        quadTo(703f, 96f, 719.5f, 95f)
                        quadTo(736f, 94f, 749f, 105f)
                        quadTo(802f, 153f, 836f, 217.5f)
                        quadTo(870f, 282f, 878f, 357f)
                        quadTo(880f, 374f, 868.5f, 387f)
                        quadTo(857f, 400f, 840f, 400f)
                        close()
                    }
                }
                .build()

        return _NotificationsActiveFilled!!
    }

@Suppress("ObjectPropertyName") private var _NotificationsActiveFilled: ImageVector? = null
