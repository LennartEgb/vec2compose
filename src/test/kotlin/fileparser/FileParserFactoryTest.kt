package fileparser

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertIs

internal class FileParserFactoryTest {

    @Test
    fun `createFileParser with xml file returns XMLFileParser`() {
        assertIs<XMLFileParser>(FileParserFactory.createFileParser(File("file.xml")))
    }

    @Test
    fun `createFileParser with svg file returns SVGFileParser`() {
        assertIs<SVGFileParser>(FileParserFactory.createFileParser(File("file.svg")))
    }

    @Test
    fun `createFileParser with unsupported file throws IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            FileParserFactory.createFileParser(File("file.jpeg"))
        }
    }
}