package imagevector

import VectorSet
import commands.Command
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ImageVectorParserTest {

    private val imageVectorParser = ImageVectorParser()

    @Test
    fun parse_VectorSet_with_FillType_NonZero_to_ImageVector_string() {
        val set = VectorSet(
            width = 24,
            height = 24,
            viewportWidth = 48f,
            viewportHeight = 42f,
            paths = listOf(
                VectorSet.Path(
                    fillType = VectorSet.Path.FillType.NonZero,
                    commands = listOf(
                        Command.MoveTo(x = 1f, y = 2f, isAbsolute = true),
                        Command.LineTo(x = 2f, y = 2f, isAbsolute = true),
                        Command.LineTo(x = 1f, y = 2f, isAbsolute = false),
                        Command.Close
                    ),
                    alpha = 1f
                )
            ),
            groups = emptyList()
        )

        val expected = """
            ImageVector.Builder(
                name = "ic_icon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 48.0f,
                viewportHeight = 42.0f
            ).path(
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
                moveTo(1.0f, 2.0f)
                lineTo(2.0f, 2.0f)
                lineToRelative(1.0f, 2.0f)
                close()
            }.build()
        """.trimIndent()
        assertEquals(
            expected = expected,
            actual = imageVectorParser.parse(name = "ic_icon", vectorSet = set)
        )
    }

    @Test
    fun parse_VectorSet_with_FillType_EvenOdd_to_ImageVector_string() {
        val set = VectorSet(
            width = 24,
            height = 24,
            viewportWidth = 48f,
            viewportHeight = 42f,
            paths = listOf(
                VectorSet.Path(
                    fillType = VectorSet.Path.FillType.EvenOdd,
                    commands = listOf(
                        Command.MoveTo(x = 1f, y = 2f, isAbsolute = true),
                        Command.LineTo(x = 2f, y = 2f, isAbsolute = true),
                        Command.LineTo(x = 1f, y = 2f, isAbsolute = false),
                        Command.Close
                    ),
                    alpha = .5f
                )
            ),
            groups = emptyList()
        )

        val expected = """
            ImageVector.Builder(
                name = "ic_icon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 48.0f,
                viewportHeight = 42.0f
            ).path(
                fill = SolidColor(Color.Black),
                fillAlpha = 0.5f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Bevel,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(1.0f, 2.0f)
                lineTo(2.0f, 2.0f)
                lineToRelative(1.0f, 2.0f)
                close()
            }.build()
        """.trimIndent()
        assertEquals(
            expected = expected,
            actual = imageVectorParser.parse(name = "ic_icon", vectorSet = set)
        )
    }
}
