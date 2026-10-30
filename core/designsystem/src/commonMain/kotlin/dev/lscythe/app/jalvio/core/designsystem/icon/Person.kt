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

val JalvioIcons.Person: ImageVector
    get() {
        if (_Person != null) {
            return _Person!!
        }
        _Person =
            ImageVector.Builder(
                    name = "Person",
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
                        moveTo(240f, 720f)
                        lineTo(720f, 720f)
                        lineTo(720f, 688f)
                        quadTo(720f, 677f, 714.5f, 668f)
                        quadTo(709f, 659f, 700f, 654f)
                        quadTo(646f, 627f, 591f, 613.5f)
                        quadTo(536f, 600f, 480f, 600f)
                        quadTo(424f, 600f, 369f, 613.5f)
                        quadTo(314f, 627f, 260f, 654f)
                        quadTo(251f, 659f, 245.5f, 668f)
                        quadTo(240f, 677f, 240f, 688f)
                        lineTo(240f, 720f)
                        close()
                        moveTo(480f, 400f)
                        quadTo(513f, 400f, 536.5f, 376.5f)
                        quadTo(560f, 353f, 560f, 320f)
                        quadTo(560f, 287f, 536.5f, 263.5f)
                        quadTo(513f, 240f, 480f, 240f)
                        quadTo(447f, 240f, 423.5f, 263.5f)
                        quadTo(400f, 287f, 400f, 320f)
                        quadTo(400f, 353f, 423.5f, 376.5f)
                        quadTo(447f, 400f, 480f, 400f)
                        close()
                        moveTo(480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        quadTo(480f, 320f, 480f, 320f)
                        close()
                        moveTo(480f, 720f)
                        lineTo(480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        quadTo(480f, 720f, 480f, 720f)
                        lineTo(480f, 720f)
                        lineTo(480f, 720f)
                        close()
                    }
                }
                .build()

        return _Person!!
    }

@Suppress("ObjectPropertyName") private var _Person: ImageVector? = null
