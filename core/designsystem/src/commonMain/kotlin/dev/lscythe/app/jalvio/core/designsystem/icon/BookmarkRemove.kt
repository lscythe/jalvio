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

val JalvioIcons.BookmarkRemove: ImageVector
    get() {
        if (_BookmarkRemove != null) {
            return _BookmarkRemove!!
        }
        _BookmarkRemove =
            ImageVector.Builder(
                    name = "BookmarkRemove",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(640f, 280f)
                        quadTo(623f, 280f, 611.5f, 268.5f)
                        quadTo(600f, 257f, 600f, 240f)
                        quadTo(600f, 223f, 611.5f, 211.5f)
                        quadTo(623f, 200f, 640f, 200f)
                        lineTo(800f, 200f)
                        quadTo(817f, 200f, 828.5f, 211.5f)
                        quadTo(840f, 223f, 840f, 240f)
                        quadTo(840f, 257f, 828.5f, 268.5f)
                        quadTo(817f, 280f, 800f, 280f)
                        lineTo(640f, 280f)
                        close()
                        moveTo(480f, 720f)
                        lineTo(312f, 792f)
                        quadTo(272f, 809f, 236f, 785.5f)
                        quadTo(200f, 762f, 200f, 719f)
                        lineTo(200f, 200f)
                        quadTo(200f, 167f, 223.5f, 143.5f)
                        quadTo(247f, 120f, 280f, 120f)
                        lineTo(480f, 120f)
                        quadTo(497f, 120f, 508.5f, 131.5f)
                        quadTo(520f, 143f, 520f, 160f)
                        quadTo(520f, 177f, 508.5f, 188.5f)
                        quadTo(497f, 200f, 480f, 200f)
                        lineTo(280f, 200f)
                        quadTo(280f, 200f, 280f, 200f)
                        quadTo(280f, 200f, 280f, 200f)
                        lineTo(280f, 718f)
                        quadTo(280f, 718f, 280f, 718f)
                        quadTo(280f, 718f, 280f, 718f)
                        lineTo(480f, 632f)
                        lineTo(680f, 718f)
                        quadTo(680f, 718f, 680f, 718f)
                        quadTo(680f, 718f, 680f, 718f)
                        lineTo(680f, 480f)
                        quadTo(680f, 463f, 691.5f, 451.5f)
                        quadTo(703f, 440f, 720f, 440f)
                        quadTo(737f, 440f, 748.5f, 451.5f)
                        quadTo(760f, 463f, 760f, 480f)
                        lineTo(760f, 719f)
                        quadTo(760f, 762f, 724f, 785.5f)
                        quadTo(688f, 809f, 648f, 792f)
                        lineTo(480f, 720f)
                        close()
                        moveTo(480f, 200f)
                        lineTo(280f, 200f)
                        quadTo(280f, 200f, 280f, 200f)
                        quadTo(280f, 200f, 280f, 200f)
                        lineTo(280f, 200f)
                        quadTo(280f, 200f, 280f, 200f)
                        quadTo(280f, 200f, 280f, 200f)
                        lineTo(520f, 200f)
                        quadTo(520f, 200f, 520f, 200f)
                        quadTo(520f, 200f, 520f, 200f)
                        quadTo(520f, 200f, 520f, 200f)
                        quadTo(520f, 200f, 520f, 200f)
                        lineTo(520f, 200f)
                        quadTo(520f, 200f, 520f, 200f)
                        quadTo(520f, 200f, 520f, 200f)
                        lineTo(480f, 200f)
                        close()
                    }
                }
                .build()

        return _BookmarkRemove!!
    }

@Suppress("ObjectPropertyName") private var _BookmarkRemove: ImageVector? = null
