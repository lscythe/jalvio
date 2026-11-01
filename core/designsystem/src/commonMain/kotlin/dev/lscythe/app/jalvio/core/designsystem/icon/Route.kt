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

val JalvioIcons.Route: ImageVector
    get() {
        if (_Route != null) {
            return _Route!!
        }
        _Route =
            ImageVector.Builder(
                    name = "Route",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(247f, 793f)
                        quadTo(200f, 746f, 200f, 680f)
                        lineTo(200f, 353f)
                        quadTo(165f, 340f, 142.5f, 309.5f)
                        quadTo(120f, 279f, 120f, 240f)
                        quadTo(120f, 190f, 155f, 155f)
                        quadTo(190f, 120f, 240f, 120f)
                        quadTo(290f, 120f, 325f, 155f)
                        quadTo(360f, 190f, 360f, 240f)
                        quadTo(360f, 279f, 337.5f, 309.5f)
                        quadTo(315f, 340f, 280f, 353f)
                        lineTo(280f, 680f)
                        quadTo(280f, 713f, 303.5f, 736.5f)
                        quadTo(327f, 760f, 360f, 760f)
                        quadTo(393f, 760f, 416.5f, 736.5f)
                        quadTo(440f, 713f, 440f, 680f)
                        lineTo(440f, 280f)
                        quadTo(440f, 214f, 487f, 167f)
                        quadTo(534f, 120f, 600f, 120f)
                        quadTo(666f, 120f, 713f, 167f)
                        quadTo(760f, 214f, 760f, 280f)
                        lineTo(760f, 607f)
                        quadTo(795f, 620f, 817.5f, 650.5f)
                        quadTo(840f, 681f, 840f, 720f)
                        quadTo(840f, 770f, 805f, 805f)
                        quadTo(770f, 840f, 720f, 840f)
                        quadTo(670f, 840f, 635f, 805f)
                        quadTo(600f, 770f, 600f, 720f)
                        quadTo(600f, 681f, 622.5f, 650f)
                        quadTo(645f, 619f, 680f, 607f)
                        lineTo(680f, 280f)
                        quadTo(680f, 247f, 656.5f, 223.5f)
                        quadTo(633f, 200f, 600f, 200f)
                        quadTo(567f, 200f, 543.5f, 223.5f)
                        quadTo(520f, 247f, 520f, 280f)
                        lineTo(520f, 680f)
                        quadTo(520f, 746f, 473f, 793f)
                        quadTo(426f, 840f, 360f, 840f)
                        quadTo(294f, 840f, 247f, 793f)
                        close()
                        moveTo(240f, 280f)
                        quadTo(257f, 280f, 268.5f, 268.5f)
                        quadTo(280f, 257f, 280f, 240f)
                        quadTo(280f, 223f, 268.5f, 211.5f)
                        quadTo(257f, 200f, 240f, 200f)
                        quadTo(223f, 200f, 211.5f, 211.5f)
                        quadTo(200f, 223f, 200f, 240f)
                        quadTo(200f, 257f, 211.5f, 268.5f)
                        quadTo(223f, 280f, 240f, 280f)
                        close()
                        moveTo(720f, 760f)
                        quadTo(737f, 760f, 748.5f, 748.5f)
                        quadTo(760f, 737f, 760f, 720f)
                        quadTo(760f, 703f, 748.5f, 691.5f)
                        quadTo(737f, 680f, 720f, 680f)
                        quadTo(703f, 680f, 691.5f, 691.5f)
                        quadTo(680f, 703f, 680f, 720f)
                        quadTo(680f, 737f, 691.5f, 748.5f)
                        quadTo(703f, 760f, 720f, 760f)
                        close()
                        moveTo(240f, 240f)
                        close()
                        moveTo(720f, 720f)
                        close()
                    }
                }
                .build()

        return _Route!!
    }

@Suppress("ObjectPropertyName") private var _Route: ImageVector? = null
