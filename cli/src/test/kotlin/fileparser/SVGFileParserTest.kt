package fileparser

import FileParser
import Injection
import imagevector.ImageVectorParser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import utils.FileLoader
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SVGFileParserTest {

    private val fileLoader = FileLoader()
    private val parser = FileParser(vectorSetParser = Injection.SVGParser, imageVectorParser = ImageVectorParser())

    @Test
    fun `parse successfully search_24 svg`() {
        val file = fileLoader.load("svg/search_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content,
                """
                ImageVector.Builder(
                    name = "search_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
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
                content,
                """
                ImageVector.Builder(
                    name = "account_circle_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully check_circle svg`() {
        val file = fileLoader.load("svg/check_circle_48.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content,
                """
                ImageVector.Builder(
                    name = "check_circle_48",
                    defaultWidth = 48.dp,
                    defaultHeight = 48.dp,
                    viewportWidth = 48.0f,
                    viewportHeight = 48.0f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully delete svg`() {
        val file = fileLoader.load("svg/delete_48.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertContains(
                content,
                """
                ImageVector.Builder(
                    name = "delete_48",
                    defaultWidth = 48.dp,
                    defaultHeight = 48.dp,
                    viewportWidth = 48.0f,
                    viewportHeight = 48.0f
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
                content,
                """
                ImageVector.Builder(
                    name = "done_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
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
                content,
                """
                ImageVector.Builder(
                    name = "home_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
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
                content,
                """
                ImageVector.Builder(
                    name = "info_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                """.trimIndent()
            )
        }
    }

    @Test
    fun `parse successfully settings svg with groups`() {
        val file = fileLoader.load("svg/settings_24.svg")
        assertDoesNotThrow {
            val content = parser.parse(file).getOrThrow()
            assertEquals(
                expected = """
                ImageVector.Builder(
                    name = "settings_24",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f
                ).group(
                    rotate = 0.0f,
                    pivotX = 0.0f,
                    pivotY = 0.0f,
                    scaleX = 1.0f,
                    scaleY = 1.0f,
                    translationX = 0.0f,
                    translationY = 0.0f,
                    clipPathData = emptyList()
                ) {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(0.0f, 0.0f)
                        horizontalLineToRelative(24.0f)
                        verticalLineToRelative(24.0f)
                        horizontalLineTo(0.0f)
                        verticalLineTo(0.0f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1.0f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(19.14f, 12.94f)
                        curveToRelative(0.04f, -0.3f, 0.06f, -0.61f, 0.06f, -0.94f)
                        curveToRelative(0.0f, -0.32f, -0.02f, -0.64f, -0.07f, -0.94f)
                        lineToRelative(2.03f, -1.58f)
                        curveToRelative(0.18f, -0.14f, 0.23f, -0.41f, 0.12f, -0.61f)
                        lineToRelative(-1.92f, -3.32f)
                        curveToRelative(-0.12f, -0.22f, -0.37f, -0.29f, -0.59f, -0.22f)
                        lineToRelative(-2.39f, 0.96f)
                        curveToRelative(-0.5f, -0.38f, -1.03f, -0.7f, -1.62f, -0.94f)
                        lineTo(14.4f, 2.81f)
                        curveToRelative(-0.04f, -0.24f, -0.24f, -0.41f, -0.48f, -0.41f)
                        horizontalLineToRelative(-3.84f)
                        curveToRelative(-0.24f, 0.0f, -0.43f, 0.17f, -0.47f, 0.41f)
                        lineTo(9.25f, 5.35f)
                        curveTo(8.66f, 5.59f, 8.12f, 5.92f, 7.63f, 6.29f)
                        lineTo(5.24f, 5.33f)
                        curveToRelative(-0.22f, -0.08f, -0.47f, 0.0f, -0.59f, 0.22f)
                        lineTo(2.74f, 8.87f)
                        curveTo(2.62f, 9.08f, 2.66f, 9.34f, 2.86f, 9.48f)
                        lineToRelative(2.03f, 1.58f)
                        curveTo(4.84f, 11.36f, 4.8f, 11.69f, 4.8f, 12.0f)
                        reflectiveCurveToRelative(0.02f, 0.64f, 0.07f, 0.94f)
                        lineToRelative(-2.03f, 1.58f)
                        curveToRelative(-0.18f, 0.14f, -0.23f, 0.41f, -0.12f, 0.61f)
                        lineToRelative(1.92f, 3.32f)
                        curveToRelative(0.12f, 0.22f, 0.37f, 0.29f, 0.59f, 0.22f)
                        lineToRelative(2.39f, -0.96f)
                        curveToRelative(0.5f, 0.38f, 1.03f, 0.7f, 1.62f, 0.94f)
                        lineToRelative(0.36f, 2.54f)
                        curveToRelative(0.05f, 0.24f, 0.24f, 0.41f, 0.48f, 0.41f)
                        horizontalLineToRelative(3.84f)
                        curveToRelative(0.24f, 0.0f, 0.44f, -0.17f, 0.47f, -0.41f)
                        lineToRelative(0.36f, -2.54f)
                        curveToRelative(0.59f, -0.24f, 1.13f, -0.56f, 1.62f, -0.94f)
                        lineToRelative(2.39f, 0.96f)
                        curveToRelative(0.22f, 0.08f, 0.47f, 0.0f, 0.59f, -0.22f)
                        lineToRelative(1.92f, -3.32f)
                        curveToRelative(0.12f, -0.22f, 0.07f, -0.47f, -0.12f, -0.61f)
                        lineTo(19.14f, 12.94f)
                        close()
                        moveTo(12.0f, 15.6f)
                        curveToRelative(-1.98f, 0.0f, -3.6f, -1.62f, -3.6f, -3.6f)
                        reflectiveCurveToRelative(1.62f, -3.6f, 3.6f, -3.6f)
                        reflectiveCurveToRelative(3.6f, 1.62f, 3.6f, 3.6f)
                        reflectiveCurveTo(13.98f, 15.6f, 12.0f, 15.6f)
                        close()
                    }
                }
                .build()
                """.trimIndent(),
                actual = content
            )
        }
    }

    @Test
    fun `parse successfully party_cloudy_night_24`() {
        val file = fileLoader.load("svg/partly_cloudy_night_24.svg")
        val result = parser.parse(file = file)
        assertContains(
            result.getOrThrow(),
            """
                ImageVector.Builder(
                    name = "partly_cloudy_night_24",
                    defaultWidth = 48.dp,
                    defaultHeight = 48.dp,
                    viewportWidth = 48.0f,
                    viewportHeight = 48.0f
                )
            """.trimIndent()
        )
    }
}
