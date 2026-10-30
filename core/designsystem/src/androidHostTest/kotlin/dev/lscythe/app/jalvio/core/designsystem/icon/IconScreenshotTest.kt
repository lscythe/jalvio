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
package dev.lscythe.app.jalvio.core.designsystem.icon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeMode
import dev.lscythe.app.jalvio.core.designsystem.theme.ThemeSettings
import dev.lscythe.app.jalvio.core.designsystem.theme.captureThemedScreenshot
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class IconScreenshotTest {
    @Test
    fun icons() =
        captureThemedScreenshot("Icons_light", ThemeSettings(mode = ThemeMode.Light)) {
            IconSpecimen()
        }
}

@Composable
private fun IconSpecimen() =
    FlowRow(
        Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        allIcons.forEach { (outlined, filled) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(outlined)
                if (filled != null) IconBox(filled)
            }
        }
    }

@Composable
private fun IconBox(icon: ImageVector) =
    Box(Modifier.size(28.dp), contentAlignment = Alignment.Center) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
    }

private val allIcons: List<Pair<ImageVector, ImageVector?>>
    get() =
        listOf(
            JalvioIcons.Accessible to null,
            JalvioIcons.AddAPhoto to JalvioIcons.AddAPhotoFilled,
            JalvioIcons.ArrowBack to null,
            JalvioIcons.ArrowOutward to null,
            JalvioIcons.Bedtime to JalvioIcons.BedtimeFilled,
            JalvioIcons.Bookmark to JalvioIcons.BookmarkFilled,
            JalvioIcons.BookmarkAdded to JalvioIcons.BookmarkAddedFilled,
            JalvioIcons.BookmarkRemove to JalvioIcons.BookmarkRemoveFilled,
            JalvioIcons.Bus to JalvioIcons.BusFilled,
            JalvioIcons.Campaign to JalvioIcons.CampaignFilled,
            JalvioIcons.ChatBubble to JalvioIcons.ChatBubbleFilled,
            JalvioIcons.Check to null,
            JalvioIcons.CheckCircle to JalvioIcons.CheckCircleFilled,
            JalvioIcons.ChevronRight to null,
            JalvioIcons.Close to null,
            JalvioIcons.CloudOff to JalvioIcons.CloudOffFilled,
            JalvioIcons.DoorFront to JalvioIcons.DoorFrontFilled,
            JalvioIcons.DoorOpen to JalvioIcons.DoorOpenFilled,
            JalvioIcons.Elevator to JalvioIcons.ElevatorFilled,
            JalvioIcons.EventSeat to JalvioIcons.EventSeatFilled,
            JalvioIcons.Flag to JalvioIcons.FlagFilled,
            JalvioIcons.Flood to JalvioIcons.FloodFilled,
            JalvioIcons.Groups to JalvioIcons.GroupsFilled,
            JalvioIcons.Home to JalvioIcons.HomeFilled,
            JalvioIcons.Layers to JalvioIcons.LayersFilled,
            JalvioIcons.LocationDisabled to null,
            JalvioIcons.LocationOn to JalvioIcons.LocationOnFilled,
            JalvioIcons.Map to JalvioIcons.MapFilled,
            JalvioIcons.MyLocation to JalvioIcons.MyLocationFilled,
            JalvioIcons.Notifications to JalvioIcons.NotificationsFilled,
            JalvioIcons.NotificationsActive to JalvioIcons.NotificationsActiveFilled,
            JalvioIcons.Person to JalvioIcons.PersonFilled,
            JalvioIcons.Schedule to JalvioIcons.ScheduleFilled,
            JalvioIcons.Search to null,
            JalvioIcons.Share to JalvioIcons.ShareFilled,
            JalvioIcons.Shield to JalvioIcons.ShieldFilled,
            JalvioIcons.Signpost to JalvioIcons.SignpostFilled,
            JalvioIcons.Straight to null,
            JalvioIcons.SwapVert to null,
            JalvioIcons.ThumbUp to JalvioIcons.ThumbUpFilled,
            JalvioIcons.TurnRight to null,
            JalvioIcons.Undo to null,
            JalvioIcons.Walk to null,
            JalvioIcons.Wc to null,
            JalvioIcons.Work to JalvioIcons.WorkFilled,
        )
