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

val JalvioIcons.LockFilled: ImageVector
    get() {
        if (_LockFilled != null) {
            return _LockFilled!!
        }
        _LockFilled =
            ImageVector.Builder(
                    name = "LockFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(240f, 880f)
                        quadTo(207f, 880f, 183.5f, 856.5f)
                        quadTo(160f, 833f, 160f, 800f)
                        lineTo(160f, 400f)
                        quadTo(160f, 367f, 183.5f, 343.5f)
                        quadTo(207f, 320f, 240f, 320f)
                        lineTo(280f, 320f)
                        lineTo(280f, 240f)
                        quadTo(280f, 157f, 338.5f, 98.5f)
                        quadTo(397f, 40f, 480f, 40f)
                        quadTo(563f, 40f, 621.5f, 98.5f)
                        quadTo(680f, 157f, 680f, 240f)
                        lineTo(680f, 320f)
                        lineTo(720f, 320f)
                        quadTo(753f, 320f, 776.5f, 343.5f)
                        quadTo(800f, 367f, 800f, 400f)
                        lineTo(800f, 800f)
                        quadTo(800f, 833f, 776.5f, 856.5f)
                        quadTo(753f, 880f, 720f, 880f)
                        lineTo(240f, 880f)
                        close()
                        moveTo(536.5f, 656.5f)
                        quadTo(560f, 633f, 560f, 600f)
                        quadTo(560f, 567f, 536.5f, 543.5f)
                        quadTo(513f, 520f, 480f, 520f)
                        quadTo(447f, 520f, 423.5f, 543.5f)
                        quadTo(400f, 567f, 400f, 600f)
                        quadTo(400f, 633f, 423.5f, 656.5f)
                        quadTo(447f, 680f, 480f, 680f)
                        quadTo(513f, 680f, 536.5f, 656.5f)
                        close()
                        moveTo(360f, 320f)
                        lineTo(600f, 320f)
                        lineTo(600f, 240f)
                        quadTo(600f, 190f, 565f, 155f)
                        quadTo(530f, 120f, 480f, 120f)
                        quadTo(430f, 120f, 395f, 155f)
                        quadTo(360f, 190f, 360f, 240f)
                        lineTo(360f, 320f)
                        close()
                    }
                }
                .build()

        return _LockFilled!!
    }

@Suppress("ObjectPropertyName") private var _LockFilled: ImageVector? = null
