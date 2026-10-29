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

val JalvioIcons.ElevatorFilled: ImageVector
    get() {
        if (_ElevatorFilled != null) {
            return _ElevatorFilled!!
        }
        _ElevatorFilled =
            ImageVector.Builder(
                    name = "ElevatorFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(280f, 560f)
                        lineTo(280f, 680f)
                        quadTo(280f, 697f, 291.5f, 708.5f)
                        quadTo(303f, 720f, 320f, 720f)
                        lineTo(360f, 720f)
                        quadTo(377f, 720f, 388.5f, 708.5f)
                        quadTo(400f, 697f, 400f, 680f)
                        lineTo(400f, 560f)
                        lineTo(400f, 560f)
                        quadTo(411f, 549f, 425.5f, 542.5f)
                        quadTo(440f, 536f, 440f, 520f)
                        lineTo(440f, 460f)
                        quadTo(440f, 427f, 416.5f, 403.5f)
                        quadTo(393f, 380f, 360f, 380f)
                        lineTo(320f, 380f)
                        quadTo(287f, 380f, 263.5f, 403.5f)
                        quadTo(240f, 427f, 240f, 460f)
                        lineTo(240f, 520f)
                        quadTo(240f, 536f, 254.5f, 542.5f)
                        quadTo(269f, 549f, 280f, 560f)
                        lineTo(280f, 560f)
                        close()
                        moveTo(340f, 340f)
                        quadTo(361f, 340f, 375.5f, 325.5f)
                        quadTo(390f, 311f, 390f, 290f)
                        quadTo(390f, 269f, 375.5f, 254.5f)
                        quadTo(361f, 240f, 340f, 240f)
                        quadTo(319f, 240f, 304.5f, 254.5f)
                        quadTo(290f, 269f, 290f, 290f)
                        quadTo(290f, 311f, 304.5f, 325.5f)
                        quadTo(319f, 340f, 340f, 340f)
                        close()
                        moveTo(556f, 440f)
                        lineTo(684f, 440f)
                        quadTo(696f, 440f, 701.5f, 429.5f)
                        quadTo(707f, 419f, 701f, 409f)
                        lineTo(637f, 307f)
                        quadTo(631f, 297f, 620f, 297f)
                        quadTo(609f, 297f, 603f, 307f)
                        lineTo(539f, 409f)
                        quadTo(533f, 419f, 538.5f, 429.5f)
                        quadTo(544f, 440f, 556f, 440f)
                        close()
                        moveTo(637f, 653f)
                        lineTo(701f, 551f)
                        quadTo(707f, 541f, 701.5f, 530.5f)
                        quadTo(696f, 520f, 684f, 520f)
                        lineTo(556f, 520f)
                        quadTo(544f, 520f, 538.5f, 530.5f)
                        quadTo(533f, 541f, 539f, 551f)
                        lineTo(603f, 653f)
                        quadTo(609f, 663f, 620f, 663f)
                        quadTo(631f, 663f, 637f, 653f)
                        close()
                        moveTo(200f, 840f)
                        quadTo(167f, 840f, 143.5f, 816.5f)
                        quadTo(120f, 793f, 120f, 760f)
                        lineTo(120f, 200f)
                        quadTo(120f, 167f, 143.5f, 143.5f)
                        quadTo(167f, 120f, 200f, 120f)
                        lineTo(760f, 120f)
                        quadTo(793f, 120f, 816.5f, 143.5f)
                        quadTo(840f, 167f, 840f, 200f)
                        lineTo(840f, 760f)
                        quadTo(840f, 793f, 816.5f, 816.5f)
                        quadTo(793f, 840f, 760f, 840f)
                        lineTo(200f, 840f)
                        close()
                    }
                }
                .build()

        return _ElevatorFilled!!
    }

@Suppress("ObjectPropertyName") private var _ElevatorFilled: ImageVector? = null
