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

val JalvioIcons.Walk: ImageVector
    get() {
        if (_Walk != null) {
            return _Walk!!
        }
        _Walk =
            ImageVector.Builder(
                    name = "Walk",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                    autoMirror = true,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(436f, 596f)
                        lineTo(371f, 888f)
                        quadTo(368f, 902f, 356.5f, 911f)
                        quadTo(345f, 920f, 330f, 920f)
                        quadTo(310f, 920f, 298f, 905f)
                        quadTo(286f, 890f, 290f, 871f)
                        lineTo(392f, 356f)
                        lineTo(320f, 384f)
                        lineTo(320f, 480f)
                        quadTo(320f, 497f, 308.5f, 508.5f)
                        quadTo(297f, 520f, 280f, 520f)
                        quadTo(263f, 520f, 251.5f, 508.5f)
                        quadTo(240f, 497f, 240f, 480f)
                        lineTo(240f, 358f)
                        quadTo(240f, 346f, 246.5f, 336.5f)
                        quadTo(253f, 327f, 264f, 322f)
                        lineTo(442f, 246f)
                        quadTo(456f, 240f, 471.5f, 239f)
                        quadTo(487f, 238f, 501f, 243f)
                        quadTo(515f, 248f, 527.5f, 257f)
                        quadTo(540f, 266f, 548f, 280f)
                        lineTo(588f, 344f)
                        quadTo(601f, 364f, 618.5f, 382f)
                        quadTo(636f, 400f, 658f, 413f)
                        quadTo(672f, 421f, 689f, 427.5f)
                        quadTo(706f, 434f, 723f, 437f)
                        quadTo(739f, 440f, 749.5f, 451.5f)
                        quadTo(760f, 463f, 760f, 480f)
                        quadTo(760f, 497f, 748f, 508f)
                        quadTo(736f, 519f, 719f, 517f)
                        quadTo(663f, 509f, 618.5f, 482f)
                        quadTo(574f, 455f, 541f, 417f)
                        lineTo(516f, 540f)
                        lineTo(588f, 608f)
                        quadTo(594f, 614f, 597f, 621.5f)
                        quadTo(600f, 629f, 600f, 637f)
                        lineTo(600f, 880f)
                        quadTo(600f, 897f, 588.5f, 908.5f)
                        quadTo(577f, 920f, 560f, 920f)
                        quadTo(543f, 920f, 531.5f, 908.5f)
                        quadTo(520f, 897f, 520f, 880f)
                        lineTo(520f, 660f)
                        lineTo(436f, 596f)
                        close()
                        moveTo(540f, 220f)
                        quadTo(507f, 220f, 483.5f, 196.5f)
                        quadTo(460f, 173f, 460f, 140f)
                        quadTo(460f, 107f, 483.5f, 83.5f)
                        quadTo(507f, 60f, 540f, 60f)
                        quadTo(573f, 60f, 596.5f, 83.5f)
                        quadTo(620f, 107f, 620f, 140f)
                        quadTo(620f, 173f, 596.5f, 196.5f)
                        quadTo(573f, 220f, 540f, 220f)
                        close()
                    }
                }
                .build()

        return _Walk!!
    }

@Suppress("ObjectPropertyName") private var _Walk: ImageVector? = null
