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

val JalvioIcons.Layers: ImageVector
    get() {
        if (_Layers != null) {
            return _Layers!!
        }
        _Layers =
            ImageVector.Builder(
                    name = "Layers",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(161f, 594f)
                        quadTo(145f, 582f, 145.5f, 562.5f)
                        quadTo(146f, 543f, 162f, 531f)
                        quadTo(173f, 523f, 186f, 523f)
                        quadTo(199f, 523f, 210f, 531f)
                        lineTo(480f, 740f)
                        quadTo(480f, 740f, 480f, 740f)
                        quadTo(480f, 740f, 480f, 740f)
                        lineTo(750f, 531f)
                        quadTo(761f, 523f, 774f, 523f)
                        quadTo(787f, 523f, 798f, 531f)
                        quadTo(814f, 543f, 814.5f, 562.5f)
                        quadTo(815f, 582f, 799f, 594f)
                        lineTo(529f, 804f)
                        quadTo(507f, 821f, 480f, 821f)
                        quadTo(453f, 821f, 431f, 804f)
                        lineTo(161f, 594f)
                        close()
                        moveTo(431f, 602f)
                        lineTo(201f, 423f)
                        quadTo(170f, 399f, 170f, 360f)
                        quadTo(170f, 321f, 201f, 297f)
                        lineTo(431f, 118f)
                        quadTo(453f, 101f, 480f, 101f)
                        quadTo(507f, 101f, 529f, 118f)
                        lineTo(759f, 297f)
                        quadTo(790f, 321f, 790f, 360f)
                        quadTo(790f, 399f, 759f, 423f)
                        lineTo(529f, 602f)
                        quadTo(507f, 619f, 480f, 619f)
                        quadTo(453f, 619f, 431f, 602f)
                        close()
                        moveTo(480f, 538f)
                        lineTo(710f, 360f)
                        quadTo(710f, 360f, 710f, 360f)
                        quadTo(710f, 360f, 710f, 360f)
                        lineTo(480f, 182f)
                        quadTo(480f, 182f, 480f, 182f)
                        quadTo(480f, 182f, 480f, 182f)
                        lineTo(250f, 360f)
                        quadTo(250f, 360f, 250f, 360f)
                        quadTo(250f, 360f, 250f, 360f)
                        lineTo(480f, 538f)
                        quadTo(480f, 538f, 480f, 538f)
                        quadTo(480f, 538f, 480f, 538f)
                        close()
                        moveTo(480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        lineTo(480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        lineTo(480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        lineTo(480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        lineTo(480f, 360f)
                        close()
                    }
                }
                .build()

        return _Layers!!
    }

@Suppress("ObjectPropertyName") private var _Layers: ImageVector? = null
