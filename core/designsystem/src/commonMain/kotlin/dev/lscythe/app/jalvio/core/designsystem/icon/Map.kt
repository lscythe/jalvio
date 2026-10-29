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

val JalvioIcons.Map: ImageVector
    get() {
        if (_Map != null) {
            return _Map!!
        }
        _Map =
            ImageVector.Builder(
                    name = "Map",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(574f, 831f)
                        lineTo(360f, 756f)
                        lineTo(174f, 828f)
                        quadTo(164f, 832f, 154.5f, 830.5f)
                        quadTo(145f, 829f, 137f, 824f)
                        quadTo(129f, 819f, 124.5f, 810.5f)
                        quadTo(120f, 802f, 120f, 791f)
                        lineTo(120f, 230f)
                        quadTo(120f, 217f, 127.5f, 207f)
                        quadTo(135f, 197f, 148f, 192f)
                        lineTo(334f, 129f)
                        quadTo(340f, 127f, 346.5f, 126f)
                        quadTo(353f, 125f, 360f, 125f)
                        quadTo(367f, 125f, 373.5f, 126f)
                        quadTo(380f, 127f, 386f, 129f)
                        lineTo(600f, 204f)
                        lineTo(786f, 132f)
                        quadTo(796f, 128f, 805.5f, 129.5f)
                        quadTo(815f, 131f, 823f, 136f)
                        quadTo(831f, 141f, 835.5f, 149.5f)
                        quadTo(840f, 158f, 840f, 169f)
                        lineTo(840f, 730f)
                        quadTo(840f, 743f, 832.5f, 753f)
                        quadTo(825f, 763f, 812f, 768f)
                        lineTo(626f, 831f)
                        quadTo(620f, 833f, 613.5f, 834f)
                        quadTo(607f, 835f, 600f, 835f)
                        quadTo(593f, 835f, 586.5f, 834f)
                        quadTo(580f, 833f, 574f, 831f)
                        close()
                        moveTo(560f, 742f)
                        lineTo(560f, 274f)
                        lineTo(400f, 218f)
                        lineTo(400f, 686f)
                        lineTo(560f, 742f)
                        close()
                        moveTo(640f, 742f)
                        lineTo(760f, 702f)
                        lineTo(760f, 228f)
                        lineTo(640f, 274f)
                        lineTo(640f, 742f)
                        close()
                        moveTo(200f, 732f)
                        lineTo(320f, 686f)
                        lineTo(320f, 218f)
                        lineTo(200f, 258f)
                        lineTo(200f, 732f)
                        close()
                        moveTo(640f, 274f)
                        lineTo(640f, 274f)
                        lineTo(640f, 742f)
                        lineTo(640f, 742f)
                        lineTo(640f, 274f)
                        close()
                        moveTo(320f, 218f)
                        lineTo(320f, 218f)
                        lineTo(320f, 686f)
                        lineTo(320f, 686f)
                        lineTo(320f, 218f)
                        close()
                    }
                }
                .build()

        return _Map!!
    }

@Suppress("ObjectPropertyName") private var _Map: ImageVector? = null
