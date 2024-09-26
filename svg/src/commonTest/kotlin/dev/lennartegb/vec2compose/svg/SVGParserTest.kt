package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVector.Path.FillType
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.Command
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class SVGParserTest {

    private val ImageVector.groups: List<ImageVector.Group>
        get() = nodes.filterIsInstance<ImageVector.Group>()

    @Test
    fun svg_without_viewBox_uses_width_and_height() {
        val svg = svg(viewBox = null) { "" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).getOrThrow(),
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = emptyList()
            )
        )
    }

    @Test
    fun parse_SVG_file_with_correct_fill_color() {
        val svg = svg {
            """
            <path d="" fill="none"/>
            <path fill="#fff" d=""/>
            <path d=""/>
            """
        }
        val expected = listOf(
            ImageVector.Path(
                fillType = FillType.Default,
                fillColor = null,
                commands = emptyList(),
                alpha = 1f
            ),
            ImageVector.Path(
                fillType = FillType.Default,
                fillColor = ImageVector.Path.FillColor(0xff, 0xff, 0xff, 0xff),
                commands = emptyList(),
                alpha = 1f
            ),
            ImageVector.Path(
                fillType = FillType.Default,
                fillColor = ImageVector.Path.FillColor(0x00, 0x00, 0x00, alpha = 0xff),
                commands = emptyList(),
                alpha = 1f
            )
        )
        assertEquals(
            actual = svgImageVectorParser().parse(content = svg).map { it.nodes },
            expected = Result.success(expected)
        )
    }

    @Test
    fun parse_file_with_group_rotation() {
        val svg = svg { """<g transform="rotate(45 0 0)"/>""" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).map { it.nodes },
            expected = Result.success(
                listOf(
                    ImageVector.Group(
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
    fun parse_file_with_group_pivot() {
        val svg = svg { """<g transform="rotate(45 20 30)"/>""" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).map { it.groups.map { it.pivot } },
            expected = Result.success(listOf(Translation(20f, 30f)))
        )
    }

    @Test
    fun parse_file_with_group_translate() {
        val svg = svg { """<g transform="translate(20 30)"/>""" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg)
                .map { set -> set.groups.map { it.translation } },
            expected = Result.success(listOf(Translation(20f, 30f)))
        )
    }

    @Test
    fun parse_path_data_with_fill_none() {
        val svg = svg { """<path d="" fill="none"/>""" }

        assertEquals(
            actual = svgImageVectorParser().parse(svg).map { it.nodes },
            expected = Result.success(
                listOf(
                    ImageVector.Path(
                        fillType = FillType.Default,
                        fillColor = null,
                        commands = emptyList(),
                        alpha = 1f
                    )
                )
            )
        )
    }

    @Test
    fun parse_invalid_ellipse_from_SVG() {
        val svg = svg { """<ellipse cx="10" /> """ }
        assertFailsWith<IllegalArgumentException> { svgImageVectorParser().parse(svg).getOrThrow() }
    }

    @Test
    fun parse_ellipse_from_SVG() {
        val svg = svg {
            """<ellipse cx="10" cy="20" rx="10" ry="20" fill="red" stroke="blue" stroke-width="3"/>"""
        }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).map { it.nodes },
            expected = Result.success(
                listOf(
                    ImageVector.Path(
                        fillType = FillType.Default,
                        fillColor = ImageVector.Path.FillColor(
                            red = 0xFF,
                            green = 0x00,
                            blue = 0x00,
                            alpha = 0xFF
                        ),
                        alpha = 1f,
                        stroke = Stroke(
                            color = ImageVector.Path.FillColor(
                                red = 0x00,
                                green = 0x00,
                                blue = 0xFF,
                                alpha = 0xFF
                            ),
                            alpha = 1f,
                            width = 3f
                        ),
                        commands = listOf(
                            MoveTo(0f, 20f),
                            ArcTo(
                                horizontalEllipseRadius = 10f,
                                verticalEllipseRadius = 20f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isPositiveArc = true,
                                x1 = 20f,
                                y1 = 20f
                            ),
                            ArcTo(
                                horizontalEllipseRadius = 10f,
                                verticalEllipseRadius = 20f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isPositiveArc = true,
                                x1 = 0f,
                                y1 = 20f
                            ),
                            Close()
                        )
                    )
                )
            )
        )
    }

    @Test
    fun parse_circle_from_SVG() {
        val svg = svg { """<circle cx="5" cy="5" r="5" />""" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).getOrThrow(),
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    ImageVector.Path(
                        fillType = FillType.Default,
                        fillColor = ImageVector.Path.FillColor(0x00, 0x00, 0x00, 0xFF),
                        commands = listOf(
                            MoveTo(x = 0f, y = 5f),
                            ArcTo(
                                horizontalEllipseRadius = 5f,
                                verticalEllipseRadius = 5f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isPositiveArc = true,
                                x1 = 10f,
                                y1 = 5f
                            ),
                            ArcTo(
                                horizontalEllipseRadius = 5f,
                                verticalEllipseRadius = 5f,
                                theta = 0f,
                                isMoreThanHalf = false,
                                isPositiveArc = true,
                                x1 = 0f,
                                y1 = 5f
                            ),
                            Close()
                        ),
                        alpha = 1f,
                        stroke = Stroke()
                    )
                )
            )
        )
    }

    @Test
    fun parse_rect_from_SVG() {
        val svg = svg { """<rect x="1" y="2" width="10" height="5" />""" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).getOrThrow(),
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    ImageVector.Path(
                        fillType = FillType.Default,
                        fillColor = null,
                        commands = listOf(
                            MoveTo(x = 1f, y = 2f),
                            LineTo(x = 11f, y = 2f),
                            LineTo(x = 11f, y = 7f),
                            LineTo(x = 1f, y = 7f),
                            Close()
                        ),
                        alpha = 1f,
                        stroke = Stroke(width = 0f)
                    )
                )
            )
        )
    }

    @Test
    fun parse_minimal_polygon_from_SVG() {
        val svg = svg { """<polygon points="0,0 10,10, 0,10" />""" }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).getOrThrow(),
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    ImageVector.Path(
                        fillType = FillType.Default,
                        fillColor = ImageVector.Path.FillColor(0, 0, 0, 0xff),
                        commands = listOf(Command("M0,0 10,10, 0,10"), Close()),
                        alpha = 1f,
                        stroke = Stroke(width = 1f)
                    )
                )
            )
        )
    }

    @Test
    fun parse_polygon_from_SVG() {
        val svg = svg {
            """<polygon points="0,0 10,10, 0,10" fill="none" fill-rule="evenodd" opacity="0.5" stroke="black" stroke-width="3" />"""
        }
        assertEquals(
            actual = svgImageVectorParser().parse(svg).getOrThrow(),
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    ImageVector.Path(
                        fillType = FillType.EvenOdd,
                        fillColor = null,
                        commands = listOf(Command("M0,0 10,10, 0,10"), Close()),
                        alpha = .5f,
                        stroke = Stroke(
                            color = ImageVector.Path.FillColor(0, 0, 0, 255),
                            width = 3f
                        )
                    )
                )
            )
        )
    }
}
