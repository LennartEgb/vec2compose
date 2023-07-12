package svg

import HexColorParser
import Translation
import VectorSet
import commands.CommandParser
import commands.PathParser
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SVGParserTest {

    private val parser = SVGParser(
        deserializer = SVGDeserializer(),
        pathParser = PathParser(CommandParser()),
        colorParser = HexColorParser()
    )

    @Test
    fun parse_SVG_file_with_correct_fill_color() {
        val svg = svg {
            appendLine("""<path d="" fill="none"/>""")
            appendLine("""<path fill="#fff" d=""/>""")
        }
        val result = parser.parse(content = svg).getOrThrow()
        val expected = listOf(null, VectorSet.Path.FillColor(0xff, 0xff, 0xff, 0xff))
        assertEquals(expected = expected, actual = result.paths.map { it.fillColor })
    }

    @Test
    fun parse_file_with_group_rotation() {
        val svg = svg {
            appendLine("""<g transform="rotate(45 0 0)"/>""")
        }
        val result = parser.parse(svg).getOrThrow()
        assertEquals(expected = listOf(45f), actual = result.groups.map { it.rotate })
    }

    @Test
    fun parse_file_with_group_pivot() {
        val svg = svg {
            appendLine("""<g transform="rotate(45 20 30)"/>""")
        }
        val result = parser.parse(svg).getOrThrow()
        assertEquals(expected = listOf(Translation(20f, 30f)), actual = result.groups.map { it.pivot })
    }

    @Test
    fun parse_file_with_group_translate() {
        val svg = svg {
            appendLine("""<g transform="translate(20 30)"/>""")
        }
        val result = parser.parse(svg).getOrThrow()
        assertEquals(expected = listOf(Translation(20f, 30f)), actual = result.groups.map { it.translation })
    }

    private fun svg(block: StringBuilder.() -> Unit): String {
        return buildString {
            appendLine(
                """
                <svg xmlns="http://www.w3.org/2000/svg"
                     height="24px"
                     viewBox="0 0 24 24"
                     width="24px"
                     fill="#000000">
                """.trimIndent()
            )
            block()
            appendLine("</svg>")
        }
    }
}
