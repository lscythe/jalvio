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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dev.lscythe.app.jalvio.core.designsystem.icon.MapFilled
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_done_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_done_title
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_open_map
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Confetti
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.cyclingBackground
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberOnboardingTertiaryScheme
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberShapeCycle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DoneStep(onIntent: (OnboardingIntent) -> Unit) {
    val tertiary = rememberOnboardingTertiaryScheme()
    StepScaffold(
        containerColor = tertiary.container,
        contentColor = tertiary.onContainer,
    ) {
        Celebration(Modifier.fillMaxWidth().weight(1f).heightIn(min = 280.dp))
        SpringHeadline(
            stringResource(Res.string.onboarding_done_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up, 380),
            fontSize = 80.sp,
            delayMillis = 380,
        )
        StepBody(
            stringResource(Res.string.onboarding_done_body),
            Modifier.entrance(EntranceKind.Fade, 460),
        )
        SquishButton(
            onClick = { onIntent(OnboardingIntent.Finish) },
            corners = Corners(40.dp),
            pressedCorners = Corners(20.dp),
            containerColor = tertiary.button,
            contentColor = tertiary.onButton,
            height = 80.dp,
            modifier = Modifier.fillMaxWidth().entrance(EntranceKind.Up, 520),
        ) {
            Icon(JalvioIcons.MapFilled, contentDescription = null, Modifier.size(28.dp))
            Text(
                stringResource(Res.string.onboarding_open_map),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun Celebration(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val shapes = remember { listOf(MaterialShapes.Burst, MaterialShapes.Flower) }
    val cycle = rememberShapeCycle(shapes, stepMillis = BurstCycleMillis / shapes.size)
    Box(
        modifier.clearAndSetSemantics {},
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier.size(300.dp)
                .entrance(EntranceKind.Grow)
                .cyclingBackground(cycle, colors.primary)
        )
        Confetti(Modifier.fillMaxSize())
        Jali(
            JaliMood.Thanks,
            Modifier.entrance(EntranceKind.Grow, 180),
            tone = JaliTone.Tertiary,
            size = 170.dp,
        )
    }
}

private const val BurstCycleMillis = 6_000
