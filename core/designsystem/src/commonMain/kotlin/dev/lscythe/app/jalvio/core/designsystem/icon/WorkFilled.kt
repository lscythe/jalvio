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

val JalvioIcons.WorkFilled: ImageVector
    get() {
        if (_WorkFilled != null) {
            return _WorkFilled!!
        }
        _WorkFilled =
            ImageVector.Builder(
                    name = "WorkFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(160f, 840f)
                        quadTo(127f, 840f, 103.5f, 816.5f)
                        quadTo(80f, 793f, 80f, 760f)
                        lineTo(80f, 320f)
                        quadTo(80f, 287f, 103.5f, 263.5f)
                        quadTo(127f, 240f, 160f, 240f)
                        lineTo(320f, 240f)
                        lineTo(320f, 160f)
                        quadTo(320f, 127f, 343.5f, 103.5f)
                        quadTo(367f, 80f, 400f, 80f)
                        lineTo(560f, 80f)
                        quadTo(593f, 80f, 616.5f, 103.5f)
                        quadTo(640f, 127f, 640f, 160f)
                        lineTo(640f, 240f)
                        lineTo(800f, 240f)
                        quadTo(833f, 240f, 856.5f, 263.5f)
                        quadTo(880f, 287f, 880f, 320f)
                        lineTo(880f, 760f)
                        quadTo(880f, 793f, 856.5f, 816.5f)
                        quadTo(833f, 840f, 800f, 840f)
                        lineTo(160f, 840f)
                        close()
                        moveTo(400f, 240f)
                        lineTo(560f, 240f)
                        lineTo(560f, 160f)
                        quadTo(560f, 160f, 560f, 160f)
                        quadTo(560f, 160f, 560f, 160f)
                        lineTo(400f, 160f)
                        quadTo(400f, 160f, 400f, 160f)
                        quadTo(400f, 160f, 400f, 160f)
                        lineTo(400f, 240f)
                        close()
                    }
                }
                .build()

        return _WorkFilled!!
    }

@Suppress("ObjectPropertyName") private var _WorkFilled: ImageVector? = null
