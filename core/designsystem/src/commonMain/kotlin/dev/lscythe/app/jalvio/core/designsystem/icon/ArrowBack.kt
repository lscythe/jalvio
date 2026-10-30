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

val JalvioIcons.ArrowBack: ImageVector
    get() {
        if (_ArrowBack != null) {
            return _ArrowBack!!
        }
        _ArrowBack =
            ImageVector.Builder(
                    name = "ArrowBack",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                    autoMirror = true,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(313f, 520f)
                        lineTo(509f, 716f)
                        quadTo(521f, 728f, 520.5f, 744f)
                        quadTo(520f, 760f, 508f, 772f)
                        quadTo(496f, 783f, 480f, 783.5f)
                        quadTo(464f, 784f, 452f, 772f)
                        lineTo(188f, 508f)
                        quadTo(182f, 502f, 179.5f, 495f)
                        quadTo(177f, 488f, 177f, 480f)
                        quadTo(177f, 472f, 179.5f, 465f)
                        quadTo(182f, 458f, 188f, 452f)
                        lineTo(452f, 188f)
                        quadTo(463f, 177f, 479.5f, 177f)
                        quadTo(496f, 177f, 508f, 188f)
                        quadTo(520f, 200f, 520f, 216.5f)
                        quadTo(520f, 233f, 508f, 245f)
                        lineTo(313f, 440f)
                        lineTo(760f, 440f)
                        quadTo(777f, 440f, 788.5f, 451.5f)
                        quadTo(800f, 463f, 800f, 480f)
                        quadTo(800f, 497f, 788.5f, 508.5f)
                        quadTo(777f, 520f, 760f, 520f)
                        lineTo(313f, 520f)
                        close()
                    }
                }
                .build()

        return _ArrowBack!!
    }

@Suppress("ObjectPropertyName") private var _ArrowBack: ImageVector? = null
