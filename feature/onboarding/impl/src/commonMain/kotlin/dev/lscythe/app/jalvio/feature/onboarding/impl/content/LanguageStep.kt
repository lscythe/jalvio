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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_continue
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_language_body
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_language_title
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingUiState
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.MorphingCheck
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepTopBar
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.LanguageOptionUi
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LanguageStep(state: OnboardingUiState, onIntent: (OnboardingIntent) -> Unit) {
    val colors = MaterialTheme.colorScheme
    StepScaffold(
        containerColor = colors.surface,
        contentColor = colors.onSurface,
        topBar = { StepTopBar(filled = 1, onBack = { onIntent(OnboardingIntent.Back) }) },
    ) {
        SpringHeadline(
            stringResource(Res.string.onboarding_language_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up),
        )
        StepBody(
            stringResource(Res.string.onboarding_language_body),
            Modifier.entrance(EntranceKind.Fade, 120),
            color = colors.onSurfaceVariant,
            fontSize = 15.sp,
        )
        Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            state.languages.forEachIndexed { index, option ->
                LanguageCard(
                    option,
                    first = index == 0,
                    onClick = { onIntent(OnboardingIntent.SelectLanguage(option.language)) },
                    modifier = Modifier.entrance(EntranceKind.Up, 100 + index * 60),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        SquishButton(
            onClick = { onIntent(OnboardingIntent.Next) },
            corners = Corners(36.dp),
            pressedCorners = Corners(18.dp),
            containerColor = colors.primary,
            contentColor = colors.onPrimary,
            modifier = Modifier.fillMaxWidth().entrance(EntranceKind.Up, 260),
        ) {
            Text(
                stringResource(Res.string.onboarding_continue),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LanguageCard(
    option: LanguageOptionUi,
    first: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val motion = MaterialTheme.motionScheme
    val outer by
        animateDpAsState(if (option.selected) 40.dp else 28.dp, motion.defaultSpatialSpec())
    val inner by animateDpAsState(if (option.selected) 12.dp else 8.dp, motion.defaultSpatialSpec())
    val container by
        animateColorAsState(
            if (option.selected) colors.primary else colors.surfaceContainer,
            motion.defaultEffectsSpec(),
        )
    val content by
        animateColorAsState(
            if (option.selected) colors.onPrimary else colors.onSurface,
            motion.defaultEffectsSpec(),
        )
    val shape =
        if (first) RoundedCornerShape(outer, outer, inner, inner)
        else RoundedCornerShape(inner, inner, outer, outer)
    Surface(
        shape = shape,
        color = container,
        contentColor = content,
        modifier =
            modifier
                .fillMaxWidth()
                .selectable(option.selected, role = Role.RadioButton, onClick = onClick),
    ) {
        Box(Modifier.heightIn(min = 100.dp).padding(horizontal = 20.dp, vertical = 16.dp)) {
            if (option.selected) {
                MorphingCheck(Modifier.align(Alignment.TopEnd).padding(start = 8.dp))
            }
            Column(
                Modifier.align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    option.name,
                    fontSize = 24.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight(750),
                )
                Text(
                    option.sample,
                    fontSize = 14.sp,
                    color = content.copy(alpha = SampleAlpha),
                )
            }
        }
    }
}

private const val SampleAlpha = 0.8f
