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
package dev.lscythe.app.jalvio.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.theme.RouteTone
import dev.lscythe.app.jalvio.core.designsystem.theme.container
import dev.lscythe.app.jalvio.core.designsystem.theme.content

enum class RouteBadgeSize(val height: Dp, val corner: Dp, val fontSize: TextUnit) {
    Filter(48.dp, 14.dp, 24.sp),
    Row(40.dp, 12.dp, 22.sp),
    Compact(36.dp, 11.dp, 20.sp),
    OnColor(32.dp, 10.dp, 20.sp),
    Inline(24.dp, 8.dp, 13.sp),
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RouteBadge(
    label: String,
    tone: RouteTone,
    size: RouteBadgeSize,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val inverted = size == RouteBadgeSize.OnColor
    val background = if (inverted) tone.content(scheme) else tone.container(scheme)
    val textColor = if (inverted) tone.container(scheme) else tone.content(scheme)
    val inline = size == RouteBadgeSize.Inline
    val typography = MaterialTheme.typography
    val textStyle = if (inline) typography.titleLarge else typography.titleLargeEmphasized
    Box(
        modifier
            .heightIn(min = size.height)
            .widthIn(min = if (inline) InlineMinWidth else size.height)
            .background(background, RoundedCornerShape(size.corner))
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            label,
            color = textColor,
            maxLines = 1,
            softWrap = false,
            style = textStyle.copy(fontSize = size.fontSize, lineHeight = size.fontSize),
        )
    }
}

private val InlineMinWidth = 28.dp
