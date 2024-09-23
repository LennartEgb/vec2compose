package dev.lennartegb.vec2compose.svg

import kotlin.test.Test
import kotlin.test.assertEquals

internal class SVGDeserializerTest {

    private val deserializer = SVGDeserializer()

    @Test
    fun deserialize_valid_SVG_file() {
        val content = svg {
            """
            <path d="M0 0h24v24H0z" fill="none"/>
            <path d="M15.5 14h-.79l-.28-.27C15.41"/>
            """
        }

        assertEquals(
            actual = deserializer.deserialize(content),
            expected = testSVG(
                children = listOf(
                    SVG.Path(fillRule = "nonzero", pathData = "M0 0h24v24H0z", fill = "none"),
                    SVG.Path(fillRule = "nonzero", pathData = "M15.5 14h-.79l-.28-.27C15.41")
                )
            )
        )
    }

    @Test
    fun deserialize_valid_SVG_file_with_fill_rule_evenodd() {
        val content = svg {
            """
            <path d="M0 0h24v24H0z" fill="none" fill-rule="evenodd"/>
            <path d="M15.5 14h-.79l-.28-.27C15.41"/>
            """
        }

        val expected = testSVG(
            children = listOf(
                SVG.Path(fillRule = "evenodd", pathData = "M0 0h24v24H0z", fill = "none"),
                SVG.Path(fillRule = "nonzero", pathData = "M15.5 14h-.79l-.28-.27C15.41")
            )
        )

        assertEquals(actual = deserializer.deserialize(content), expected = expected)
    }

    @Test
    fun parse_SVG_with_groups() {
        val content = svg(width = "1144.12px", height = "400px", viewBox = "0 0 572.06 200") {
            """
                <g id="bird">
            		<g id="body">
            			<path id="first" d="M48.42,78.11"/>
            		</g>
            		<g id="head">
            			<path id="second" d="M48.42,78.11"/>
            		</g>
            	</g>
            """
        }
        assertEquals(
            actual = deserializer.deserialize(content),
            expected = testSVG(
                width = "1144.12px",
                height = "400px",
                viewBox = "0 0 572.06 200",
                children = listOf(
                    SVG.Group(
                        name = "bird",
                        children = listOf(
                            SVG.Group(
                                name = "body",
                                children = listOf(SVG.Path(id = "first", pathData = "M48.42,78.11"))
                            ),
                            SVG.Group(
                                name = "head",
                                children = listOf(
                                    SVG.Path(
                                        id = "second",
                                        pathData = "M48.42,78.11"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun parse_svg_with_strokes() {
        val svg = svg {
            """
            <path d="M12.5 12V7.5" stroke="#888888" stroke-width="1.5" stroke-linecap="butt" stroke-linejoin="round" stroke-opacity="0.5" stroke-miterlimit="1"/>
            """
        }
        assertEquals(
            actual = deserializer.deserialize(svg),
            expected = testSVG(
                children = listOf(
                    SVG.Path(
                        pathData = "M12.5 12V7.5",
                        strokeLinecap = "butt",
                        strokeLinejoin = "round",
                        strokeWidth = "1.5",
                        strokeColor = "#888888",
                        strokeAlpha = "0.5",
                        strokeMiter = "1"
                    )
                )
            )
        )
    }

    @Test
    fun parse_svg_with_circle() {
        val svg = svg { """<circle cx="50" cy="50" r="30" fill="green" stroke="yellow" stroke-width="10"/>""" }
        assertEquals(
            actual = deserializer.deserialize(svg),
            expected = testSVG(
                children = listOf(
                    SVG.Circle(
                        centerX = "50",
                        centerY = "50",
                        radius = "30",
                        fill = "green",
                        stroke = "yellow",
                        strokeWidth = "10"
                    )
                )
            )
        )
    }

    @Test
    fun parse_SVG_with_rectangle() {
        val svg = svg {
            """<rect width="150" height="50" fill="red" y="50" />"""
        }
        assertEquals(
            actual = deserializer.deserialize(svg),
            expected = testSVG(
                children = listOf(
                    SVG.Rectangle(
                        width = "150",
                        height = "50",
                        fill = "red",
                        y = "50"
                    )
                )
            )
        )
    }

    @Test
    fun parse_SVG_with_mixed_children_order() {
        val svg = svg {
            """
                <g id="head">
                </g>
                <rect width="150" height="50" />
                <g id="foot">
                </g>
            """.trimIndent()
        }

        assertEquals(
            actual = deserializer.deserialize(svg),
            expected = testSVG(
                children = listOf(
                    SVG.Group(name = "head"),
                    SVG.Rectangle(width = "150", height = "50"),
                    SVG.Group(name = "foot")
                )
            )
        )
    }
}
