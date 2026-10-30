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

val JalvioIcons.EventSeat: ImageVector
    get() {
        if (_EventSeat != null) {
            return _EventSeat!!
        }
        _EventSeat =
            ImageVector.Builder(
                    name = "EventSeat",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(200f, 840f)
                        quadTo(183f, 840f, 171.5f, 828.5f)
                        quadTo(160f, 817f, 160f, 800f)
                        lineTo(160f, 680f)
                        quadTo(160f, 647f, 183.5f, 623.5f)
                        quadTo(207f, 600f, 240f, 600f)
                        lineTo(720f, 600f)
                        quadTo(753f, 600f, 776.5f, 623.5f)
                        quadTo(800f, 647f, 800f, 680f)
                        lineTo(800f, 800f)
                        quadTo(800f, 817f, 788.5f, 828.5f)
                        quadTo(777f, 840f, 760f, 840f)
                        quadTo(743f, 840f, 731.5f, 828.5f)
                        quadTo(720f, 817f, 720f, 800f)
                        lineTo(720f, 680f)
                        quadTo(720f, 680f, 720f, 680f)
                        quadTo(720f, 680f, 720f, 680f)
                        lineTo(240f, 680f)
                        quadTo(240f, 680f, 240f, 680f)
                        quadTo(240f, 680f, 240f, 680f)
                        lineTo(240f, 800f)
                        quadTo(240f, 817f, 228.5f, 828.5f)
                        quadTo(217f, 840f, 200f, 840f)
                        close()
                        moveTo(180f, 560f)
                        quadTo(155f, 560f, 137.5f, 542.5f)
                        quadTo(120f, 525f, 120f, 500f)
                        quadTo(120f, 475f, 137.5f, 457.5f)
                        quadTo(155f, 440f, 180f, 440f)
                        quadTo(205f, 440f, 222.5f, 457.5f)
                        quadTo(240f, 475f, 240f, 500f)
                        quadTo(240f, 525f, 222.5f, 542.5f)
                        quadTo(205f, 560f, 180f, 560f)
                        close()
                        moveTo(280f, 560f)
                        lineTo(280f, 200f)
                        quadTo(280f, 167f, 303.5f, 143.5f)
                        quadTo(327f, 120f, 360f, 120f)
                        lineTo(600f, 120f)
                        quadTo(633f, 120f, 656.5f, 143.5f)
                        quadTo(680f, 167f, 680f, 200f)
                        lineTo(680f, 560f)
                        lineTo(280f, 560f)
                        close()
                        moveTo(780f, 560f)
                        quadTo(755f, 560f, 737.5f, 542.5f)
                        quadTo(720f, 525f, 720f, 500f)
                        quadTo(720f, 475f, 737.5f, 457.5f)
                        quadTo(755f, 440f, 780f, 440f)
                        quadTo(805f, 440f, 822.5f, 457.5f)
                        quadTo(840f, 475f, 840f, 500f)
                        quadTo(840f, 525f, 822.5f, 542.5f)
                        quadTo(805f, 560f, 780f, 560f)
                        close()
                        moveTo(360f, 480f)
                        lineTo(600f, 480f)
                        lineTo(600f, 200f)
                        quadTo(600f, 200f, 600f, 200f)
                        quadTo(600f, 200f, 600f, 200f)
                        lineTo(360f, 200f)
                        quadTo(360f, 200f, 360f, 200f)
                        quadTo(360f, 200f, 360f, 200f)
                        lineTo(360f, 480f)
                        close()
                        moveTo(360f, 480f)
                        lineTo(360f, 480f)
                        quadTo(360f, 480f, 360f, 480f)
                        quadTo(360f, 480f, 360f, 480f)
                        lineTo(600f, 480f)
                        quadTo(600f, 480f, 600f, 480f)
                        quadTo(600f, 480f, 600f, 480f)
                        lineTo(600f, 480f)
                        lineTo(360f, 480f)
                        close()
                    }
                }
                .build()

        return _EventSeat!!
    }

@Suppress("ObjectPropertyName") private var _EventSeat: ImageVector? = null
