package fileparser

import VectorSetParserFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import svg.SVGParser
import vectordrawable.VectorDrawableParser
import java.io.File
import kotlin.test.assertIs

internal class VectorSetParserFactoryTest {

    @Test
    fun `createFileParser with xml file returns XMLFileParser`() {
        assertIs<VectorDrawableParser>(VectorSetParserFactory.createVectorSetParser(File("file.xml")))
    }

    @Test
    fun `createFileParser with svg file returns SVGFileParser`() {
        assertIs<SVGParser>(VectorSetParserFactory.createVectorSetParser(File("file.svg")))
    }

    @Test
    fun `createFileParser with unsupported file throws IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            VectorSetParserFactory.createVectorSetParser(File("file.jpeg"))
        }
    }
}