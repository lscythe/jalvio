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

val JalvioIcons.AirlineSeatReclineExtra: ImageVector
    get() {
        if (_AirlineSeatReclineExtra != null) {
            return _AirlineSeatReclineExtra!!
        }
        _AirlineSeatReclineExtra =
            ImageVector.Builder(
                    name = "AirlineSeatReclineExtra",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(340f, 240f)
                        quadTo(307f, 240f, 283.5f, 216.5f)
                        quadTo(260f, 193f, 260f, 160f)
                        quadTo(260f, 127f, 283.5f, 103.5f)
                        quadTo(307f, 80f, 340f, 80f)
                        quadTo(373f, 80f, 396.5f, 103.5f)
                        quadTo(420f, 127f, 420f, 160f)
                        quadTo(420f, 193f, 396.5f, 216.5f)
                        quadTo(373f, 240f, 340f, 240f)
                        close()
                        moveTo(520f, 800f)
                        lineTo(302f, 800f)
                        quadTo(269f, 800f, 241.5f, 776.5f)
                        quadTo(214f, 753f, 207f, 720f)
                        lineTo(129f, 328f)
                        quadTo(125f, 309f, 137f, 294.5f)
                        quadTo(149f, 280f, 169f, 280f)
                        quadTo(183f, 280f, 194f, 289f)
                        quadTo(205f, 298f, 208f, 312f)
                        lineTo(290f, 720f)
                        lineTo(520f, 720f)
                        quadTo(537f, 720f, 548.5f, 731.5f)
                        quadTo(560f, 743f, 560f, 760f)
                        quadTo(560f, 777f, 548.5f, 788.5f)
                        quadTo(537f, 800f, 520f, 800f)
                        close()
                        moveTo(760f, 845f)
                        lineTo(664f, 680f)
                        lineTo(386f, 680f)
                        quadTo(357f, 680f, 335.5f, 662.5f)
                        quadTo(314f, 645f, 308f, 616f)
                        lineTo(264f, 402f)
                        quadTo(253f, 354f, 286.5f, 317f)
                        quadTo(320f, 280f, 368f, 280f)
                        quadTo(403f, 280f, 431.5f, 301f)
                        quadTo(460f, 322f, 468f, 358f)
                        lineTo(512f, 560f)
                        lineTo(642f, 560f)
                        quadTo(663f, 560f, 681f, 571f)
                        quadTo(699f, 582f, 710f, 600f)
                        lineTo(830f, 805f)
                        quadTo(838f, 819f, 833.5f, 835.5f)
                        quadTo(829f, 852f, 815f, 860f)
                        quadTo(801f, 868f, 784.5f, 863.5f)
                        quadTo(768f, 859f, 760f, 845f)
                        close()
                    }
                }
                .build()

        return _AirlineSeatReclineExtra!!
    }

@Suppress("ObjectPropertyName") private var _AirlineSeatReclineExtra: ImageVector? = null
