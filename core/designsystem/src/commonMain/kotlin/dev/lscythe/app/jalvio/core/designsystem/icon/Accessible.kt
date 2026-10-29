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

val JalvioIcons.Accessible: ImageVector
    get() {
        if (_Accessible != null) {
            return _Accessible!!
        }
        _Accessible =
            ImageVector.Builder(
                    name = "Accessible",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(400f, 880f)
                        quadTo(317f, 880f, 258.5f, 821.5f)
                        quadTo(200f, 763f, 200f, 680f)
                        quadTo(200f, 629f, 223f, 586f)
                        quadTo(246f, 543f, 284f, 516f)
                        quadTo(298f, 506f, 314f, 509f)
                        quadTo(330f, 512f, 340f, 526f)
                        quadTo(350f, 540f, 347.5f, 556f)
                        quadTo(345f, 572f, 331f, 582f)
                        quadTo(308f, 599f, 294f, 624.5f)
                        quadTo(280f, 650f, 280f, 680f)
                        quadTo(280f, 730f, 315f, 765f)
                        quadTo(350f, 800f, 400f, 800f)
                        quadTo(430f, 800f, 455.5f, 786f)
                        quadTo(481f, 772f, 498f, 749f)
                        quadTo(508f, 735f, 524f, 732.5f)
                        quadTo(540f, 730f, 554f, 740f)
                        quadTo(568f, 750f, 571f, 766f)
                        quadTo(574f, 782f, 564f, 796f)
                        quadTo(537f, 834f, 494f, 857f)
                        quadTo(451f, 880f, 400f, 880f)
                        close()
                        moveTo(720f, 880f)
                        quadTo(703f, 880f, 691.5f, 868.5f)
                        quadTo(680f, 857f, 680f, 840f)
                        lineTo(680f, 680f)
                        quadTo(680f, 680f, 680f, 680f)
                        quadTo(680f, 680f, 680f, 680f)
                        lineTo(480f, 680f)
                        quadTo(447f, 680f, 423.5f, 656.5f)
                        quadTo(400f, 633f, 400f, 600f)
                        lineTo(400f, 360f)
                        quadTo(400f, 327f, 423.5f, 303.5f)
                        quadTo(447f, 280f, 480f, 280f)
                        quadTo(504f, 280f, 521.5f, 290.5f)
                        quadTo(539f, 301f, 559f, 324f)
                        quadTo(604f, 378f, 640.5f, 403f)
                        quadTo(677f, 428f, 719f, 436f)
                        quadTo(736f, 439f, 748f, 451f)
                        quadTo(760f, 463f, 760f, 480f)
                        quadTo(760f, 497f, 747.5f, 508f)
                        quadTo(735f, 519f, 719f, 516f)
                        quadTo(675f, 508f, 633.5f, 488f)
                        quadTo(592f, 468f, 560f, 442f)
                        lineTo(560f, 580f)
                        lineTo(680f, 580f)
                        quadTo(713f, 580f, 736.5f, 603.5f)
                        quadTo(760f, 627f, 760f, 660f)
                        lineTo(760f, 840f)
                        quadTo(760f, 857f, 748.5f, 868.5f)
                        quadTo(737f, 880f, 720f, 880f)
                        close()
                        moveTo(480f, 240f)
                        quadTo(447f, 240f, 423.5f, 216.5f)
                        quadTo(400f, 193f, 400f, 160f)
                        quadTo(400f, 127f, 423.5f, 103.5f)
                        quadTo(447f, 80f, 480f, 80f)
                        quadTo(513f, 80f, 536.5f, 103.5f)
                        quadTo(560f, 127f, 560f, 160f)
                        quadTo(560f, 193f, 536.5f, 216.5f)
                        quadTo(513f, 240f, 480f, 240f)
                        close()
                    }
                }
                .build()

        return _Accessible!!
    }

@Suppress("ObjectPropertyName") private var _Accessible: ImageVector? = null
