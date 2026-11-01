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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.component.jali.Jali
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliMood
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliTone
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.LockFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.SmartphoneFilled
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_account_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_account_google
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_account_local
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_account_no_transjakarta
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_account_skip
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_account_title
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepTopBar
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepTopBarColors
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.cyclingBackground
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberOnboardingBrandScheme
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberShapeCycle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AccountStep(onIntent: (OnboardingIntent) -> Unit) {
    val colors = MaterialTheme.colorScheme
    val brand = rememberOnboardingBrandScheme()
    StepScaffold(
        containerColor = brand.container,
        contentColor = brand.onContainer,
        decoration = { AccountDecoration(brand.decorationShape) },
        topBar = {
            StepTopBar(
                filled = 4,
                onBack = { onIntent(OnboardingIntent.Back) },
                colors =
                    StepTopBarColors(
                        button = brand.topBarButton,
                        buttonContent = brand.topBarButtonContent,
                        track = brand.topBarTrack,
                        fill = brand.topBarFill,
                    ),
            )
        },
    ) {
        Spacer(Modifier.weight(1f))
        SpringHeadline(
            stringResource(Res.string.onboarding_account_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up, 120),
            fontSize = 60.sp,
            delayMillis = 120,
        )
        StepBody(
            stringResource(Res.string.onboarding_account_body),
            Modifier.entrance(EntranceKind.Fade, 220),
            color = brand.body,
            fontSize = 15.sp,
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SquishButton(
                onClick = { onIntent(OnboardingIntent.Next) },
                corners = Corners.top(32.dp, 10.dp),
                pressedCorners = Corners.top(16.dp, 8.dp),
                containerColor = brand.cardBackground,
                contentColor = brand.cardContent,
                height = 64.dp,
                modifier = Modifier.fillMaxWidth().entrance(EntranceKind.Up, 260),
            ) {
                Box(
                    Modifier.size(26.dp).background(colors.surfaceContainer, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("G", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text(
                    stringResource(Res.string.onboarding_account_google),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            SquishButton(
                onClick = { onIntent(OnboardingIntent.Next) },
                corners = Corners.bottom(32.dp, 10.dp),
                pressedCorners = Corners.bottom(16.dp, 8.dp),
                containerColor = brand.localAccountBackground,
                contentColor = brand.localAccountContent,
                height = 64.dp,
                modifier = Modifier.fillMaxWidth().entrance(EntranceKind.Up, 320),
            ) {
                Icon(JalvioIcons.SmartphoneFilled, contentDescription = null)
                Text(
                    stringResource(Res.string.onboarding_account_local),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            TextButton(
                onClick = { onIntent(OnboardingIntent.Next) },
                modifier = Modifier.fillMaxWidth().entrance(EntranceKind.Fade, 360),
            ) {
                Text(
                    stringResource(Res.string.onboarding_account_skip),
                    color = brand.skipButton,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                JalvioIcons.LockFilled,
                contentDescription = null,
                tint = brand.lockText,
                modifier = Modifier.size(16.dp),
            )
            Text(
                stringResource(Res.string.onboarding_account_no_transjakarta),
                color = brand.lockText,
                fontSize = 12.sp,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AccountDecoration(cookieColor: Color) {
    val shapes = remember {
        listOf(MaterialShapes.Cookie9Sided, MaterialShapes.Flower, MaterialShapes.Clover4Leaf)
    }
    val cycle = rememberShapeCycle(shapes, stepMillis = CookieCycleMillis / shapes.size)
    Box(
        Modifier.offset(x = (-90).dp, y = 96.dp)
            .size(200.dp)
            .clearAndSetSemantics {}
            .entrance(EntranceKind.Grow, 60)
            .cyclingBackground(cycle, cookieColor)
    )
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
        Jali(
            JaliMood.Calm,
            Modifier.offset(x = 24.dp, y = 120.dp).entrance(EntranceKind.Grow, 160),
            tone = JaliTone.Tertiary,
            size = 180.dp,
        )
    }
}

private const val TrackAlpha = 0.25f
private const val CookieCycleMillis = 7_000
