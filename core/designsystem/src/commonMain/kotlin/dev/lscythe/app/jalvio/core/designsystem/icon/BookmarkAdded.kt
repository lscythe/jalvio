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

val JalvioIcons.BookmarkAdded: ImageVector
    get() {
        if (_BookmarkAdded != null) {
            return _BookmarkAdded!!
        }
        _BookmarkAdded =
            ImageVector.Builder(
                    name = "BookmarkAdded",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(713f, 247f)
                        lineTo(826f, 134f)
                        quadTo(838f, 122f, 854.5f, 122f)
                        quadTo(871f, 122f, 883f, 134f)
                        quadTo(895f, 146f, 895f, 162.5f)
                        quadTo(895f, 179f, 883f, 191f)
                        lineTo(741f, 332f)
                        quadTo(729f, 344f, 713f, 344f)
                        quadTo(697f, 344f, 685f, 332f)
                        lineTo(628f, 275f)
                        quadTo(617f, 264f, 617f, 247.5f)
                        quadTo(617f, 231f, 628f, 219f)
                        quadTo(640f, 207f, 656.5f, 207f)
                        quadTo(673f, 207f, 685f, 219f)
                        lineTo(713f, 247f)
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

        return _BookmarkAdded!!
    }

@Suppress("ObjectPropertyName") private var _BookmarkAdded: ImageVector? = null
