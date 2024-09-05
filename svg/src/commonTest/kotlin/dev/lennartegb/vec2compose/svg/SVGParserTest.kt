package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SVGParserTest {

    private val parser = SVGParser()

    private val VectorSet.groups: List<VectorSet.Group> get() = nodes.filterIsInstance<VectorSet.Group>()
    private val VectorSet.paths: List<VectorSet.Path> get() = nodes.filterIsInstance<VectorSet.Path>()

    @Test
    fun parse_SVG_file_with_correct_fill_color() {
        val svg = svg {
            appendLine("""<path d="" fill="none"/>""")
            appendLine("""<path fill="#fff" d=""/>""")
            appendLine("""<path d=""/>""")
        }
        val result = parser.parse(content = svg).getOrThrow()
        val expected = listOf(
            VectorSet.Path(
                fillType = VectorSet.Path.FillType.Default,
                fillColor = null,
                commands = emptyList(),
                alpha = 1f
            ),
            VectorSet.Path(
                fillType = VectorSet.Path.FillType.Default,
                fillColor = VectorSet.Path.FillColor(0xff, 0xff, 0xff, 0xff),
                commands = emptyList(),
                alpha = 1f
            ),
            VectorSet.Path(
                fillType = VectorSet.Path.FillType.Default,
                fillColor = VectorSet.Path.FillColor(0x00, 0x00, 0x00, alpha = 0xff),
                commands = emptyList(),
                alpha = 1f
            ),
        )
        assertEquals(expected = expected, actual = result.nodes)
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
        assertEquals(
            expected = listOf(Translation(20f, 30f)),
            actual = result.groups.map { it.pivot })
    }

    @Test
    fun parse_file_with_group_translate() {
        val svg = svg {
            appendLine("""<g transform="translate(20 30)"/>""")
        }
        assertEquals(
            actual = parser.parse(svg).map { set -> set.groups.map { it.translation } },
            expected = Result.success(listOf(Translation(20f, 30f))),
        )
    }

    @Test
    fun parse_path_data_with_fill_none() {
        val svg = svg {
            appendLine("""<path d="" fill="none"/>""")
        }

        assertEquals(
            expected = listOf(
                VectorSet.Path(
                    fillType = VectorSet.Path.FillType.Default,
                    fillColor = null,
                    commands = emptyList(),
                    alpha = 1f
                )
            ),
            actual = parser.parse(svg).map { it.paths }.getOrThrow()
        )
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
