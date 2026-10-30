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

val JalvioIcons.PersonFilled: ImageVector
    get() {
        if (_PersonFilled != null) {
            return _PersonFilled!!
        }
        _PersonFilled =
            ImageVector.Builder(
                    name = "PersonFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 480f)
                        quadTo(414f, 480f, 367f, 433f)
                        quadTo(320f, 386f, 320f, 320f)
                        quadTo(320f, 254f, 367f, 207f)
                        quadTo(414f, 160f, 480f, 160f)
                        quadTo(546f, 160f, 593f, 207f)
                        quadTo(640f, 254f, 640f, 320f)
                        quadTo(640f, 386f, 593f, 433f)
                        quadTo(546f, 480f, 480f, 480f)
                        close()
                        moveTo(160f, 720f)
                        lineTo(160f, 688f)
                        quadTo(160f, 654f, 177.5f, 625.5f)
                        quadTo(195f, 597f, 224f, 582f)
                        quadTo(286f, 551f, 350f, 535.5f)
                        quadTo(414f, 520f, 480f, 520f)
                        quadTo(546f, 520f, 610f, 535.5f)
                        quadTo(674f, 551f, 736f, 582f)
                        quadTo(765f, 597f, 782.5f, 625.5f)
                        quadTo(800f, 654f, 800f, 688f)
                        lineTo(800f, 720f)
                        quadTo(800f, 753f, 776.5f, 776.5f)
                        quadTo(753f, 800f, 720f, 800f)
                        lineTo(240f, 800f)
                        quadTo(207f, 800f, 183.5f, 776.5f)
                        quadTo(160f, 753f, 160f, 720f)
                        close()
                    }
                }
                .build()

        return _PersonFilled!!
    }

@Suppress("ObjectPropertyName") private var _PersonFilled: ImageVector? = null
