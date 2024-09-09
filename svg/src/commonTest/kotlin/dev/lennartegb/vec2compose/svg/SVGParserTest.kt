package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.VectorSet.Path.FillType
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke
import dev.lennartegb.vec2compose.core.commands.Command
import dev.lennartegb.vec2compose.core.commands.Command.ArcTo
import dev.lennartegb.vec2compose.core.commands.Command.Close
import dev.lennartegb.vec2compose.core.commands.Command.MoveTo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class SVGParserTest {

    private val parser = SVGParser()

    private val VectorSet.groups: List<VectorSet.Group> get() = nodes.filterIsInstance<VectorSet.Group>()

    @Test
    fun `parse SVG file with correct fill color`() {
        val svg = svg {
            """
            <path d="" fill="none"/>
            <path fill="#fff" d=""/>
            <path d=""/>
            """
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
            )
        )
        assertEquals(
            actual = parser.parse(content = svg).map { it.nodes },
            expected = Result.success(expected)
        )
    }

    @Test
    fun `parse file with group rotation`() {
        val svg = svg { """<g transform="rotate(45 0 0)"/>""" }
        assertEquals(
            actual = parser.parse(svg).map { it.nodes },
            expected = Result.success(
                listOf(
                    VectorSet.Group(
                        name = null,
                        nodes = emptyList(),
                        rotate = 45f,
                        pivot = Translation(0f, 0f),
                        translation = Translation(0f, 0f),
                        scale = Scale(1f, 1f)
                    )
                )
            )
        )
    }

    @Test
    fun `parse file with group pivot`() {
        val svg = svg { """<g transform="rotate(45 20 30)"/>""" }
        assertEquals(
            actual = parser.parse(svg).map { it.groups.map { it.pivot } },
            expected = Result.success(listOf(Translation(20f, 30f)))
        )
    }

    @Test
    fun `parse file with group translate`() {
        val svg = svg { """<g transform="translate(20 30)"/>""" }
        assertEquals(
            actual = parser.parse(svg).map { set -> set.groups.map { it.translation } },
            expected = Result.success(listOf(Translation(20f, 30f)))
        )
    }

    @Test
    fun `parse path data with fill none`() {
        val svg = svg { """<path d="" fill="none"/>""" }

        assertEquals(
            actual = parser.parse(svg).map { it.nodes },
            expected = Result.success(
                listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.Default,
                        fillColor = null,
                        commands = emptyList(),
                        alpha = 1f
                    )
                )
            )
        )
    }

    @Test
    fun `parse invalid ellipse from SVG`() {
        val svg = svg { """<ellipse cx="10" /> """ }
        assertFailsWith<IllegalArgumentException> { parser.parse(svg).getOrThrow() }
    }

    @Test
    fun `parse ellipse from SVG`() {
        val svg = svg {
            """<ellipse cx="10" cy="20" rx="10" ry="20" fill="red" stroke="blue" stroke-width="3"/>"""
        }
        assertEquals(
            actual = parser.parse(svg).map { it.nodes },
            expected = Result.success(
                listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.Default,
                        fillColor = VectorSet.Path.FillColor(
                            red = 0xFF,
                            green = 0x00,
                            blue = 0x00,
                            alpha = 0xFF
                        ),
                        alpha = 1f,
                        stroke = VectorSet.Path.Stroke(
                            color = VectorSet.Path.FillColor(
                                red = 0x00,
                                green = 0x00,
                                blue = 0xFF,
                                alpha = 0xFF
                            ),
                            alpha = 1f,
                            width = 3f
                        ),
                        commands = listOf(
                            Command.MoveTo(0f, 20f, isAbsolute = true),
                            Command.ArcTo(
                                horizontalEllipseRadius = 10f,
                                verticalEllipseRadius = 20f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isAbsolute = true,
                                isPositiveArc = true,
                                x1 = 20f,
                                y1 = 20f
                            ),
                            Command.ArcTo(
                                horizontalEllipseRadius = 10f,
                                verticalEllipseRadius = 20f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isAbsolute = true,
                                isPositiveArc = true,
                                x1 = 0f,
                                y1 = 20f
                            ),
                            Command.Close
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `parse circle from SVG`() {
        val svg = svg {
            """
            <circle cx="5" cy="5" r="5" />
            """
        }
        assertEquals(
            actual = parser.parse(svg).getOrThrow(),
            expected = VectorSet(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    VectorSet.Path(
                        fillType = FillType.Default,
                        fillColor = VectorSet.Path.FillColor(0x00, 0x00, 0x00, 0xFF),
                        commands = listOf(
                            MoveTo(x = 0f, y = 5f, isAbsolute = true),
                            ArcTo(
                                horizontalEllipseRadius = 5f,
                                verticalEllipseRadius = 5f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isAbsolute = true,
                                isPositiveArc = true,
                                x1 = 10f,
                                y1 = 5f
                            ),
                            ArcTo(
                                horizontalEllipseRadius = 5f,
                                verticalEllipseRadius = 5f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isAbsolute = true,
                                isPositiveArc = true,
                                x1 = 0f,
                                y1 = 5f
                            ),
                            Close
                        ),
                        alpha = 1f,
                        stroke = Stroke()
                    )
                )
            )
        )
    }
}
