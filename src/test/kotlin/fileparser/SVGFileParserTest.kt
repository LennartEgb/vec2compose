package fileparser

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
        val file = fileLoader.load("svg/search_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
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

    @Test
    fun `parse successfully account_circle svg`() {
        val file = fileLoader.load("svg/account_circle_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "account_circle_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully check_circle svg`() {
        val file = fileLoader.load("svg/check_circle_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "check_circle_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully delete svg`() {
        val file = fileLoader.load("svg/delete_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "delete_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully done svg`() {
        val file = fileLoader.load("svg/done_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "done_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully home svg`() {
        val file = fileLoader.load("svg/home_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "home_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully info svg`() {
        val file = fileLoader.load("svg/info_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "info_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully settings svg`() {
        val file = fileLoader.load("svg/settings_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content, """
                ImageVector.Builder(
                    name = "settings_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                """.trimIndent()
            )
        }
    }
}