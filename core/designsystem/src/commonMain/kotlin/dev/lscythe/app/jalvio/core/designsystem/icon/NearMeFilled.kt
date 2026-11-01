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

val JalvioIcons.NearMeFilled: ImageVector
    get() {
        if (_NearMeFilled != null) {
            return _NearMeFilled!!
        }
        _NearMeFilled =
            ImageVector.Builder(
                    name = "NearMeFilled",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                )
                .apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(402f, 558f)
                        lineTo(143f, 453f)
                        quadTo(130f, 448f, 124f, 437.5f)
                        quadTo(118f, 427f, 118f, 416f)
                        quadTo(118f, 405f, 124.5f, 394.5f)
                        quadTo(131f, 384f, 144f, 379f)
                        lineTo(758f, 151f)
                        quadTo(770f, 146f, 781f, 149f)
                        quadTo(792f, 152f, 800f, 160f)
                        quadTo(808f, 168f, 811f, 179f)
                        quadTo(814f, 190f, 809f, 202f)
                        lineTo(581f, 816f)
                        quadTo(576f, 829f, 565.5f, 835.5f)
                        quadTo(555f, 842f, 544f, 842f)
                        quadTo(533f, 842f, 522.5f, 836f)
                        quadTo(512f, 830f, 507f, 817f)
                        lineTo(402f, 558f)
                        close()
                    }
                }
                .build()

        return _NearMeFilled!!
    }

@Suppress("ObjectPropertyName") private var _NearMeFilled: ImageVector? = null
