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

val JalvioIcons.SentimentSatisfied: ImageVector
    get() {
        if (_SentimentSatisfied != null) {
            return _SentimentSatisfied!!
        }
        _SentimentSatisfied =
            ImageVector.Builder(
                    name = "SentimentSatisfied",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 700f)
                        quadTo(533f, 700f, 580.5f, 677f)
                        quadTo(628f, 654f, 657f, 610f)
                        quadTo(668f, 593f, 660f, 576.5f)
                        quadTo(652f, 560f, 634f, 560f)
                        quadTo(626f, 560f, 619.5f, 563.5f)
                        quadTo(613f, 567f, 609f, 574f)
                        quadTo(586f, 605f, 552f, 622.5f)
                        quadTo(518f, 640f, 480f, 640f)
                        quadTo(442f, 640f, 408f, 622.5f)
                        quadTo(374f, 605f, 351f, 574f)
                        quadTo(346f, 567f, 339.5f, 563.5f)
                        quadTo(333f, 560f, 325f, 560f)
                        quadTo(307f, 560f, 299f, 576f)
                        quadTo(291f, 592f, 302f, 608f)
                        quadTo(331f, 653f, 378.5f, 676.5f)
                        quadTo(426f, 700f, 480f, 700f)
                        close()
                        moveTo(620f, 440f)
                        quadTo(645f, 440f, 662.5f, 422.5f)
                        quadTo(680f, 405f, 680f, 380f)
                        quadTo(680f, 355f, 662.5f, 337.5f)
                        quadTo(645f, 320f, 620f, 320f)
                        quadTo(595f, 320f, 577.5f, 337.5f)
                        quadTo(560f, 355f, 560f, 380f)
                        quadTo(560f, 405f, 577.5f, 422.5f)
                        quadTo(595f, 440f, 620f, 440f)
                        close()
                        moveTo(340f, 440f)
                        quadTo(365f, 440f, 382.5f, 422.5f)
                        quadTo(400f, 405f, 400f, 380f)
                        quadTo(400f, 355f, 382.5f, 337.5f)
                        quadTo(365f, 320f, 340f, 320f)
                        quadTo(315f, 320f, 297.5f, 337.5f)
                        quadTo(280f, 355f, 280f, 380f)
                        quadTo(280f, 405f, 297.5f, 422.5f)
                        quadTo(315f, 440f, 340f, 440f)
                        close()
                        moveTo(324f, 848.5f)
                        quadTo(251f, 817f, 197f, 763f)
                        quadTo(143f, 709f, 111.5f, 636f)
                        quadTo(80f, 563f, 80f, 480f)
                        quadTo(80f, 397f, 111.5f, 324f)
                        quadTo(143f, 251f, 197f, 197f)
                        quadTo(251f, 143f, 324f, 111.5f)
                        quadTo(397f, 80f, 480f, 80f)
                        quadTo(563f, 80f, 636f, 111.5f)
                        quadTo(709f, 143f, 763f, 197f)
                        quadTo(817f, 251f, 848.5f, 324f)
                        quadTo(880f, 397f, 880f, 480f)
                        quadTo(880f, 563f, 848.5f, 636f)
                        quadTo(817f, 709f, 763f, 763f)
                        quadTo(709f, 817f, 636f, 848.5f)
                        quadTo(563f, 880f, 480f, 880f)
                        quadTo(397f, 880f, 324f, 848.5f)
                        close()
                        moveTo(480f, 480f)
                        close()
                        moveTo(707f, 707f)
                        quadTo(800f, 614f, 800f, 480f)
                        quadTo(800f, 346f, 707f, 253f)
                        quadTo(614f, 160f, 480f, 160f)
                        quadTo(346f, 160f, 253f, 253f)
                        quadTo(160f, 346f, 160f, 480f)
                        quadTo(160f, 614f, 253f, 707f)
                        quadTo(346f, 800f, 480f, 800f)
                        quadTo(614f, 800f, 707f, 707f)
                        close()
                    }
                }
                .build()

        return _SentimentSatisfied!!
    }

@Suppress("ObjectPropertyName") private var _SentimentSatisfied: ImageVector? = null
