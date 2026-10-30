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

val JalvioIcons.Groups: ImageVector
    get() {
        if (_Groups != null) {
            return _Groups!!
        }
        _Groups =
            ImageVector.Builder(
                    name = "Groups",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(40f, 720f)
                        quadTo(23f, 720f, 11.5f, 708.5f)
                        quadTo(0f, 697f, 0f, 680f)
                        lineTo(0f, 657f)
                        quadTo(0f, 614f, 44f, 587f)
                        quadTo(88f, 560f, 160f, 560f)
                        quadTo(173f, 560f, 185f, 560.5f)
                        quadTo(197f, 561f, 208f, 563f)
                        quadTo(194f, 584f, 187f, 607f)
                        quadTo(180f, 630f, 180f, 655f)
                        lineTo(180f, 720f)
                        lineTo(40f, 720f)
                        close()
                        moveTo(280f, 720f)
                        quadTo(263f, 720f, 251.5f, 708.5f)
                        quadTo(240f, 697f, 240f, 680f)
                        lineTo(240f, 655f)
                        quadTo(240f, 623f, 257.5f, 596.5f)
                        quadTo(275f, 570f, 307f, 550f)
                        quadTo(339f, 530f, 383.5f, 520f)
                        quadTo(428f, 510f, 480f, 510f)
                        quadTo(533f, 510f, 577.5f, 520f)
                        quadTo(622f, 530f, 654f, 550f)
                        quadTo(686f, 570f, 703f, 596.5f)
                        quadTo(720f, 623f, 720f, 655f)
                        lineTo(720f, 680f)
                        quadTo(720f, 697f, 708.5f, 708.5f)
                        quadTo(697f, 720f, 680f, 720f)
                        lineTo(280f, 720f)
                        close()
                        moveTo(780f, 720f)
                        lineTo(780f, 655f)
                        quadTo(780f, 629f, 773.5f, 606f)
                        quadTo(767f, 583f, 754f, 563f)
                        quadTo(765f, 561f, 776.5f, 560.5f)
                        quadTo(788f, 560f, 800f, 560f)
                        quadTo(872f, 560f, 916f, 586.5f)
                        quadTo(960f, 613f, 960f, 657f)
                        lineTo(960f, 680f)
                        quadTo(960f, 697f, 948.5f, 708.5f)
                        quadTo(937f, 720f, 920f, 720f)
                        lineTo(780f, 720f)
                        close()
                        moveTo(325f, 640f)
                        lineTo(636f, 640f)
                        lineTo(636f, 640f)
                        quadTo(626f, 620f, 580.5f, 605f)
                        quadTo(535f, 590f, 480f, 590f)
                        quadTo(425f, 590f, 379.5f, 605f)
                        quadTo(334f, 620f, 325f, 640f)
                        lineTo(325f, 640f)
                        close()
                        moveTo(160f, 520f)
                        quadTo(127f, 520f, 103.5f, 496.5f)
                        quadTo(80f, 473f, 80f, 440f)
                        quadTo(80f, 406f, 103.5f, 383f)
                        quadTo(127f, 360f, 160f, 360f)
                        quadTo(194f, 360f, 217f, 383f)
                        quadTo(240f, 406f, 240f, 440f)
                        quadTo(240f, 473f, 217f, 496.5f)
                        quadTo(194f, 520f, 160f, 520f)
                        close()
                        moveTo(800f, 520f)
                        quadTo(767f, 520f, 743.5f, 496.5f)
                        quadTo(720f, 473f, 720f, 440f)
                        quadTo(720f, 406f, 743.5f, 383f)
                        quadTo(767f, 360f, 800f, 360f)
                        quadTo(834f, 360f, 857f, 383f)
                        quadTo(880f, 406f, 880f, 440f)
                        quadTo(880f, 473f, 857f, 496.5f)
                        quadTo(834f, 520f, 800f, 520f)
                        close()
                        moveTo(480f, 480f)
                        quadTo(430f, 480f, 395f, 445f)
                        quadTo(360f, 410f, 360f, 360f)
                        quadTo(360f, 309f, 395f, 274.5f)
                        quadTo(430f, 240f, 480f, 240f)
                        quadTo(531f, 240f, 565.5f, 274.5f)
                        quadTo(600f, 309f, 600f, 360f)
                        quadTo(600f, 410f, 565.5f, 445f)
                        quadTo(531f, 480f, 480f, 480f)
                        close()
                        moveTo(480f, 400f)
                        quadTo(497f, 400f, 508.5f, 388.5f)
                        quadTo(520f, 377f, 520f, 360f)
                        quadTo(520f, 343f, 508.5f, 331.5f)
                        quadTo(497f, 320f, 480f, 320f)
                        quadTo(463f, 320f, 451.5f, 331.5f)
                        quadTo(440f, 343f, 440f, 360f)
                        quadTo(440f, 377f, 451.5f, 388.5f)
                        quadTo(463f, 400f, 480f, 400f)
                        close()
                        moveTo(481f, 640f)
                        lineTo(481f, 640f)
                        quadTo(481f, 640f, 481f, 640f)
                        quadTo(481f, 640f, 481f, 640f)
                        quadTo(481f, 640f, 481f, 640f)
                        quadTo(481f, 640f, 481f, 640f)
                        lineTo(481f, 640f)
                        lineTo(481f, 640f)
                        close()
                        moveTo(480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        quadTo(480f, 360f, 480f, 360f)
                        close()
                    }
                }
                .build()

        return _Groups!!
    }

@Suppress("ObjectPropertyName") private var _Groups: ImageVector? = null
