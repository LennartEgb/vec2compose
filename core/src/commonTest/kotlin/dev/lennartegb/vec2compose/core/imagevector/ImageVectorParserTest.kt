package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ImageVectorParserTest {

    private val imageVectorParser = ImageVectorCreator(indentation = "    ")

    @Test
    fun `parse ImageVector with FillType NonZero to ImageVector string`() {
        val set = ImageVector(
            width = 24,
            height = 24,
            viewportWidth = 48f,
            viewportHeight = 42f,
            nodes = listOf(
                ImageVector.Path(
                    fillType = ImageVector.Path.FillType.NonZero,
                    commands = listOf(
                        MoveTo(x = 1f, y = 2f, isAbsolute = true),
                        LineTo(x = 2f, y = 2f, isAbsolute = true),
                        LineTo(x = 1f, y = 2f, isAbsolute = false),
                        Close
                    ),
                    alpha = 1f
                )
            )
        )

        val expected = """
            ImageVector.Builder(
                name = "ic_icon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 48.0f,
                viewportHeight = 42.0f
            ).path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Bevel,
                strokeLineMiter = 1.0f,
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
            actual = imageVectorParser.create(name = "ic_icon", imageVector = set)
        )
    }

    @Test
    fun `parse ImageVector with FillType EvenOdd to ImageVector string`() {
        val set = ImageVector(
            width = 24,
            height = 24,
            viewportWidth = 48f,
            viewportHeight = 42f,
            nodes = listOf(
                ImageVector.Path(
                    fillType = ImageVector.Path.FillType.EvenOdd,
                    commands = listOf(
                        MoveTo(x = 1f, y = 2f, isAbsolute = true),
                        LineTo(x = 2f, y = 2f, isAbsolute = true),
                        LineTo(x = 1f, y = 2f, isAbsolute = false),
                        Close
                    ),
                    alpha = .5f
                )
            )
        )

        val expected = """
            ImageVector.Builder(
                name = "ic_icon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 48.0f,
                viewportHeight = 42.0f
            ).path(
                fill = null,
                fillAlpha = 0.5f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Bevel,
                strokeLineMiter = 1.0f,
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
            actual = imageVectorParser.create(name = "ic_icon", imageVector = set)
        )
    }
}
