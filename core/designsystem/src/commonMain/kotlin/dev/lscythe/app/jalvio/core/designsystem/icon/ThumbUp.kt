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

val JalvioIcons.ThumbUp: ImageVector
    get() {
        if (_ThumbUp != null) {
            return _ThumbUp!!
        }
        _ThumbUp =
            ImageVector.Builder(
                    name = "ThumbUp",
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
                        quadTo(920f, 487f, 918f, 495f)
                        quadTo(916f, 503f, 914f, 510f)
                        lineTo(794f, 792f)
                        quadTo(785f, 812f, 764f, 826f)
                        quadTo(743f, 840f, 720f, 840f)
                        lineTo(280f, 840f)
                        quadTo(280f, 840f, 280f, 840f)
                        quadTo(280f, 840f, 280f, 840f)
                        lineTo(280f, 320f)
                        quadTo(280f, 320f, 280f, 320f)
                        quadTo(280f, 320f, 280f, 320f)
                        lineTo(520f, 82f)
                        quadTo(535f, 67f, 555.5f, 64.5f)
                        quadTo(576f, 62f, 595f, 72f)
                        quadTo(614f, 82f, 623f, 100f)
                        quadTo(632f, 118f, 627f, 137f)
                        lineTo(582f, 320f)
                        lineTo(840f, 320f)
                        close()
                        moveTo(360f, 354f)
                        lineTo(360f, 760f)
                        quadTo(360f, 760f, 360f, 760f)
                        quadTo(360f, 760f, 360f, 760f)
                        lineTo(720f, 760f)
                        quadTo(720f, 760f, 720f, 760f)
                        quadTo(720f, 760f, 720f, 760f)
                        lineTo(840f, 480f)
                        lineTo(840f, 400f)
                        quadTo(840f, 400f, 840f, 400f)
                        quadTo(840f, 400f, 840f, 400f)
                        lineTo(480f, 400f)
                        lineTo(534f, 180f)
                        lineTo(360f, 354f)
                        close()
                        moveTo(160f, 840f)
                        quadTo(127f, 840f, 103.5f, 816.5f)
                        quadTo(80f, 793f, 80f, 760f)
                        lineTo(80f, 400f)
                        quadTo(80f, 367f, 103.5f, 343.5f)
                        quadTo(127f, 320f, 160f, 320f)
                        lineTo(280f, 320f)
                        quadTo(280f, 320f, 280f, 320f)
                        quadTo(280f, 320f, 280f, 320f)
                        lineTo(280f, 400f)
                        lineTo(160f, 400f)
                        quadTo(160f, 400f, 160f, 400f)
                        quadTo(160f, 400f, 160f, 400f)
                        lineTo(160f, 760f)
                        quadTo(160f, 760f, 160f, 760f)
                        quadTo(160f, 760f, 160f, 760f)
                        lineTo(280f, 760f)
                        lineTo(280f, 840f)
                        quadTo(280f, 840f, 280f, 840f)
                        quadTo(280f, 840f, 280f, 840f)
                        lineTo(160f, 840f)
                        close()
                        moveTo(360f, 760f)
                        lineTo(360f, 354f)
                        lineTo(360f, 354f)
                        lineTo(360f, 400f)
                        lineTo(360f, 400f)
                        quadTo(360f, 400f, 360f, 400f)
                        quadTo(360f, 400f, 360f, 400f)
                        lineTo(360f, 480f)
                        lineTo(360f, 760f)
                        quadTo(360f, 760f, 360f, 760f)
                        quadTo(360f, 760f, 360f, 760f)
                        lineTo(360f, 760f)
                        quadTo(360f, 760f, 360f, 760f)
                        quadTo(360f, 760f, 360f, 760f)
                        close()
                    }
                }
                .build()

        return _ThumbUp!!
    }

@Suppress("ObjectPropertyName") private var _ThumbUp: ImageVector? = null
