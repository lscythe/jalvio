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

val JalvioIcons.Campaign: ImageVector
    get() {
        if (_Campaign != null) {
            return _Campaign!!
        }
        _Campaign =
            ImageVector.Builder(
                    name = "Campaign",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(840f, 520f)
                        lineTo(760f, 520f)
                        quadTo(743f, 520f, 731.5f, 508.5f)
                        quadTo(720f, 497f, 720f, 480f)
                        quadTo(720f, 463f, 731.5f, 451.5f)
                        quadTo(743f, 440f, 760f, 440f)
                        lineTo(840f, 440f)
                        quadTo(857f, 440f, 868.5f, 451.5f)
                        quadTo(880f, 463f, 880f, 480f)
                        quadTo(880f, 497f, 868.5f, 508.5f)
                        quadTo(857f, 520f, 840f, 520f)
                        close()
                        moveTo(664f, 672f)
                        quadTo(674f, 658f, 690f, 656f)
                        quadTo(706f, 654f, 720f, 664f)
                        lineTo(784f, 712f)
                        quadTo(798f, 722f, 800f, 738f)
                        quadTo(802f, 754f, 792f, 768f)
                        quadTo(782f, 782f, 766f, 784f)
                        quadTo(750f, 786f, 736f, 776f)
                        lineTo(672f, 728f)
                        quadTo(658f, 718f, 656f, 702f)
                        quadTo(654f, 686f, 664f, 672f)
                        close()
                        moveTo(784f, 248f)
                        lineTo(720f, 296f)
                        quadTo(706f, 306f, 690f, 304f)
                        quadTo(674f, 302f, 664f, 288f)
                        quadTo(654f, 274f, 656f, 258f)
                        quadTo(658f, 242f, 672f, 232f)
                        lineTo(736f, 184f)
                        quadTo(750f, 174f, 766f, 176f)
                        quadTo(782f, 178f, 792f, 192f)
                        quadTo(802f, 206f, 800f, 222f)
                        quadTo(798f, 238f, 784f, 248f)
                        close()
                        moveTo(200f, 600f)
                        lineTo(160f, 600f)
                        quadTo(127f, 600f, 103.5f, 576.5f)
                        quadTo(80f, 553f, 80f, 520f)
                        lineTo(80f, 440f)
                        quadTo(80f, 407f, 103.5f, 383.5f)
                        quadTo(127f, 360f, 160f, 360f)
                        lineTo(320f, 360f)
                        lineTo(459f, 276f)
                        quadTo(479f, 264f, 499.5f, 276f)
                        quadTo(520f, 288f, 520f, 311f)
                        lineTo(520f, 649f)
                        quadTo(520f, 672f, 499.5f, 684f)
                        quadTo(479f, 696f, 459f, 684f)
                        lineTo(320f, 600f)
                        lineTo(280f, 600f)
                        lineTo(280f, 720f)
                        quadTo(280f, 737f, 268.5f, 748.5f)
                        quadTo(257f, 760f, 240f, 760f)
                        quadTo(223f, 760f, 211.5f, 748.5f)
                        quadTo(200f, 737f, 200f, 720f)
                        lineTo(200f, 600f)
                        close()
                        moveTo(440f, 578f)
                        lineTo(440f, 382f)
                        lineTo(342f, 440f)
                        lineTo(160f, 440f)
                        quadTo(160f, 440f, 160f, 440f)
                        quadTo(160f, 440f, 160f, 440f)
                        lineTo(160f, 520f)
                        quadTo(160f, 520f, 160f, 520f)
                        quadTo(160f, 520f, 160f, 520f)
                        lineTo(342f, 520f)
                        lineTo(440f, 578f)
                        close()
                        moveTo(560f, 614f)
                        lineTo(560f, 346f)
                        quadTo(587f, 370f, 603.5f, 404.5f)
                        quadTo(620f, 439f, 620f, 480f)
                        quadTo(620f, 521f, 603.5f, 555.5f)
                        quadTo(587f, 590f, 560f, 614f)
                        close()
                        moveTo(300f, 480f)
                        lineTo(300f, 480f)
                        lineTo(300f, 480f)
                        quadTo(300f, 480f, 300f, 480f)
                        quadTo(300f, 480f, 300f, 480f)
                        lineTo(300f, 480f)
                        quadTo(300f, 480f, 300f, 480f)
                        quadTo(300f, 480f, 300f, 480f)
                        lineTo(300f, 480f)
                        lineTo(300f, 480f)
                        lineTo(300f, 480f)
                        close()
                    }
                }
                .build()

        return _Campaign!!
    }

@Suppress("ObjectPropertyName") private var _Campaign: ImageVector? = null
