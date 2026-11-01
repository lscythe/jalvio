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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.icon.ArrowForward
import dev.lscythe.app.jalvio.core.designsystem.icon.BusFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.FavoriteFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.GroupsFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.theme.LocalReduceMotion
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_continue
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_independent_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_independent_chip
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_independent_title
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.PageDots
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.ShapeIcon
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.cyclingBackground
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberOnboardingTertiaryScheme
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberShapeCycle
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberSpin
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun IndependentStep(onIntent: (OnboardingIntent) -> Unit) {
    val tertiary = rememberOnboardingTertiaryScheme()
    StepScaffold(
        containerColor = tertiary.container,
        contentColor = tertiary.onContainer,
    ) {
        Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
            IndependentIllustration(
                chipBackground = tertiary.chipBackground,
                chipContent = tertiary.chipContent,
                chipIconContainer = tertiary.chipIconContainer,
                chipIconContent = tertiary.chipIconContent,
            )
        }
        SpringHeadline(
            stringResource(Res.string.onboarding_independent_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up, 200),
            delayMillis = 200,
        )
        StepBody(
            stringResource(Res.string.onboarding_independent_body),
            Modifier.entrance(EntranceKind.Fade, 320),
        )
        Row(
            Modifier.fillMaxWidth().height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PageDots(
                current = 2,
                activeColor = tertiary.activeDot,
                inactiveColor = tertiary.inactiveDot,
            )
            ContinuePill(
                containerColor = tertiary.button,
                contentColor = tertiary.onButton,
                onClick = { onIntent(OnboardingIntent.Next) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun IndependentIllustration(
    chipBackground: Color,
    chipContent: Color,
    chipIconContainer: Color,
    chipIconContent: Color,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val badges = remember {
        listOf(MaterialShapes.Cookie9Sided, MaterialShapes.Flower, MaterialShapes.Clover4Leaf)
    }
    val cycle = rememberShapeCycle(badges, stepMillis = BadgeCycleMillis / badges.size)
    val spin = rememberSpin(FlowerSpinMillis, clockwise = false)
    Box(modifier.fillMaxWidth().height(260.dp).clearAndSetSemantics {}) {
        Box(
            Modifier.offset(x = (-10).dp, y = 0.dp)
                .size(190.dp)
                .entrance(EntranceKind.Grow, 60)
                .cyclingBackground(cycle, colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Icon(JalvioIcons.BusFilled, null, Modifier.size(80.dp), tint = colors.onPrimary)
        }
        ShapeIcon(
            JalvioIcons.GroupsFilled,
            MaterialShapes.Flower,
            colors.tertiary,
            colors.onTertiary,
            size = 150.dp,
            iconSize = 60.dp,
            modifier =
                Modifier.align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = 100.dp)
                    .entrance(EntranceKind.Grow, 160),
            rotation = spin,
        )
        Row(
            Modifier.offset(x = 40.dp, y = 196.dp)
                .entrance(EntranceKind.Grow, 320, MaterialTheme.motionScheme.fastSpatialSpec())
                .height(56.dp)
                .background(chipBackground, RoundedCornerShape(28.dp))
                .padding(start = 8.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShapeIcon(
                JalvioIcons.FavoriteFilled,
                MaterialShapes.Clover4Leaf,
                chipIconContainer,
                chipIconContent,
                size = 40.dp,
                iconSize = 22.dp,
            )
            Text(
                stringResource(Res.string.onboarding_independent_chip),
                color = chipContent,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun ContinuePill(
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
) {
    val reduceMotion = LocalReduceMotion.current
    var expanded by remember { mutableStateOf(reduceMotion) }
    LaunchedEffect(Unit) {
        delay(PillExpandDelayMillis.milliseconds)
        expanded = true
    }
    SquishButton(
        onClick = onClick,
        corners = Corners(40.dp),
        pressedCorners = Corners(20.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        height = 80.dp,
        pressedScale = 0.96f,
        contentPadding = 25.dp,
        modifier = Modifier.widthIn(min = 80.dp),
    ) {
        AnimatedVisibility(expanded, enter = expandHorizontally() + fadeIn()) {
            Text(
                stringResource(Res.string.onboarding_continue),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )
        }
        Icon(JalvioIcons.ArrowForward, contentDescription = null, Modifier.size(30.dp))
    }
}

private const val BadgeCycleMillis = 7_000
private const val FlowerSpinMillis = 24_000
private const val PillExpandDelayMillis = 300L
