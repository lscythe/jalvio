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
package dev.lscythe.app.jalvio.feature.onboarding.impl.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.icon.FaceFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.PersonFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.SentimentSatisfiedFilled

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun RiderAvatars(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val pop = MaterialTheme.motionScheme.fastSpatialSpec<Float>()
    Box(
        modifier.size(146.dp, 64.dp).clearAndSetSemantics {},
        contentAlignment = Alignment.CenterStart,
    ) {
        ShapeIcon(
            JalvioIcons.PersonFilled,
            MaterialShapes.Flower,
            colors.primary,
            colors.onPrimary,
            size = 64.dp,
            iconSize = 28.dp,
            modifier = Modifier.entrance(EntranceKind.Grow, 300, pop),
        )
        ShapeIcon(
            JalvioIcons.FaceFilled,
            MaterialShapes.Cookie9Sided,
            colors.tertiaryContainer,
            colors.onTertiaryContainer,
            size = 56.dp,
            iconSize = 24.dp,
            modifier = Modifier.offset(x = 52.dp).entrance(EntranceKind.Grow, 370, pop),
        )
        ShapeIcon(
            JalvioIcons.SentimentSatisfiedFilled,
            MaterialShapes.Clover4Leaf,
            colors.tertiary,
            colors.onTertiary,
            size = 48.dp,
            iconSize = 22.dp,
            modifier = Modifier.offset(x = 98.dp).entrance(EntranceKind.Grow, 440, pop),
        )
    }
}
