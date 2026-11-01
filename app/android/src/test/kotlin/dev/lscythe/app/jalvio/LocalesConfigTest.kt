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
package dev.lscythe.app.jalvio

import android.app.Application
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.xmlpull.v1.XmlPullParser

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class LocalesConfigTest {
    @Test
    fun theAppOffersEnglishAndIndonesian() {
        val parser = RuntimeEnvironment.getApplication().resources.getXml(R.xml.locales_config)
        val locales = buildList {
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType == XmlPullParser.START_TAG && parser.name == "locale") {
                    add(parser.getAttributeValue(AndroidNamespace, "name"))
                }
            }
        }

        locales shouldContainExactly listOf("en", "id")
    }
}

private const val AndroidNamespace = "http://schemas.android.com/apk/res/android"
