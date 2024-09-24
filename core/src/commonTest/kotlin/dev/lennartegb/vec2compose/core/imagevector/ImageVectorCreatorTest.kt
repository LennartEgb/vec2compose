package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ImageVectorCreatorTest {

    private val imageVectorCreator = ImageVectorCreator(indentation = "    ")

    @Test
    fun parse_ImageVector_with_FillType_NonZero_to_ImageVector_string() {
        val set = ImageVector(
            width = 24,
            height = 24,
            viewportWidth = 48f,
            viewportHeight = 42f,
            nodes = listOf(
                ImageVector.Path(
                    fillType = ImageVector.Path.FillType.NonZero,
                    commands = listOf(
                        MoveTo(x = 1f, y = 2f),
                        LineTo(x = 2f, y = 2f),
                        LineTo(x = 1f, y = 2f),
                        Close()
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
                lineTo(1.0f, 2.0f)
                close()
            }.build()
        """.trimIndent()
        assertEquals(
            expected = expected,
            actual = imageVectorCreator.create(name = "ic_icon", imageVector = set)
        )
    }

    @Test
    fun parse_ImageVector_with_FillType_EvenOdd_to_ImageVector_string() {
        val set = ImageVector(
            width = 24,
            height = 24,
            viewportWidth = 48f,
            viewportHeight = 42f,
            nodes = listOf(
                ImageVector.Path(
                    fillType = ImageVector.Path.FillType.EvenOdd,
                    commands = listOf(
                        MoveTo(x = 1f, y = 2f),
                        LineTo(x = 2f, y = 2f),
                        LineTo(x = 1f, y = 2f),
                        Close()
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
                lineTo(1.0f, 2.0f)
                close()
            }.build()
        """.trimIndent()
        assertEquals(
            expected = expected,
            actual = imageVectorCreator.create(name = "ic_icon", imageVector = set)
        )
    }

    @Test
    fun parse_ImageVector_with_Group_to_ImageVector_string() {
        val imageVector = ImageVector(
            width = 24,
            height = 25,
            viewportWidth = 26f,
            viewportHeight = 27f,
            nodes = listOf(
                ImageVector.Group(
                    name = null,
                    rotate = 0f,
                    pivot = Translation(0f, 0f),
                    translation = Translation(0f, 0f),
                    scale = Scale(1f, 1f),
                    nodes = emptyList()
                )
            )
        )

        assertEquals(
            actual = imageVectorCreator.create(name = "icon", imageVector = imageVector),
            expected = """
            ImageVector.Builder(
                name = "icon",
                defaultWidth = 24.dp,
                defaultHeight = 25.dp,
                viewportWidth = 26.0f,
                viewportHeight = 27.0f
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
            }.build()
            """.trimIndent()
        )
    }
}
