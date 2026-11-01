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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_community_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_community_title
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_next
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_skip
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingUiState
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.CrowdPicker
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.MorphingNextButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.PageDots
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.RiderAvatars
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CommunityStep(state: OnboardingUiState, onIntent: (OnboardingIntent) -> Unit) {
    val colors = MaterialTheme.colorScheme
    StepScaffold(
        containerColor = colors.primaryContainer,
        contentColor = colors.onPrimaryContainer,
    ) {
        SpringHeadline(
            stringResource(Res.string.onboarding_community_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up),
        )
        Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
            Box(Modifier.fillMaxWidth()) {
                CrowdPicker(
                    selected = state.crowd,
                    onSelect = { onIntent(OnboardingIntent.SelectCrowd(it)) },
                    modifier = Modifier.padding(top = 80.dp).entrance(EntranceKind.Up, 140),
                )
                RiderAvatars(Modifier.align(Alignment.TopEnd).offset(x = (-6).dp, y = 0.dp))
            }
        }
        StepBody(
            stringResource(Res.string.onboarding_community_body),
            Modifier.entrance(EntranceKind.Fade, 260),
        )
        Row(
            Modifier.fillMaxWidth().height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = { onIntent(OnboardingIntent.SkipIntro) }) {
                Text(
                    stringResource(Res.string.onboarding_skip),
                    color = colors.onPrimaryContainer,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            PageDots(
                current = 1,
                activeColor = colors.primary,
                inactiveColor = colors.primary.copy(alpha = InactiveDotAlpha),
            )
            MorphingNextButton(
                onClick = { onIntent(OnboardingIntent.Next) },
                contentDescription = stringResource(Res.string.onboarding_next),
            )
        }
    }
}

private const val InactiveDotAlpha = 0.3f
