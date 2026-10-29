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

val JalvioIcons.Flag: ImageVector
    get() {
        if (_Flag != null) {
            return _Flag!!
        }
        _Flag =
            ImageVector.Builder(
                    name = "Flag",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                    autoMirror = true,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(280f, 560f)
                        lineTo(280f, 800f)
                        quadTo(280f, 817f, 268.5f, 828.5f)
                        quadTo(257f, 840f, 240f, 840f)
                        quadTo(223f, 840f, 211.5f, 828.5f)
                        quadTo(200f, 817f, 200f, 800f)
                        lineTo(200f, 200f)
                        quadTo(200f, 183f, 211.5f, 171.5f)
                        quadTo(223f, 160f, 240f, 160f)
                        lineTo(527f, 160f)
                        quadTo(541f, 160f, 552f, 169f)
                        quadTo(563f, 178f, 566f, 192f)
                        lineTo(576f, 240f)
                        lineTo(760f, 240f)
                        quadTo(777f, 240f, 788.5f, 251.5f)
                        quadTo(800f, 263f, 800f, 280f)
                        lineTo(800f, 600f)
                        quadTo(800f, 617f, 788.5f, 628.5f)
                        quadTo(777f, 640f, 760f, 640f)
                        lineTo(553f, 640f)
                        quadTo(539f, 640f, 528f, 631f)
                        quadTo(517f, 622f, 514f, 608f)
                        lineTo(504f, 560f)
                        lineTo(280f, 560f)
                        close()
                        moveTo(586f, 560f)
                        lineTo(720f, 560f)
                        lineTo(720f, 320f)
                        lineTo(543f, 320f)
                        quadTo(529f, 320f, 518f, 311f)
                        quadTo(507f, 302f, 504f, 288f)
                        lineTo(494f, 240f)
                        lineTo(280f, 240f)
                        lineTo(280f, 480f)
                        lineTo(537f, 480f)
                        quadTo(551f, 480f, 562f, 489f)
                        quadTo(573f, 498f, 576f, 512f)
                        lineTo(586f, 560f)
                        close()
                        moveTo(500f, 400f)
                        lineTo(500f, 400f)
                        quadTo(500f, 400f, 500f, 400f)
                        quadTo(500f, 400f, 500f, 400f)
                        lineTo(500f, 400f)
                        lineTo(500f, 400f)
                        lineTo(500f, 400f)
                        lineTo(500f, 400f)
                        quadTo(500f, 400f, 500f, 400f)
                        quadTo(500f, 400f, 500f, 400f)
                        lineTo(500f, 400f)
                        lineTo(500f, 400f)
                        lineTo(500f, 400f)
                        close()
                    }
                }
                .build()

        return _Flag!!
    }

@Suppress("ObjectPropertyName") private var _Flag: ImageVector? = null
