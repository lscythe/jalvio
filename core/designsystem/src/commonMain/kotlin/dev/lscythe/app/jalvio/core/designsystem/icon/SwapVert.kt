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

val JalvioIcons.SwapVert: ImageVector
    get() {
        if (_SwapVert != null) {
            return _SwapVert!!
        }
        _SwapVert =
            ImageVector.Builder(
                    name = "SwapVert",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(360f, 520f)
                        quadTo(343f, 520f, 331.5f, 508.5f)
                        quadTo(320f, 497f, 320f, 480f)
                        lineTo(320f, 233f)
                        lineTo(245f, 308f)
                        quadTo(234f, 319f, 217.5f, 319f)
                        quadTo(201f, 319f, 189f, 308f)
                        quadTo(177f, 296f, 177f, 279.5f)
                        quadTo(177f, 263f, 189f, 251f)
                        lineTo(332f, 108f)
                        quadTo(338f, 102f, 345f, 99.5f)
                        quadTo(352f, 97f, 360f, 97f)
                        quadTo(368f, 97f, 375f, 99.5f)
                        quadTo(382f, 102f, 388f, 108f)
                        lineTo(532f, 252f)
                        quadTo(544f, 264f, 543.5f, 280f)
                        quadTo(543f, 296f, 531f, 308f)
                        quadTo(519f, 319f, 503f, 319.5f)
                        quadTo(487f, 320f, 475f, 308f)
                        lineTo(400f, 233f)
                        lineTo(400f, 480f)
                        quadTo(400f, 497f, 388.5f, 508.5f)
                        quadTo(377f, 520f, 360f, 520f)
                        close()
                        moveTo(600f, 863f)
                        quadTo(592f, 863f, 585f, 860.5f)
                        quadTo(578f, 858f, 572f, 852f)
                        lineTo(428f, 708f)
                        quadTo(416f, 696f, 416.5f, 680f)
                        quadTo(417f, 664f, 429f, 652f)
                        quadTo(441f, 641f, 457f, 640.5f)
                        quadTo(473f, 640f, 485f, 652f)
                        lineTo(560f, 727f)
                        lineTo(560f, 480f)
                        quadTo(560f, 463f, 571.5f, 451.5f)
                        quadTo(583f, 440f, 600f, 440f)
                        quadTo(617f, 440f, 628.5f, 451.5f)
                        quadTo(640f, 463f, 640f, 480f)
                        lineTo(640f, 727f)
                        lineTo(715f, 652f)
                        quadTo(726f, 641f, 742.5f, 641f)
                        quadTo(759f, 641f, 771f, 652f)
                        quadTo(783f, 664f, 783f, 680.5f)
                        quadTo(783f, 697f, 771f, 709f)
                        lineTo(628f, 852f)
                        quadTo(622f, 858f, 615f, 860.5f)
                        quadTo(608f, 863f, 600f, 863f)
                        close()
                    }
                }
                .build()

        return _SwapVert!!
    }

@Suppress("ObjectPropertyName") private var _SwapVert: ImageVector? = null
