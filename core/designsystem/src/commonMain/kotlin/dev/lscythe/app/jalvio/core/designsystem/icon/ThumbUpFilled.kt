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

val JalvioIcons.ThumbUpFilled: ImageVector
    get() {
        if (_ThumbUpFilled != null) {
            return _ThumbUpFilled!!
        }
        _ThumbUpFilled =
            ImageVector.Builder(
                    name = "ThumbUpFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(840f, 320f)
                        quadTo(872f, 320f, 896f, 344f)
                        quadTo(920f, 368f, 920f, 400f)
                        lineTo(920f, 480f)
                        quadTo(920f, 487f, 918.5f, 495f)
                        quadTo(917f, 503f, 914f, 510f)
                        lineTo(794f, 792f)
                        quadTo(785f, 812f, 764f, 826f)
                        quadTo(743f, 840f, 720f, 840f)
                        lineTo(400f, 840f)
                        quadTo(367f, 840f, 343.5f, 816.5f)
                        quadTo(320f, 793f, 320f, 760f)
                        lineTo(320f, 353f)
                        quadTo(320f, 337f, 326.5f, 322.5f)
                        quadTo(333f, 308f, 344f, 297f)
                        lineTo(561f, 81f)
                        quadTo(576f, 67f, 596.5f, 64f)
                        quadTo(617f, 61f, 636f, 71f)
                        quadTo(655f, 81f, 663.5f, 99f)
                        quadTo(672f, 117f, 667f, 136f)
                        lineTo(622f, 320f)
                        lineTo(840f, 320f)
                        close()
                        moveTo(160f, 840f)
                        quadTo(127f, 840f, 103.5f, 816.5f)
                        quadTo(80f, 793f, 80f, 760f)
                        lineTo(80f, 400f)
                        quadTo(80f, 367f, 103.5f, 343.5f)
                        quadTo(127f, 320f, 160f, 320f)
                        lineTo(160f, 320f)
                        quadTo(193f, 320f, 216.5f, 343.5f)
                        quadTo(240f, 367f, 240f, 400f)
                        lineTo(240f, 760f)
                        quadTo(240f, 793f, 216.5f, 816.5f)
                        quadTo(193f, 840f, 160f, 840f)
                        lineTo(160f, 840f)
                        close()
                    }
                }
                .build()

        return _ThumbUpFilled!!
    }

@Suppress("ObjectPropertyName") private var _ThumbUpFilled: ImageVector? = null
