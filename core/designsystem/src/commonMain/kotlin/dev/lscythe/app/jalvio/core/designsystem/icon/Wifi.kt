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

val JalvioIcons.Wifi: ImageVector
    get() {
        if (_Wifi != null) {
            return _Wifi!!
        }
        _Wifi =
            ImageVector.Builder(
                    name = "Wifi",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(409f, 811f)
                        quadTo(380f, 782f, 380f, 740f)
                        quadTo(380f, 698f, 409f, 669f)
                        quadTo(438f, 640f, 480f, 640f)
                        quadTo(522f, 640f, 551f, 669f)
                        quadTo(580f, 698f, 580f, 740f)
                        quadTo(580f, 782f, 551f, 811f)
                        quadTo(522f, 840f, 480f, 840f)
                        quadTo(438f, 840f, 409f, 811f)
                        close()
                        moveTo(622.5f, 424f)
                        quadTo(690f, 448f, 745f, 490f)
                        quadTo(765f, 505f, 765.5f, 529.5f)
                        quadTo(766f, 554f, 748f, 572f)
                        quadTo(731f, 589f, 706f, 589.5f)
                        quadTo(681f, 590f, 661f, 576f)
                        quadTo(623f, 550f, 577f, 535f)
                        quadTo(531f, 520f, 480f, 520f)
                        quadTo(429f, 520f, 383f, 535f)
                        quadTo(337f, 550f, 299f, 576f)
                        quadTo(279f, 590f, 254f, 589f)
                        quadTo(229f, 588f, 212f, 571f)
                        quadTo(195f, 553f, 195f, 528.5f)
                        quadTo(195f, 504f, 215f, 489f)
                        quadTo(270f, 447f, 337.5f, 423.5f)
                        quadTo(405f, 400f, 480f, 400f)
                        quadTo(555f, 400f, 622.5f, 424f)
                        close()
                        moveTo(715.5f, 201f)
                        quadTo(826f, 242f, 914f, 317f)
                        quadTo(934f, 334f, 935f, 359f)
                        quadTo(936f, 384f, 918f, 402f)
                        quadTo(901f, 419f, 876f, 419.5f)
                        quadTo(851f, 420f, 831f, 404f)
                        quadTo(759f, 345f, 669.5f, 312.5f)
                        quadTo(580f, 280f, 480f, 280f)
                        quadTo(380f, 280f, 290.5f, 312.5f)
                        quadTo(201f, 345f, 129f, 404f)
                        quadTo(109f, 420f, 84f, 419.5f)
                        quadTo(59f, 419f, 42f, 402f)
                        quadTo(24f, 384f, 25f, 359f)
                        quadTo(26f, 334f, 46f, 317f)
                        quadTo(134f, 242f, 244.5f, 201f)
                        quadTo(355f, 160f, 480f, 160f)
                        quadTo(605f, 160f, 715.5f, 201f)
                        close()
                    }
                }
                .build()

        return _Wifi!!
    }

@Suppress("ObjectPropertyName") private var _Wifi: ImageVector? = null
