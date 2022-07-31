package parser

import Injection
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import utils.FileLoader
import kotlin.test.assertContains

internal class XMLFileParserTest {

    private val fileLoader = FileLoader()
    private val parser = Injection.XMLFileParser

    @Test
    fun `parse successfully ic_account_circle_24`() {
        val file = fileLoader.load("ic_account_circle_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_account_circle_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_check_circle_24`() {
        val file = fileLoader.load("ic_check_circle_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_check_circle_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_delete_24`() {
        val file = fileLoader.load("ic_delete_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_delete_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_done_24`() {
        val file = fileLoader.load("ic_done_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_done_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_home_24`() {
        val file = fileLoader.load("ic_home_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_home_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_info_24`() {
        val file = fileLoader.load("ic_info_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_info_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_language_24`() {
        val file = fileLoader.load("ic_language_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_language_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_search_24`() {
        val file = fileLoader.load("ic_search_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_search_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully ic_settings_24`() {
        val file = fileLoader.load("ic_settings_24.xml")
        assertDoesNotThrow {
            val content = parser.parse(file)
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "ic_settings_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24,
                    viewportHeight = 24
                """.trimIndent()
            )
        }
    }
}