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

val JalvioIcons.Signpost: ImageVector
    get() {
        if (_Signpost != null) {
            return _Signpost!!
        }
        _Signpost =
            ImageVector.Builder(
                    name = "Signpost",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(440f, 840f)
                        lineTo(440f, 720f)
                        lineTo(273f, 720f)
                        quadTo(257f, 720f, 242.5f, 714f)
                        quadTo(228f, 708f, 217f, 697f)
                        lineTo(148f, 628f)
                        quadTo(136f, 616f, 136f, 600f)
                        quadTo(136f, 584f, 148f, 572f)
                        lineTo(217f, 503f)
                        quadTo(228f, 492f, 242.5f, 486f)
                        quadTo(257f, 480f, 273f, 480f)
                        lineTo(440f, 480f)
                        lineTo(440f, 400f)
                        lineTo(200f, 400f)
                        quadTo(183f, 400f, 171.5f, 388.5f)
                        quadTo(160f, 377f, 160f, 360f)
                        lineTo(160f, 200f)
                        quadTo(160f, 183f, 171.5f, 171.5f)
                        quadTo(183f, 160f, 200f, 160f)
                        lineTo(440f, 160f)
                        lineTo(440f, 120f)
                        quadTo(440f, 103f, 451.5f, 91.5f)
                        quadTo(463f, 80f, 480f, 80f)
                        quadTo(497f, 80f, 508.5f, 91.5f)
                        quadTo(520f, 103f, 520f, 120f)
                        lineTo(520f, 160f)
                        lineTo(687f, 160f)
                        quadTo(703f, 160f, 717.5f, 166f)
                        quadTo(732f, 172f, 743f, 183f)
                        lineTo(812f, 252f)
                        quadTo(824f, 264f, 824f, 280f)
                        quadTo(824f, 296f, 812f, 308f)
                        lineTo(743f, 377f)
                        quadTo(732f, 388f, 717.5f, 394f)
                        quadTo(703f, 400f, 687f, 400f)
                        lineTo(520f, 400f)
                        lineTo(520f, 480f)
                        lineTo(760f, 480f)
                        quadTo(777f, 480f, 788.5f, 491.5f)
                        quadTo(800f, 503f, 800f, 520f)
                        lineTo(800f, 680f)
                        quadTo(800f, 697f, 788.5f, 708.5f)
                        quadTo(777f, 720f, 760f, 720f)
                        lineTo(520f, 720f)
                        lineTo(520f, 840f)
                        quadTo(520f, 857f, 508.5f, 868.5f)
                        quadTo(497f, 880f, 480f, 880f)
                        quadTo(463f, 880f, 451.5f, 868.5f)
                        quadTo(440f, 857f, 440f, 840f)
                        close()
                        moveTo(240f, 320f)
                        lineTo(687f, 320f)
                        lineTo(727f, 280f)
                        lineTo(687f, 240f)
                        lineTo(240f, 240f)
                        lineTo(240f, 320f)
                        close()
                        moveTo(273f, 640f)
                        lineTo(720f, 640f)
                        lineTo(720f, 560f)
                        lineTo(273f, 560f)
                        lineTo(233f, 600f)
                        lineTo(273f, 640f)
                        close()
                        moveTo(240f, 320f)
                        lineTo(240f, 240f)
                        lineTo(240f, 240f)
                        lineTo(240f, 280f)
                        lineTo(240f, 320f)
                        lineTo(240f, 320f)
                        close()
                        moveTo(720f, 640f)
                        lineTo(720f, 600f)
                        lineTo(720f, 560f)
                        lineTo(720f, 560f)
                        lineTo(720f, 640f)
                        lineTo(720f, 640f)
                        close()
                    }
                }
                .build()

        return _Signpost!!
    }

@Suppress("ObjectPropertyName") private var _Signpost: ImageVector? = null
