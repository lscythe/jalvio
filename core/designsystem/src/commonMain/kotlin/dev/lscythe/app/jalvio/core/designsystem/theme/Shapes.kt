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
package dev.lscythe.app.jalvio.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
val JalvioMaterialShapes =
    Shapes(
        extraSmall = RoundedCornerShape(6.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(16.dp),
        largeIncreased = RoundedCornerShape(20.dp),
        extraLarge = RoundedCornerShape(28.dp),
        extraLargeIncreased = RoundedCornerShape(32.dp),
        extraExtraLarge = RoundedCornerShape(36.dp),
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
object JalvioShapes {
    val InnerCorner = 6.dp
    val card = RoundedCornerShape(24.dp)

    val selectedStop: RoundedPolygon = MaterialShapes.Cookie9Sided
    val openDetail: RoundedPolygon = MaterialShapes.Cookie9Sided
    val toast: RoundedPolygon = MaterialShapes.Flower
    val destination: RoundedPolygon = MaterialShapes.Flower
    val unavailable: RoundedPolygon = MaterialShapes.Clover4Leaf
    val celebration: RoundedPolygon = MaterialShapes.Sunny
    val arrival: RoundedPolygon = MaterialShapes.Burst

    fun groupItem(index: Int, count: Int, outer: Dp = 20.dp): RoundedCornerShape {
        require(count > 0 && index in 0 until count) { "index $index outside a group of $count" }
        val top = if (index == 0) outer else InnerCorner
        val bottom = if (index == count - 1) outer else InnerCorner
        return RoundedCornerShape(top, top, bottom, bottom)
    }
}
