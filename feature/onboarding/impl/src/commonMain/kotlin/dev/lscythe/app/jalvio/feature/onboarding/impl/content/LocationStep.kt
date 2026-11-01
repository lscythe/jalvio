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
package dev.lscythe.app.jalvio.feature.onboarding.impl.content

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.MyLocationFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.NearMeFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.NotificationsActiveFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.RouteFilled
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_allow
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_not_now
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_note
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_reason_alerts
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_reason_map
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_reason_nearby
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_reason_rides
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_location_title
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.LocationPulse
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.ReasonChip
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepTopBar
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.cardSurface
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.stepTopBarColors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun LocationStep(onIntent: (OnboardingIntent) -> Unit) {
    val colors = MaterialTheme.colorScheme
    StepScaffold(
        containerColor = colors.surface,
        contentColor = colors.onSurface,
        decoration = { MapHeader() },
        topBar = {
            StepTopBar(
                filled = null,
                onBack = { onIntent(OnboardingIntent.Back) },
                colors = stepTopBarColors().copy(button = colors.cardSurface),
            )
        },
    ) {
        Spacer(Modifier.height(HeadlineOffset))
        SpringHeadline(
            stringResource(Res.string.onboarding_location_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up, 80),
            fontSize = 52.sp,
            delayMillis = 80,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            reasons().forEachIndexed { index, reason ->
                ReasonChip(
                    reason.icon,
                    stringResource(reason.label),
                    reason.container,
                    reason.content,
                    reason.shape,
                    Modifier.entrance(
                        EntranceKind.Grow,
                        260 + index * 70,
                        MaterialTheme.motionScheme.fastSpatialSpec(),
                    ),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        StepBody(
            stringResource(Res.string.onboarding_location_note),
            color = colors.onSurfaceVariant,
            fontSize = 13.sp,
        )
        Row(
            Modifier.fillMaxWidth().height(IntrinsicSize.Min).entrance(EntranceKind.Up, 420),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SquishButton(
                onClick = { onIntent(OnboardingIntent.AllowLocation) },
                corners = Corners.start(36.dp, 10.dp),
                pressedCorners = Corners.start(18.dp, 8.dp),
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                modifier = Modifier.weight(1f).fillMaxHeight(),
            ) {
                Icon(JalvioIcons.NearMeFilled, contentDescription = null)
                Text(
                    stringResource(Res.string.onboarding_location_allow),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            SquishButton(
                onClick = { onIntent(OnboardingIntent.Next) },
                corners = Corners.end(36.dp, 10.dp),
                pressedCorners = Corners.end(18.dp, 8.dp),
                containerColor = colors.secondaryContainer,
                contentColor = colors.onSecondaryContainer,
                pressedScale = 0.96f,
                modifier = Modifier.fillMaxHeight(),
            ) {
                Text(
                    stringResource(Res.string.onboarding_location_not_now),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun MapHeader() {
    val colors = MaterialTheme.colorScheme
    val grid = colors.onPrimaryContainer.copy(alpha = GridAlpha)
    val road = colors.surfaceContainerLowest
    Column(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
            .background(colors.primaryContainer)
            .entrance(EntranceKind.Fade)
            .clearAndSetSemantics {}
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Box(Modifier.fillMaxWidth().height(HeaderHeight)) {
            Canvas(Modifier.fillMaxSize()) {
                val cell = GridCell.toPx()
                var x = 0f
                while (x < size.width) {
                    drawLine(grid, Offset(x, 0f), Offset(x, size.height), 1.dp.toPx())
                    x += cell
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(grid, Offset(0f, y), Offset(size.width, y), 1.dp.toPx())
                    y += cell
                }
                rotate(-8f, Offset(size.width / 2, 127.dp.toPx())) {
                    drawRect(
                        road,
                        Offset(-20.dp.toPx(), 118.dp.toPx()),
                        Size(size.width + 40.dp.toPx(), 18.dp.toPx()),
                    )
                }
                rotate(14f, Offset(size.width * 0.72f, size.height / 2)) {
                    drawRect(
                        road,
                        Offset(size.width * 0.72f - 7.dp.toPx(), -20.dp.toPx()),
                        Size(14.dp.toPx(), size.height + 40.dp.toPx()),
                    )
                }
            }
            LocationPulse(Modifier.align(Alignment.TopCenter).offset(y = 48.dp))
        }
    }
}

private class Reason(
    val icon: ImageVector,
    val label: StringResource,
    val container: Color,
    val content: Color,
    val shape: RoundedCornerShape,
)

@Composable
private fun reasons(): List<Reason> {
    val colors = MaterialTheme.colorScheme
    return listOf(
        Reason(
            JalvioIcons.NearMeFilled,
            Res.string.onboarding_location_reason_nearby,
            colors.primary,
            colors.onPrimary,
            RoundedCornerShape(20.dp),
        ),
        Reason(
            JalvioIcons.MyLocationFilled,
            Res.string.onboarding_location_reason_map,
            colors.tertiaryContainer,
            colors.onTertiaryContainer,
            RoundedCornerShape(8.dp),
        ),
        Reason(
            JalvioIcons.NotificationsActiveFilled,
            Res.string.onboarding_location_reason_alerts,
            colors.secondaryContainer,
            colors.onSecondaryContainer,
            RoundedCornerShape(20.dp, 8.dp, 20.dp, 8.dp),
        ),
        Reason(
            JalvioIcons.RouteFilled,
            Res.string.onboarding_location_reason_rides,
            colors.tertiary,
            colors.onTertiary,
            RoundedCornerShape(20.dp),
        ),
    )
}

private val HeaderHeight = 218.dp
private val HeadlineOffset = 140.dp
private val GridCell = 30.dp
private const val GridAlpha = 0.1f
