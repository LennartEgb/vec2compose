package parser

import androidvector.AndroidVectorParser
import androidvector.AndroidVectorSerializer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import utils.FileLoader

internal class XMLFileParserTest {

    private val fileLoader = FileLoader()
    private val serializer = AndroidVectorSerializer()
    private val pathParser = PathParser()
    private val androidVectorParser = AndroidVectorParser(serializer = serializer, pathParser = pathParser)
    private val imageVectorParser = ImageVectorParser()
    private val parser = XMLFileParser(androidVectorParser, imageVectorParser)

    @Test
    fun `parse successfully ic_account_circle_24`() {
        val accountCircle = fileLoader.load("ic_account_circle_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_check_circle_24`() {
        val accountCircle = fileLoader.load("ic_check_circle_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_delete_24`() {
        val accountCircle = fileLoader.load("ic_delete_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_done_24`() {
        val accountCircle = fileLoader.load("ic_done_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_home_24`() {
        val accountCircle = fileLoader.load("ic_home_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_info_24`() {
        val accountCircle = fileLoader.load("ic_info_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_language_24`() {
        val accountCircle = fileLoader.load("ic_language_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_search_24`() {
        val accountCircle = fileLoader.load("ic_search_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }

    @Test
    fun `parse successfully ic_settings_24`() {
        val accountCircle = fileLoader.load("ic_settings_24.xml")
        assertDoesNotThrow { parser.parse(accountCircle) }
    }
}