package fileparser

import VectorSetParserFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import svg.SVGParser
import vectordrawable.VectorDrawableParser
import kotlin.test.assertIs

internal class VectorSetParserFactoryTest {

    @Test
    fun `createFileParser with xml file returns XMLFileParser`() {
        val factory = VectorSetParserFactory
        assertIs<VectorDrawableParser>(factory.create("xml"))
    }

    @Test
    fun `createFileParser with svg file returns SVGFileParser`() {
        val factory = VectorSetParserFactory
        assertIs<SVGParser>(factory.create("svg"))
    }

    @Test
    fun `createFileParser with unsupported file throws IllegalArgumentException`() {
        val factory = VectorSetParserFactory
        assertThrows<IllegalArgumentException> { factory.create("png") }
    }
}
