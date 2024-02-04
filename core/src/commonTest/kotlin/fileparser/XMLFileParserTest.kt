package fileparser

import FileParser
import Injection
import imagevector.ImageVectorParser
import utils.FileLoader
import kotlin.test.Test
import kotlin.test.assertContains

internal class XMLFileParserTest {

    private val fileLoader = FileLoader()
    private val parser = FileParser(
        vectorSetParser = Injection.VectorDrawableParser,
        imageVectorParser = ImageVectorParser(indentation = "    ")
    )

    @Test
    fun parse_successfully_ic_account_circle_24() {
        val file = fileLoader.load("xml/ic_account_circle_24.xml")
        val content = parser.parse(content = file, name = "ic_account_circle_24").getOrThrow()
        assertContains(
            content,
            """
            ImageVector.Builder(
                name = "ic_account_circle_24",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f
            """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_check_circle_24() {
        val file = fileLoader.load("xml/ic_check_circle_24.xml")
        val content = parser.parse(content = file, name = "ic_check_circle_24").getOrThrow()
        assertContains(
            content,
            """
            ImageVector.Builder(
                name = "ic_check_circle_24",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f
            """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_delete_24() {
        val file = fileLoader.load("xml/ic_delete_24.xml")
        val content = parser.parse(content = file, name = "ic_delete_24").getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_delete_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_done_24() {
        val file = fileLoader.load("xml/ic_done_24.xml")
        val content = parser.parse(content = file, name = "ic_done_24").getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_done_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_home_24() {
        val file = fileLoader.load("xml/ic_home_24.xml")
        val content = parser.parse(name = "ic_home_24", content = file).getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_home_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_info_24() {
        val file = fileLoader.load("xml/ic_info_24.xml")
        val content = parser.parse(content = file, name = "ic_info_24").getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_info_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_language_24() {
        val file = fileLoader.load("xml/ic_language_24.xml")
        val content = parser.parse(content = file, name = "ic_language_24").getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_language_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_search_24() {
        val file = fileLoader.load("xml/ic_search_24.xml")
        val content = parser.parse(content = file, name = "ic_search_24").getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_search_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }

    @Test
    fun parse_successfully_ic_settings_24() {
        val file = fileLoader.load("xml/ic_settings_24.xml")
        val content = parser.parse(content = file, name = "ic_settings_24").getOrThrow()
        assertContains(
            content,
            """
                ImageVector.Builder(
                    name = "ic_settings_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
        )
    }
}
