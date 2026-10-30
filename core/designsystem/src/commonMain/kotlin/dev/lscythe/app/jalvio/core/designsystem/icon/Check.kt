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

val JalvioIcons.Check: ImageVector
    get() {
        if (_Check != null) {
            return _Check!!
        }
        _Check =
            ImageVector.Builder(
                    name = "Check",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(382f, 606f)
                        lineTo(721f, 267f)
                        quadTo(733f, 255f, 749f, 255f)
                        quadTo(765f, 255f, 777f, 267f)
                        quadTo(789f, 279f, 789f, 295.5f)
                        quadTo(789f, 312f, 777f, 324f)
                        lineTo(410f, 692f)
                        quadTo(398f, 704f, 382f, 704f)
                        quadTo(366f, 704f, 354f, 692f)
                        lineTo(182f, 520f)
                        quadTo(170f, 508f, 170.5f, 491.5f)
                        quadTo(171f, 475f, 183f, 463f)
                        quadTo(195f, 451f, 211.5f, 451f)
                        quadTo(228f, 451f, 240f, 463f)
                        lineTo(382f, 606f)
                        close()
                    }
                }
                .build()

        return _Check!!
    }

@Suppress("ObjectPropertyName") private var _Check: ImageVector? = null
