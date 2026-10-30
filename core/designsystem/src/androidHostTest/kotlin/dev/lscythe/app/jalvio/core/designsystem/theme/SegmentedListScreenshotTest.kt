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
package dev.lscythe.app.jalvio.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.component.RouteBadge
import dev.lscythe.app.jalvio.core.designsystem.component.RouteBadgeSize
import dev.lscythe.app.jalvio.core.designsystem.icon.BusFilled
import dev.lscythe.app.jalvio.core.designsystem.icon.ChevronRight
import dev.lscythe.app.jalvio.core.designsystem.icon.JalvioIcons
import dev.lscythe.app.jalvio.core.designsystem.icon.LocationOnFilled
import dev.lscythe.app.jalvio.core.testing.screenshot.LargestPhoneWindow
import dev.lscythe.app.jalvio.core.testing.screenshot.Phone
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "w400dp-h800dp")
class SegmentedListScreenshotTest {
    @Test
    fun settingsAndSearchLists() =
        mapOf("light" to ThemeMode.Light, "dark" to ThemeMode.Dark).forEach { (name, mode) ->
            captureThemedScreenshot("SegmentedList_$name", ThemeSettings(mode = mode)) { Samples() }
        }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = LargestPhoneWindow)
class SegmentedListPhoneScreenshotTest(private val phone: Phone) {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun settings() =
        composeRule.captureThemedPhoneScreenshot("SegmentedList_settings_phones", phone) {
            Backdrop { SettingsList() }
        }

    @Test
    fun search() =
        composeRule.captureThemedPhoneScreenshot("SegmentedList_search_phones", phone) {
            Backdrop { SearchResults() }
        }

    companion object {
        @JvmStatic @Parameters(name = "{0}") fun phones() = Phone.parameters()
    }
}

@Composable
private fun Samples() =
    Backdrop(Arrangement.spacedBy(24.dp)) {
        SettingsList()
        SearchResults()
    }

@Composable
private fun Backdrop(
    arrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.() -> Unit,
) =
    Column(
        Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(JalvioLayout.screenMargin),
        verticalArrangement = arrangement,
        content = content,
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SettingsList() =
    Column(verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)) {
        SegmentedListItem(
            shapes = JalvioShapes.segmentedListItem(0, 3),
            supportingContent = { ClockChoice() },
        ) {
            RowLabel("Clock")
        }
        SegmentedListItem(
            onClick = {},
            shapes = JalvioShapes.segmentedListItem(1, 3),
            supportingContent = { Text("Map, lines and fares") },
            trailingContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Jakarta", style = MaterialTheme.typography.titleSmall)
                    Icon(JalvioIcons.ChevronRight, contentDescription = null)
                }
            },
        ) {
            RowLabel("Home city")
        }
        SegmentedListItem(
            checked = false,
            onCheckedChange = {},
            shapes = JalvioShapes.segmentedListItem(2, 3),
            supportingContent = { Text("Shapes stop morphing and the mascot stays still.") },
            trailingContent = { Switch(checked = false, onCheckedChange = null) },
        ) {
            RowLabel("Reduce motion")
        }
    }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ClockChoice() =
    Row(
        Modifier.padding(top = 8.dp).height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        ToggleButton(
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.weight(1f).fillMaxHeight(),
            shapes = ButtonGroupDefaults.connectedLeadingButtonShapes(),
        ) {
            Text("9:41 PM")
        }
        ToggleButton(
            checked = true,
            onCheckedChange = {},
            modifier = Modifier.weight(1f).fillMaxHeight(),
            shapes = ButtonGroupDefaults.connectedTrailingButtonShapes(),
        ) {
            Text("21:41")
        }
    }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SearchResults() =
    Column(verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)) {
        SegmentedListItem(
            onClick = {},
            shapes = JalvioShapes.segmentedListItem(0, 3),
            leadingContent = {
                IconTile(
                    JalvioIcons.BusFilled,
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                )
            },
            supportingContent = { Text("Stop") },
            trailingContent = { Text("6.2 km", style = MaterialTheme.typography.titleSmall) },
        ) {
            RowLabel("Halte Blok M")
        }
        SegmentedListItem(
            onClick = {},
            shapes = JalvioShapes.segmentedListItem(1, 3),
            leadingContent = { RouteBadge("1", RouteTone.Primary, RouteBadgeSize.Filter) },
            supportingContent = { Text("Line") },
            trailingContent = { Text("4 min", style = MaterialTheme.typography.titleSmall) },
        ) {
            RowLabel("Line 1 · Blok M – Kota")
        }
        SegmentedListItem(
            onClick = {},
            shapes = JalvioShapes.segmentedListItem(2, 3),
            leadingContent = {
                IconTile(
                    JalvioIcons.LocationOnFilled,
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.onTertiaryContainer,
                )
            },
            supportingContent = { Text("Place") },
            trailingContent = { Text("31 min", style = MaterialTheme.typography.titleSmall) },
        ) {
            RowLabel("Blok M Plaza")
        }
    }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun RowLabel(text: String) =
    Text(text, style = MaterialTheme.typography.bodyLargeEmphasized)

@Composable
private fun IconTile(icon: ImageVector, container: Color, content: Color) =
    Box(
        Modifier.size(48.dp).background(container, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = content)
    }
