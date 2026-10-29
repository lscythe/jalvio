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

val JalvioIcons.ShareFilled: ImageVector
    get() {
        if (_ShareFilled != null) {
            return _ShareFilled!!
        }
        _ShareFilled =
            ImageVector.Builder(
                    name = "ShareFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(720f, 880f)
                        quadTo(670f, 880f, 635f, 845f)
                        quadTo(600f, 810f, 600f, 760f)
                        quadTo(600f, 753f, 601f, 745.5f)
                        quadTo(602f, 738f, 604f, 732f)
                        lineTo(322f, 568f)
                        quadTo(305f, 583f, 284f, 591.5f)
                        quadTo(263f, 600f, 240f, 600f)
                        quadTo(190f, 600f, 155f, 565f)
                        quadTo(120f, 530f, 120f, 480f)
                        quadTo(120f, 430f, 155f, 395f)
                        quadTo(190f, 360f, 240f, 360f)
                        quadTo(263f, 360f, 284f, 368.5f)
                        quadTo(305f, 377f, 322f, 392f)
                        lineTo(604f, 228f)
                        quadTo(602f, 222f, 601f, 214.5f)
                        quadTo(600f, 207f, 600f, 200f)
                        quadTo(600f, 150f, 635f, 115f)
                        quadTo(670f, 80f, 720f, 80f)
                        quadTo(770f, 80f, 805f, 115f)
                        quadTo(840f, 150f, 840f, 200f)
                        quadTo(840f, 250f, 805f, 285f)
                        quadTo(770f, 320f, 720f, 320f)
                        quadTo(697f, 320f, 676f, 311.5f)
                        quadTo(655f, 303f, 638f, 288f)
                        lineTo(356f, 452f)
                        quadTo(358f, 458f, 359f, 465.5f)
                        quadTo(360f, 473f, 360f, 480f)
                        quadTo(360f, 487f, 359f, 494.5f)
                        quadTo(358f, 502f, 356f, 508f)
                        lineTo(638f, 672f)
                        quadTo(655f, 657f, 676f, 648.5f)
                        quadTo(697f, 640f, 720f, 640f)
                        quadTo(770f, 640f, 805f, 675f)
                        quadTo(840f, 710f, 840f, 760f)
                        quadTo(840f, 810f, 805f, 845f)
                        quadTo(770f, 880f, 720f, 880f)
                        close()
                    }
                }
                .build()

        return _ShareFilled!!
    }

@Suppress("ObjectPropertyName") private var _ShareFilled: ImageVector? = null
