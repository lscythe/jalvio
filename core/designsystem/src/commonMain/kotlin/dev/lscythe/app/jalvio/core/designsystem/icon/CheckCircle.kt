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

val JalvioIcons.CheckCircle: ImageVector
    get() {
        if (_CheckCircle != null) {
            return _CheckCircle!!
        }
        _CheckCircle =
            ImageVector.Builder(
                    name = "CheckCircle",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(424f, 552f)
                        lineTo(338f, 466f)
                        quadTo(327f, 455f, 310f, 455f)
                        quadTo(293f, 455f, 282f, 466f)
                        quadTo(271f, 477f, 271f, 494f)
                        quadTo(271f, 511f, 282f, 522f)
                        lineTo(396f, 636f)
                        quadTo(408f, 648f, 424f, 648f)
                        quadTo(440f, 648f, 452f, 636f)
                        lineTo(678f, 410f)
                        quadTo(689f, 399f, 689f, 382f)
                        quadTo(689f, 365f, 678f, 354f)
                        quadTo(667f, 343f, 650f, 343f)
                        quadTo(633f, 343f, 622f, 354f)
                        lineTo(424f, 552f)
                        close()
                        moveTo(480f, 880f)
                        quadTo(397f, 880f, 324f, 848.5f)
                        quadTo(251f, 817f, 197f, 763f)
                        quadTo(143f, 709f, 111.5f, 636f)
                        quadTo(80f, 563f, 80f, 480f)
                        quadTo(80f, 397f, 111.5f, 324f)
                        quadTo(143f, 251f, 197f, 197f)
                        quadTo(251f, 143f, 324f, 111.5f)
                        quadTo(397f, 80f, 480f, 80f)
                        quadTo(563f, 80f, 636f, 111.5f)
                        quadTo(709f, 143f, 763f, 197f)
                        quadTo(817f, 251f, 848.5f, 324f)
                        quadTo(880f, 397f, 880f, 480f)
                        quadTo(880f, 563f, 848.5f, 636f)
                        quadTo(817f, 709f, 763f, 763f)
                        quadTo(709f, 817f, 636f, 848.5f)
                        quadTo(563f, 880f, 480f, 880f)
                        close()
                        moveTo(480f, 800f)
                        quadTo(614f, 800f, 707f, 707f)
                        quadTo(800f, 614f, 800f, 480f)
                        quadTo(800f, 346f, 707f, 253f)
                        quadTo(614f, 160f, 480f, 160f)
                        quadTo(346f, 160f, 253f, 253f)
                        quadTo(160f, 346f, 160f, 480f)
                        quadTo(160f, 614f, 253f, 707f)
                        quadTo(346f, 800f, 480f, 800f)
                        close()
                        moveTo(480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        close()
                    }
                }
                .build()

        return _CheckCircle!!
    }

@Suppress("ObjectPropertyName") private var _CheckCircle: ImageVector? = null
