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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.component.jali.Jali
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliMood
import dev.lscythe.app.jalvio.core.designsystem.component.jali.JaliTone
import dev.lscythe.app.jalvio.core.designsystem.icon.ArrowForward
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.WordmarkLetters
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.app_name
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_get_started
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_welcome_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_welcome_title
import dev.lscythe.app.jalvio.domain.preferences.model.AppLanguage
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingUiState
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.ConnectedButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceDelays
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.LanguageSwitch
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.MascotSun
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.PageDots
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.rememberOnboardingBrandScheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WelcomeStep(
    state: OnboardingUiState,
    onIntent: (OnboardingIntent) -> Unit,
) {
    val brand = rememberOnboardingBrandScheme()
    val currentLanguage =
        state.languages.firstOrNull { it.selected }?.language ?: AppLanguage.English

    StepScaffold(
        containerColor = brand.container,
        contentColor = brand.onContainer,
        decoration = { MascotSun(Modifier.align(Alignment.TopEnd).offset(x = 70.dp, y = 96.dp)) },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().entrance(EntranceKind.Fade),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Wordmark()
            LanguageSwitch(
                current = currentLanguage,
                onSelect = { onIntent(OnboardingIntent.SelectLanguage(it)) },
            )
        }
        Spacer(Modifier.weight(1f))
        SpringHeadline(
            stringResource(Res.string.onboarding_welcome_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up, EntranceDelays.Second),
            fontSize = 76.sp,
            delayMillis = EntranceDelays.Second,
        )
        StepBody(
            stringResource(Res.string.onboarding_welcome_body),
            Modifier.entrance(EntranceKind.Fade, EntranceDelays.Third),
            color = brand.body,
            fontSize = 17.sp,
        )
        PageDots(
            current = 0,
            activeColor = brand.activeDot,
            inactiveColor = brand.inactiveDot,
            modifier = Modifier.entrance(EntranceKind.Fade, EntranceDelays.Fourth),
        )
        ConnectedButton(
            label = stringResource(Res.string.onboarding_get_started),
            onClick = { onIntent(OnboardingIntent.Next) },
            containerColor = brand.button,
            contentColor = brand.onButton,
            modifier = Modifier.entrance(EntranceKind.Up, EntranceDelays.Third),
        ) {
            Icon(JalvioIcons.ArrowForward, contentDescription = null, Modifier.size(32.dp))
        }
    }
}

@Composable
private fun Wordmark(modifier: Modifier = Modifier) {
    val name = stringResource(Res.string.app_name)
    Row(
        modifier.clearAndSetSemantics { contentDescription = name },
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            JalvioIcons.WordmarkLetters,
            contentDescription = null,
            Modifier.height(30.dp).aspectRatio(WordmarkAspect),
        )
        Jali(JaliMood.Calm, tone = JaliTone.Tertiary, size = 24.dp, idle = false)
    }
}

private const val WordmarkAspect = 1922f / 1210f
private const val InactiveDotAlpha = 0.35f
