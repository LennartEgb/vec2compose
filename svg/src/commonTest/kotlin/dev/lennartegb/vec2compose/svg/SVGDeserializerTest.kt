package dev.lennartegb.vec2compose.svg

import kotlin.test.Test
import kotlin.test.assertEquals

internal class SVGDeserializerTest {

    private val deserializer = SVGDeserializer()

    @Test
    fun deserialize_valid_SVG_file() {
        val content = """
            <svg xmlns="http://www.w3.org/2000/svg"
                width="24px"
                height="24px"
                viewBox="0 0 24 24"
                fill="#000000">
                <path d="M0 0h24v24H0z" fill="none"/>
                <path d="M15.5 14h-.79l-.28-.27C15.41"/>
            </svg>
        """.trimIndent()

        assertEquals(
            actual = deserializer.deserialize(content),
            expected = SVG(
                width = "24px",
                height = "24px",
                viewBox = "0 0 24 24",
                fill = "#000000",
                children = listOf(
                    SVG.Path(fillRule = "nonzero", pathData = "M0 0h24v24H0z", fill = "none"),
                    SVG.Path(fillRule = "nonzero", pathData = "M15.5 14h-.79l-.28-.27C15.41")
                ),
            ),
        )
    }

    @Test
    fun deserialize_valid_SVG_file_with_fill_rule_evenodd() {
        val content = """
            <svg xmlns="http://www.w3.org/2000/svg"
                height="24px"
                viewBox="0 0 24 24"
                width="24px"
                fill="#000000">
                <path d="M0 0h24v24H0z" fill="none" fill-rule="evenodd"/>
                <path d="M15.5 14h-.79l-.28-.27C15.41"/>
            </svg>
        """.trimIndent()

        val expected = SVG(
            width = "24px",
            height = "24px",
            viewBox = "0 0 24 24",
            fill = "#000000",
            children = listOf(
                SVG.Path(fillRule = "evenodd", pathData = "M0 0h24v24H0z", fill = "none"),
                SVG.Path(fillRule = "nonzero", pathData = "M15.5 14h-.79l-.28-.27C15.41")
            ),
        )

        assertEquals(actual = deserializer.deserialize(content), expected = expected)
    }

    @Test
    fun parse_SVG_with_groups() {
        // NOTE: The supported SVGs must have the documented namespace: https://www.w3.org/TR/SVG/struct.html#Namespace
        val content = """
            <svg xmlns="http://www.w3.org/2000/svg" width="1144.12px" height="400px" viewBox="0 0 572.06 200">
            	<g id="bird">
            		<g id="body">
            			<path id="first" d="M48.42,78.11"/>
            		</g>
            		<g id="head">
            			<path id="second" d="M48.42,78.11"/>
            		</g>
            	</g>
            </svg>
        """.trimIndent()
        assertEquals(
            actual = deserializer.deserialize(content),
            expected = SVG(
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
        val svg = """
            <svg width="25" height="24" viewBox="0 0 25 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12.5 12V7.5" stroke="#888888" stroke-width="1.5" stroke-linecap="butt" stroke-linejoin="round" stroke-opacity="0.5" stroke-miterlimit="1"/>
            </svg>
        """.trimIndent()
        assertEquals(
            actual = deserializer.deserialize(svg),
            expected = SVG(
                width = "25",
                height = "24",
                viewBox = "0 0 25 24",
                fill = "none",
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
        val svg = """
            <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg">
              <circle cx="50" cy="50" r="30" fill="green" stroke="yellow" stroke-width="10"/>
            </svg>
        """.trimIndent()
        assertEquals(
            actual = deserializer.deserialize(svg),
            expected = SVG(
                width = "100",
                height = "100",
                children = listOf(
                    SVG.Circle(
                        centerX = "50",
                        centerY = "50",
                        radius = "30",
                        fill = "green",
                        stroke = "yellow",
                        strokeWidth = "10",
                    )
                )
            ),
        )
    }
}
