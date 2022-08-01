package parser

import Injection
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import utils.FileLoader
import kotlin.test.assertContains

class SVGFileParserTest {

    private val fileLoader = FileLoader()
    private val parser = Injection.SVGFileParser
    @Test
    fun `parse successfully search_24 svg`() {
        val file = fileLoader.load("search_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "search_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }
}