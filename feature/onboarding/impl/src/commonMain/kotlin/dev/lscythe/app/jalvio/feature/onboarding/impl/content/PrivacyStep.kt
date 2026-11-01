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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lscythe.app.jalvio.core.designsystem.icon.BarChart
import dev.lscythe.app.jalvio.core.designsystem.icon.BugReportFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioShapes
import dev.lscythe.app.jalvio.core.resources.generated.resources.Res
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_continue
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_analytics
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_analytics_description
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_body_off_both
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_body_off_one
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_body_on_both
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_body_on_one
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_crash_reports
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_crash_reports_description
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_note
import dev.lscythe.app.jalvio.core.resources.generated.resources.onboarding_privacy_title
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingIntent
import dev.lscythe.app.jalvio.feature.onboarding.impl.OnboardingUiState
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.Corners
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.EntranceKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.PrivacyToggleCard
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SpringHeadline
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.SquishButton
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepBody
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepScaffold
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.StepTopBar
import dev.lscythe.app.jalvio.feature.onboarding.impl.component.entrance
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyKind
import dev.lscythe.app.jalvio.feature.onboarding.impl.model.PrivacyLeadUi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PrivacyStep(state: OnboardingUiState, onIntent: (OnboardingIntent) -> Unit) {
    val colors = MaterialTheme.colorScheme
    StepScaffold(
        containerColor = colors.surface,
        contentColor = colors.onSurface,
        topBar = { StepTopBar(filled = 3, onBack = { onIntent(OnboardingIntent.Back) }) },
    ) {
        SpringHeadline(
            stringResource(Res.string.onboarding_privacy_title),
            Modifier.semantics { heading() }.entrance(EntranceKind.Up),
        )
        StepBody(
            stringResource(state.privacyLead.text),
            Modifier.entrance(EntranceKind.Fade, 120),
            color = colors.onSurfaceVariant,
            fontSize = 15.sp,
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            state.privacyToggles.forEachIndexed { index, toggle ->
                val look = toggle.kind.look()
                PrivacyToggleCard(
                    title = stringResource(look.title),
                    description = stringResource(look.description),
                    icon = look.icon,
                    iconContainerColor = look.iconContainer,
                    iconContentColor = look.iconContent,
                    checked = toggle.checked,
                    onCheckedChange = { onIntent(OnboardingIntent.SetPrivacy(toggle.kind, it)) },
                    shape =
                        JalvioShapes.groupItem(
                            index,
                            state.privacyToggles.size,
                            outer = 32.dp,
                            inner = 8.dp,
                        ),
                    modifier = Modifier.entrance(EntranceKind.Up, 160 + index * 60),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        StepBody(
            stringResource(Res.string.onboarding_privacy_note),
            color = colors.onSurfaceVariant,
            fontSize = 13.sp,
        )
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

private val PrivacyLeadUi.text: StringResource
    get() =
        when {
            onByDefault && single -> Res.string.onboarding_privacy_body_on_one
            onByDefault -> Res.string.onboarding_privacy_body_on_both
            single -> Res.string.onboarding_privacy_body_off_one
            else -> Res.string.onboarding_privacy_body_off_both
        }

private class PrivacyLook(
    val title: StringResource,
    val description: StringResource,
    val icon: ImageVector,
    val iconContainer: Color,
    val iconContent: Color,
)

@Composable
private fun PrivacyKind.look(): PrivacyLook {
    val colors = MaterialTheme.colorScheme
    return when (this) {
        PrivacyKind.Analytics ->
            PrivacyLook(
                Res.string.onboarding_privacy_analytics,
                Res.string.onboarding_privacy_analytics_description,
                JalvioIcons.BarChart,
                colors.secondaryContainer,
                colors.onSecondaryContainer,
            )
        PrivacyKind.CrashReports ->
            PrivacyLook(
                Res.string.onboarding_privacy_crash_reports,
                Res.string.onboarding_privacy_crash_reports_description,
                JalvioIcons.BugReportFilled,
                colors.errorContainer,
                colors.onErrorContainer,
            )
    }
}
