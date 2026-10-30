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
package dev.lscythe.app.jalvio.core.designsystem.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.component.CrowdChip
import dev.lscythe.app.jalvio.core.designsystem.component.CrowdDots
import dev.lscythe.app.jalvio.core.designsystem.component.CrowdLevel
import dev.lscythe.app.jalvio.core.designsystem.component.LiveDot
import dev.lscythe.app.jalvio.core.designsystem.component.RouteBadge
import dev.lscythe.app.jalvio.core.designsystem.component.RouteBadgeSize
import dev.lscythe.app.jalvio.core.designsystem.component.toast.JalvioToast
import dev.lscythe.app.jalvio.core.designsystem.component.toast.ToastData
import dev.lscythe.app.jalvio.core.designsystem.component.toast.ToastDuration
import dev.lscythe.app.jalvio.core.designsystem.component.toast.ToastTone
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.NotificationsFilled
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioTheme
import dev.lscythe.app.jalvio.core.designsystem.theme.RouteTone

@JalvioPreviews
@Composable
private fun RouteBadgePreview() = PreviewSurface {
    RouteBadgeSize.entries.forEach { RouteBadge("13E", RouteTone.Primary, it) }
}

@JalvioPreviews
@Composable
private fun CrowdChipPreview() = PreviewSurface { CrowdLevel.entries.forEach { CrowdChip(it) } }

@JalvioPreviews
@Composable
private fun CrowdDotsPreview() = PreviewSurface {
    (CrowdLevel.entries + null).forEach { CrowdDots(it, showLabel = true) }
}

@JalvioPreviews
@Composable
private fun LiveDotPreview() = PreviewSurface { LiveDot(label = "Live") }

@JalvioPreviews
@Composable
private fun ToastPreview() = PreviewSurface {
    JalvioToast(
        ToastData(
            "Bus 13E is arriving",
            ToastTone.Info,
            JalvioIcons.NotificationsFilled,
            action = null,
            duration = ToastDuration.Default,
        )
    )
}

@Composable
private fun PreviewSurface(content: @Composable () -> Unit) = JalvioTheme {
    Surface {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            content()
        }
    }
}
