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

val JalvioIcons.ChatBubble: ImageVector
    get() {
        if (_ChatBubble != null) {
            return _ChatBubble!!
        }
        _ChatBubble =
            ImageVector.Builder(
                    name = "ChatBubble",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(240f, 720f)
                        lineTo(148f, 812f)
                        quadTo(129f, 831f, 104.5f, 820.5f)
                        quadTo(80f, 810f, 80f, 783f)
                        lineTo(80f, 160f)
                        quadTo(80f, 127f, 103.5f, 103.5f)
                        quadTo(127f, 80f, 160f, 80f)
                        lineTo(800f, 80f)
                        quadTo(833f, 80f, 856.5f, 103.5f)
                        quadTo(880f, 127f, 880f, 160f)
                        lineTo(880f, 640f)
                        quadTo(880f, 673f, 856.5f, 696.5f)
                        quadTo(833f, 720f, 800f, 720f)
                        lineTo(240f, 720f)
                        close()
                        moveTo(206f, 640f)
                        lineTo(800f, 640f)
                        quadTo(800f, 640f, 800f, 640f)
                        quadTo(800f, 640f, 800f, 640f)
                        lineTo(800f, 160f)
                        quadTo(800f, 160f, 800f, 160f)
                        quadTo(800f, 160f, 800f, 160f)
                        lineTo(160f, 160f)
                        quadTo(160f, 160f, 160f, 160f)
                        quadTo(160f, 160f, 160f, 160f)
                        lineTo(160f, 685f)
                        lineTo(206f, 640f)
                        close()
                        moveTo(160f, 640f)
                        lineTo(160f, 640f)
                        lineTo(160f, 160f)
                        quadTo(160f, 160f, 160f, 160f)
                        quadTo(160f, 160f, 160f, 160f)
                        lineTo(160f, 160f)
                        quadTo(160f, 160f, 160f, 160f)
                        quadTo(160f, 160f, 160f, 160f)
                        lineTo(160f, 640f)
                        quadTo(160f, 640f, 160f, 640f)
                        quadTo(160f, 640f, 160f, 640f)
                        lineTo(160f, 640f)
                        close()
                    }
                }
                .build()

        return _ChatBubble!!
    }

@Suppress("ObjectPropertyName") private var _ChatBubble: ImageVector? = null
