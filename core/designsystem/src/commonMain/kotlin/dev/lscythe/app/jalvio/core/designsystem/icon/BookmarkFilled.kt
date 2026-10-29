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

val JalvioIcons.BookmarkFilled: ImageVector
    get() {
        if (_BookmarkFilled != null) {
            return _BookmarkFilled!!
        }
        _BookmarkFilled =
            ImageVector.Builder(
                    name = "BookmarkFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 720f)
                        lineTo(312f, 792f)
                        quadTo(272f, 809f, 236f, 785.5f)
                        quadTo(200f, 762f, 200f, 719f)
                        lineTo(200f, 200f)
                        quadTo(200f, 167f, 223.5f, 143.5f)
                        quadTo(247f, 120f, 280f, 120f)
                        lineTo(680f, 120f)
                        quadTo(713f, 120f, 736.5f, 143.5f)
                        quadTo(760f, 167f, 760f, 200f)
                        lineTo(760f, 719f)
                        quadTo(760f, 762f, 724f, 785.5f)
                        quadTo(688f, 809f, 648f, 792f)
                        lineTo(480f, 720f)
                        close()
                    }
                }
                .build()

        return _BookmarkFilled!!
    }

@Suppress("ObjectPropertyName") private var _BookmarkFilled: ImageVector? = null
