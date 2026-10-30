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

val JalvioIcons.Wc: ImageVector
    get() {
        if (_Wc != null) {
            return _Wc!!
        }
        _Wc =
            ImageVector.Builder(
                    name = "Wc",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(220f, 840f)
                        lineTo(220f, 580f)
                        lineTo(200f, 580f)
                        quadTo(183f, 580f, 171.5f, 568.5f)
                        quadTo(160f, 557f, 160f, 540f)
                        lineTo(160f, 360f)
                        quadTo(160f, 327f, 183.5f, 303.5f)
                        quadTo(207f, 280f, 240f, 280f)
                        lineTo(360f, 280f)
                        quadTo(393f, 280f, 416.5f, 303.5f)
                        quadTo(440f, 327f, 440f, 360f)
                        lineTo(440f, 540f)
                        quadTo(440f, 557f, 428.5f, 568.5f)
                        quadTo(417f, 580f, 400f, 580f)
                        lineTo(380f, 580f)
                        lineTo(380f, 840f)
                        quadTo(380f, 857f, 368.5f, 868.5f)
                        quadTo(357f, 880f, 340f, 880f)
                        lineTo(260f, 880f)
                        quadTo(243f, 880f, 231.5f, 868.5f)
                        quadTo(220f, 857f, 220f, 840f)
                        close()
                        moveTo(300f, 240f)
                        quadTo(267f, 240f, 243.5f, 216.5f)
                        quadTo(220f, 193f, 220f, 160f)
                        quadTo(220f, 127f, 243.5f, 103.5f)
                        quadTo(267f, 80f, 300f, 80f)
                        quadTo(333f, 80f, 356.5f, 103.5f)
                        quadTo(380f, 127f, 380f, 160f)
                        quadTo(380f, 193f, 356.5f, 216.5f)
                        quadTo(333f, 240f, 300f, 240f)
                        close()
                        moveTo(600f, 840f)
                        lineTo(600f, 640f)
                        lineTo(535f, 640f)
                        quadTo(515f, 640f, 503f, 623.5f)
                        quadTo(491f, 607f, 498f, 587f)
                        lineTo(582f, 334f)
                        quadTo(590f, 308f, 611.5f, 294f)
                        quadTo(633f, 280f, 660f, 280f)
                        quadTo(687f, 280f, 708.5f, 294f)
                        quadTo(730f, 308f, 738f, 334f)
                        lineTo(822f, 587f)
                        quadTo(829f, 607f, 817f, 623.5f)
                        quadTo(805f, 640f, 785f, 640f)
                        lineTo(720f, 640f)
                        lineTo(720f, 840f)
                        quadTo(720f, 857f, 708.5f, 868.5f)
                        quadTo(697f, 880f, 680f, 880f)
                        lineTo(640f, 880f)
                        quadTo(623f, 880f, 611.5f, 868.5f)
                        quadTo(600f, 857f, 600f, 840f)
                        close()
                        moveTo(660f, 240f)
                        quadTo(627f, 240f, 603.5f, 216.5f)
                        quadTo(580f, 193f, 580f, 160f)
                        quadTo(580f, 127f, 603.5f, 103.5f)
                        quadTo(627f, 80f, 660f, 80f)
                        quadTo(693f, 80f, 716.5f, 103.5f)
                        quadTo(740f, 127f, 740f, 160f)
                        quadTo(740f, 193f, 716.5f, 216.5f)
                        quadTo(693f, 240f, 660f, 240f)
                        close()
                    }
                }
                .build()

        return _Wc!!
    }

@Suppress("ObjectPropertyName") private var _Wc: ImageVector? = null
