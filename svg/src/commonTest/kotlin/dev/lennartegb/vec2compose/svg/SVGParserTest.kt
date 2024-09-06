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
            """<path d="" fill="none"/>
            <path fill="#fff" d=""/>
            <path d=""/>"""
        }
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
        assertEquals(
            actual = parser.parse(content = svg).map { it.nodes },
            expected = Result.success(expected),
        )
    }

    @Test
    fun parse_file_with_group_rotation() {
        val svg = svg { """<g transform="rotate(45 0 0)"/>""" }
        assertEquals(
            actual = parser.parse(svg).map { it.groups.map { it.rotate } },
            expected = Result.success(listOf(45f)),
        )
    }

    @Test
    fun parse_file_with_group_pivot() {
        val svg = svg { """<g transform="rotate(45 20 30)"/>""" }
        assertEquals(
            actual = parser.parse(svg).map { it.groups.map { it.pivot } },
            expected = Result.success(listOf(Translation(20f, 30f))),
        )
    }

    @Test
    fun parse_file_with_group_translate() {
        val svg = svg { """<g transform="translate(20 30)"/>""" }
        assertEquals(
            actual = parser.parse(svg).map { set -> set.groups.map { it.translation } },
            expected = Result.success(listOf(Translation(20f, 30f))),
        )
    }

    @Test
    fun parse_path_data_with_fill_none() {
        val svg = svg { """<path d="" fill="none"/>""" }

        assertEquals(
            actual = parser.parse(svg).map { it.paths },
            expected = Result.success(
                listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.Default,
                        fillColor = null,
                        commands = emptyList(),
                        alpha = 1f
                    )
                )
            ),
        )
    }
}
