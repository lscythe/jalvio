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
package dev.lscythe.app.jalvio.convention.translation

import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import org.xml.sax.InputSource

internal data class ParityReport(
    val missing: Set<String>,
    val extra: Set<String>,
    val placeholderMismatches: Set<String>,
) {
    val isClean: Boolean
        get() = missing.isEmpty() && extra.isEmpty() && placeholderMismatches.isEmpty()
}

internal fun translationParity(sourceXml: String, translationXml: String): ParityReport {
    val source = translatableEntries(sourceXml)
    val translation = translatableEntries(translationXml)
    return ParityReport(
        missing = source.keys - translation.keys,
        extra = translation.keys - source.keys,
        placeholderMismatches =
            source.keys.intersect(translation.keys).filterTo(mutableSetOf()) {
                source.getValue(it) != translation.getValue(it)
            },
    )
}

private val PLACEHOLDER = Regex("""%(\d+\$)?[sd]""")

private val ENTRY_TAGS = setOf("string", "plurals", "string-array")

private fun translatableEntries(xml: String): Map<String, List<String>> =
    DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(InputSource(StringReader(xml)))
        .documentElement
        .childElements()
        .filter { it.tagName in ENTRY_TAGS && it.getAttribute("translatable") != "false" }
        .associate { it.getAttribute("name") to placeholdersOf(it) }

private fun placeholdersOf(entry: Element): List<String> {
    val found = PLACEHOLDER.findAll(entry.textContent).map { it.value }.sorted()
    return if (entry.tagName == "plurals") found.distinct().toList() else found.toList()
}

private fun Element.childElements(): Sequence<Element> =
    (0 until childNodes.length).asSequence().map(childNodes::item).filterIsInstance<Element>()
