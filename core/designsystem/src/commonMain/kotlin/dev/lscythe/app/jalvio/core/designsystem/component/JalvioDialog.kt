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
package dev.lscythe.app.jalvio.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioElevation
import dev.lscythe.app.jalvio.core.designsystem.theme.JalvioShapes

enum class DialogTone {
    Primary,
    Error,
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun JalvioDialog(
    icon: ImageVector,
    title: String,
    text: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    dismissLabel: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    tone: DialogTone = DialogTone.Primary,
) {
    val scheme = MaterialTheme.colorScheme
    val colors = tone.colors(scheme)
    val shape = MaterialTheme.shapes.extraLargeIncreased
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier.padding(DialogMargin),
    ) {
        Surface(
            modifier = Modifier.dropShadow(shape, JalvioElevation.level3),
            shape = shape,
            color = scheme.surface,
        ) {
            Column(Modifier.padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 16.dp)) {
                Column(
                    Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Box(
                        Modifier.size(IconContainerSize)
                            .clip(JalvioShapes.openDetail.toShape())
                            .background(colors.iconContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier.size(IconSize),
                            tint = colors.onIconContainer,
                        )
                    }
                    Text(
                        title,
                        modifier = Modifier.semantics { heading() },
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onSurfaceVariant,
                    )
                }
                Column(
                    Modifier.padding(top = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(ButtonGap),
                ) {
                    DialogButton(confirmLabel, onConfirm, 0, colors.action, colors.onAction)
                    DialogButton(
                        dismissLabel,
                        onDismissRequest,
                        1,
                        scheme.surfaceContainerHighest,
                        scheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogButton(
    label: String,
    onClick: () -> Unit,
    index: Int,
    container: Color,
    content: Color,
) =
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = ButtonHeight),
        shape =
            JalvioShapes.groupItem(index, 2, outer = ButtonOuterCorner, inner = ButtonInnerCorner),
        colors = ButtonDefaults.buttonColors(containerColor = container, contentColor = content),
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
    }

private class DialogToneColors(
    val iconContainer: Color,
    val onIconContainer: Color,
    val action: Color,
    val onAction: Color,
)

private fun DialogTone.colors(scheme: ColorScheme): DialogToneColors =
    when (this) {
        DialogTone.Primary ->
            DialogToneColors(
                scheme.primaryContainer,
                scheme.onPrimaryContainer,
                scheme.primary,
                scheme.onPrimary,
            )
        DialogTone.Error ->
            DialogToneColors(
                scheme.errorContainer,
                scheme.onErrorContainer,
                scheme.error,
                scheme.onError,
            )
    }

private val DialogMargin = 24.dp
private val IconContainerSize = 56.dp
private val IconSize = 28.dp
private val ButtonHeight = 56.dp
private val ButtonGap = 4.dp
private val ButtonOuterCorner = 28.dp
private val ButtonInnerCorner = 8.dp
