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

val JalvioIcons.LocationOn: ImageVector
    get() {
        if (_LocationOn != null) {
            return _LocationOn!!
        }
        _LocationOn =
            ImageVector.Builder(
                    name = "LocationOn",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 774f)
                        quadTo(602f, 662f, 661f, 570.5f)
                        quadTo(720f, 479f, 720f, 408f)
                        quadTo(720f, 299f, 650.5f, 229.5f)
                        quadTo(581f, 160f, 480f, 160f)
                        quadTo(379f, 160f, 309.5f, 229.5f)
                        quadTo(240f, 299f, 240f, 408f)
                        quadTo(240f, 479f, 299f, 570.5f)
                        quadTo(358f, 662f, 480f, 774f)
                        close()
                        moveTo(480f, 853f)
                        quadTo(466f, 853f, 452f, 848f)
                        quadTo(438f, 843f, 427f, 833f)
                        quadTo(362f, 773f, 312f, 716f)
                        quadTo(262f, 659f, 228.5f, 605.5f)
                        quadTo(195f, 552f, 177.5f, 502.5f)
                        quadTo(160f, 453f, 160f, 408f)
                        quadTo(160f, 258f, 256.5f, 169f)
                        quadTo(353f, 80f, 480f, 80f)
                        quadTo(607f, 80f, 703.5f, 169f)
                        quadTo(800f, 258f, 800f, 408f)
                        quadTo(800f, 453f, 782.5f, 502.5f)
                        quadTo(765f, 552f, 731.5f, 605.5f)
                        quadTo(698f, 659f, 648f, 716f)
                        quadTo(598f, 773f, 533f, 833f)
                        quadTo(522f, 843f, 508f, 848f)
                        quadTo(494f, 853f, 480f, 853f)
                        close()
                        moveTo(480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        quadTo(480f, 400f, 480f, 400f)
                        close()
                        moveTo(480f, 480f)
                        quadTo(513f, 480f, 536.5f, 456.5f)
                        quadTo(560f, 433f, 560f, 400f)
                        quadTo(560f, 367f, 536.5f, 343.5f)
                        quadTo(513f, 320f, 480f, 320f)
                        quadTo(447f, 320f, 423.5f, 343.5f)
                        quadTo(400f, 367f, 400f, 400f)
                        quadTo(400f, 433f, 423.5f, 456.5f)
                        quadTo(447f, 480f, 480f, 480f)
                        close()
                    }
                }
                .build()

        return _LocationOn!!
    }

@Suppress("ObjectPropertyName") private var _LocationOn: ImageVector? = null
