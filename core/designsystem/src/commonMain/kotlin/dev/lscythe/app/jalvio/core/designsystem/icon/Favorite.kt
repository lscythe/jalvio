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

val JalvioIcons.Favorite: ImageVector
    get() {
        if (_Favorite != null) {
            return _Favorite!!
        }
        _Favorite =
            ImageVector.Builder(
                    name = "Favorite",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(451.5f, 808f)
                        quadTo(437f, 803f, 426f, 792f)
                        lineTo(357f, 729f)
                        quadTo(251f, 632f, 165.5f, 536.5f)
                        quadTo(80f, 441f, 80f, 326f)
                        quadTo(80f, 232f, 143f, 169f)
                        quadTo(206f, 106f, 300f, 106f)
                        quadTo(353f, 106f, 400f, 128.5f)
                        quadTo(447f, 151f, 480f, 190f)
                        quadTo(513f, 151f, 560f, 128.5f)
                        quadTo(607f, 106f, 660f, 106f)
                        quadTo(754f, 106f, 817f, 169f)
                        quadTo(880f, 232f, 880f, 326f)
                        quadTo(880f, 441f, 795f, 537f)
                        quadTo(710f, 633f, 602f, 730f)
                        lineTo(534f, 792f)
                        quadTo(523f, 803f, 508.5f, 808f)
                        quadTo(494f, 813f, 480f, 813f)
                        quadTo(466f, 813f, 451.5f, 808f)
                        close()
                        moveTo(442f, 270f)
                        quadTo(413f, 229f, 380f, 207.5f)
                        quadTo(347f, 186f, 300f, 186f)
                        quadTo(240f, 186f, 200f, 226f)
                        quadTo(160f, 266f, 160f, 326f)
                        quadTo(160f, 378f, 197f, 436.5f)
                        quadTo(234f, 495f, 285.5f, 550f)
                        quadTo(337f, 605f, 391.5f, 653f)
                        quadTo(446f, 701f, 480f, 732f)
                        quadTo(514f, 701f, 568.5f, 653f)
                        quadTo(623f, 605f, 674.5f, 550f)
                        quadTo(726f, 495f, 763f, 436.5f)
                        quadTo(800f, 378f, 800f, 326f)
                        quadTo(800f, 266f, 760f, 226f)
                        quadTo(720f, 186f, 660f, 186f)
                        quadTo(613f, 186f, 580f, 207.5f)
                        quadTo(547f, 229f, 518f, 270f)
                        quadTo(511f, 280f, 501f, 285f)
                        quadTo(491f, 290f, 480f, 290f)
                        quadTo(469f, 290f, 459f, 285f)
                        quadTo(449f, 280f, 442f, 270f)
                        close()
                        moveTo(480f, 459f)
                        close()
                    }
                }
                .build()

        return _Favorite!!
    }

@Suppress("ObjectPropertyName") private var _Favorite: ImageVector? = null
