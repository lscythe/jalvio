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

val JalvioIcons.BedtimeFilled: ImageVector
    get() {
        if (_BedtimeFilled != null) {
            return _BedtimeFilled!!
        }
        _BedtimeFilled =
            ImageVector.Builder(
                    name = "BedtimeFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(524f, 920f)
                        quadTo(440f, 920f, 366.5f, 888f)
                        quadTo(293f, 856f, 238.5f, 801.5f)
                        quadTo(184f, 747f, 152f, 673.5f)
                        quadTo(120f, 600f, 120f, 516f)
                        quadTo(120f, 388f, 192f, 284f)
                        quadTo(264f, 180f, 385f, 138f)
                        quadTo(407f, 130f, 426f, 143.5f)
                        quadTo(445f, 157f, 444f, 180f)
                        quadTo(441f, 265f, 471f, 342f)
                        quadTo(501f, 419f, 561f, 479f)
                        quadTo(621f, 539f, 698f, 569f)
                        quadTo(775f, 599f, 860f, 596f)
                        quadTo(886f, 595f, 898.5f, 613.5f)
                        quadTo(911f, 632f, 903f, 655f)
                        quadTo(859f, 775f, 755.5f, 847.5f)
                        quadTo(652f, 920f, 524f, 920f)
                        close()
                    }
                }
                .build()

        return _BedtimeFilled!!
    }

@Suppress("ObjectPropertyName") private var _BedtimeFilled: ImageVector? = null
