package fileparser

import dev.lennartegb.vec2compose.core.VectorSetParserFactory
import svg.SVGParser
import vectordrawable.VectorDrawableParser
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

internal class VectorSetParserFactoryTest {

    @Test
    fun createFileParser_with_xml_file_returns_XMLFileParser() {
        val factory = VectorSetParserFactory
        assertIs<VectorDrawableParser>(factory.create("xml"))
    }

    @Test
    fun createFileParser_with_svg_file_returns_SVGFileParser() {
        val factory = VectorSetParserFactory
        assertIs<SVGParser>(factory.create("svg"))
    }

    @Test
    fun createFileParser_with_unsupported_file_throws_IllegalArgumentException() {
        val factory = VectorSetParserFactory
        assertFailsWith<IllegalArgumentException> { factory.create("png") }
    }
}
