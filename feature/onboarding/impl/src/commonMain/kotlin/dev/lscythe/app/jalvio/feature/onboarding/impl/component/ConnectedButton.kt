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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConnectedButton(
    label: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    trailing: @Composable () -> Unit,
) {
    Row(
        modifier.fillMaxWidth().height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(Gap),
    ) {
        SquishButton(
            onClick = onClick,
            corners = Corners.start(Outer, Inner),
            pressedCorners = Corners.start(PressedOuter, PressedInner),
            containerColor = containerColor,
            contentColor = contentColor,
            height = Height,
            contentPadding = 28.dp,
            modifier = Modifier.weight(1f).fillMaxHeight(),
        ) {
            Text(
                label,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.weight(1f))
        }
        SquishButton(
            onClick = onClick,
            corners = Corners.end(Outer, Inner),
            pressedCorners = Corners.end(PressedOuter, PressedInner),
            containerColor = containerColor,
            contentColor = contentColor,
            height = Height,
            pressedScale = 0.94f,
            contentPadding = 0.dp,
            modifier = Modifier.width(Height).fillMaxHeight(),
        ) {
            trailing()
        }
    }
}

private val Height = 80.dp
private val Gap = 4.dp
private val Outer = 40.dp
private val Inner = 12.dp
private val PressedOuter = 20.dp
private val PressedInner = 8.dp
