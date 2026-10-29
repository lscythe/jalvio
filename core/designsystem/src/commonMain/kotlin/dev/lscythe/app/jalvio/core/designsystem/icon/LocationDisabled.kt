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

val JalvioIcons.LocationDisabled: ImageVector
    get() {
        if (_LocationDisabled != null) {
            return _LocationDisabled!!
        }
        _LocationDisabled =
            ImageVector.Builder(
                    name = "LocationDisabled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(918f, 482f)
                        quadTo(918f, 499f, 906.5f, 510.5f)
                        quadTo(895f, 522f, 878f, 522f)
                        lineTo(838f, 522f)
                        quadTo(835f, 548f, 829f, 571.5f)
                        quadTo(823f, 595f, 813f, 618f)
                        quadTo(807f, 633f, 791f, 638f)
                        quadTo(775f, 643f, 761f, 635f)
                        quadTo(747f, 627f, 742f, 611.5f)
                        quadTo(737f, 596f, 743f, 580f)
                        quadTo(751f, 557f, 755.5f, 532.5f)
                        quadTo(760f, 508f, 760f, 482f)
                        quadTo(760f, 366f, 678f, 284f)
                        quadTo(596f, 202f, 480f, 202f)
                        quadTo(454f, 202f, 429.5f, 206.5f)
                        quadTo(405f, 211f, 382f, 219f)
                        quadTo(366f, 225f, 350.5f, 220f)
                        quadTo(335f, 215f, 327f, 201f)
                        quadTo(319f, 187f, 324f, 171f)
                        quadTo(329f, 155f, 344f, 149f)
                        quadTo(367f, 139f, 390.5f, 133f)
                        quadTo(414f, 127f, 440f, 124f)
                        lineTo(440f, 84f)
                        quadTo(440f, 67f, 451.5f, 55.5f)
                        quadTo(463f, 44f, 480f, 44f)
                        quadTo(497f, 44f, 508.5f, 55.5f)
                        quadTo(520f, 67f, 520f, 84f)
                        lineTo(520f, 124f)
                        quadTo(645f, 138f, 734.5f, 227.5f)
                        quadTo(824f, 317f, 838f, 442f)
                        lineTo(878f, 442f)
                        quadTo(895f, 442f, 906.5f, 453.5f)
                        quadTo(918f, 465f, 918f, 482f)
                        close()
                        moveTo(440f, 880f)
                        lineTo(440f, 840f)
                        quadTo(315f, 826f, 225.5f, 736.5f)
                        quadTo(136f, 647f, 122f, 522f)
                        lineTo(82f, 522f)
                        quadTo(65f, 522f, 53.5f, 510.5f)
                        quadTo(42f, 499f, 42f, 482f)
                        quadTo(42f, 465f, 53.5f, 453.5f)
                        quadTo(65f, 442f, 82f, 442f)
                        lineTo(122f, 442f)
                        quadTo(127f, 400f, 141f, 362.5f)
                        quadTo(155f, 325f, 176f, 290f)
                        lineTo(84f, 198f)
                        quadTo(73f, 187f, 73f, 170f)
                        quadTo(73f, 153f, 84f, 142f)
                        quadTo(95f, 131f, 112f, 131f)
                        quadTo(129f, 131f, 140f, 142f)
                        lineTo(819f, 821f)
                        quadTo(831f, 833f, 831f, 849.5f)
                        quadTo(831f, 866f, 819f, 878f)
                        quadTo(807f, 889f, 790.5f, 889.5f)
                        quadTo(774f, 890f, 762f, 878f)
                        lineTo(672f, 786f)
                        quadTo(637f, 807f, 599.5f, 821f)
                        quadTo(562f, 835f, 520f, 840f)
                        lineTo(520f, 880f)
                        quadTo(520f, 897f, 508.5f, 908.5f)
                        quadTo(497f, 920f, 480f, 920f)
                        quadTo(463f, 920f, 451.5f, 908.5f)
                        quadTo(440f, 897f, 440f, 880f)
                        close()
                        moveTo(480f, 762f)
                        quadTo(516f, 762f, 550f, 753.5f)
                        quadTo(584f, 745f, 614f, 728f)
                        lineTo(234f, 348f)
                        quadTo(217f, 378f, 208.5f, 412f)
                        quadTo(200f, 446f, 200f, 482f)
                        quadTo(200f, 598f, 282f, 680f)
                        quadTo(364f, 762f, 480f, 762f)
                        close()
                    }
                }
                .build()

        return _LocationDisabled!!
    }

@Suppress("ObjectPropertyName") private var _LocationDisabled: ImageVector? = null
