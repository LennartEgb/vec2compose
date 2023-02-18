package svg

import org.junit.jupiter.api.Test
import utils.TestObjectMapper
import kotlin.test.assertEquals

internal class SVGDeserializerTest {

    private val deserializer = SVGDeserializer(TestObjectMapper)

    @Test
    fun `deserialize valid SVG file`() {
        val content = """
            <svg xmlns="http://www.w3.org/2000/svg"
                height="24px"
                viewBox="0 0 24 24"
                width="24px"
                fill="#000000">
                <path d="M0 0h24v24H0z" fill="none"/>
                <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
            </svg>
        """.trimIndent()

        val expectedPaths = listOf(
            SVG.Path(fillRule = "nonzero", pathData = "M0 0h24v24H0z", fill = "none"),
            SVG.Path(
                fillRule = "nonzero",
                pathData = "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"
            )
        )
        val expected = SVG(
            width = "24px",
            height = "24px",
            viewBox = "0 0 24 24",
            fill = "#000000",
            path = expectedPaths,
            g = emptyList()
        )

        assertEquals(
            expected = Result.success(expected),
            actual = deserializer.deserialize(content)
        )
    }

    @Test
    fun `deserialize valid SVG file with fill-rule evenodd`() {
        val content = """
            <svg xmlns="http://www.w3.org/2000/svg"
                height="24px"
                viewBox="0 0 24 24"
                width="24px"
                fill="#000000">
                <path d="M0 0h24v24H0z" fill="none" fill-rule="evenodd"/>
                <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
            </svg>
        """.trimIndent()

        val expectedPaths = listOf(
            SVG.Path(fillRule = "evenodd", pathData = "M0 0h24v24H0z", fill = "none"),
            SVG.Path(
                fillRule = "nonzero",
                pathData = "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"
            )
        )
        val expected = SVG(
            width = "24px",
            height = "24px",
            viewBox = "0 0 24 24",
            fill = "#000000",
            path = expectedPaths,
            g = emptyList()
        )

        assertEquals(
            expected = Result.success(expected),
            actual = deserializer.deserialize(content)
        )
    }

    @Test
    fun `parse SVG with groups`() {
        // NOTE: SVG found here: https://www.sarasoueidan.com/blog/structuring-grouping-referencing-in-svg/
        val content = """
            <svg width="1144.12px" height="400px" viewBox="0 0 572.06 200">
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
            expected = SVG(
                width = "1144.12px",
                height = "400px",
                viewBox = "0 0 572.06 200",
                path = emptyList(),
                g = listOf(
                    SVG.Group(
                        name = "bird",
                        path = listOf(),
                        g = listOf(
                            SVG.Group(name = "body", path = listOf(SVG.Path(id = "first", pathData = "M48.42,78.11"))),
                            SVG.Group(name = "head", path = listOf(SVG.Path(id = "second", pathData = "M48.42,78.11")))
                        )
                    )
                )
            ),
            actual = deserializer.deserialize(content).getOrThrow()
        )
    }
}
