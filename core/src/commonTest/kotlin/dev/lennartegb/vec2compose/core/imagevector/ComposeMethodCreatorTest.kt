package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.commands.Command
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ComposeMethodCreatorTest {

    private val testVectorSet = VectorSet(
        width = 6,
        height = 12,
        viewportWidth = 24f,
        viewportHeight = 48f,
        groups = emptyList(),
        paths = emptyList(),
    )

    @Test
    fun parse_constructor_returns_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createConstructor(name = "hello", set = testVectorSet)
        assertEquals(
            expected = """
                ImageVector.Builder(
                  name = "hello",
                  defaultWidth = 6.dp,
                  defaultHeight = 12.dp,
                  viewportWidth = 24.0f,
                  viewportHeight = 48.0f
                )
            """.trimIndent(),
            actual = actual
        )
    }

    @Test
    fun parse_path() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createPath(
            forBuilder = false,
            path = VectorSet.Path(
                fillType = VectorSet.Path.FillType.EvenOdd,
                fillColor = VectorSet.Path.FillColor(red = 0xff, green = 0xff, blue = 0xff, alpha = 0xff),
                commands = listOf(
                    Command.MoveTo(2f, 2f, isAbsolute = false),
                    Command.LineTo(4f, 4f, isAbsolute = false),
                    Command.Close
                ),
                alpha = .5f,
                stroke = VectorSet.Path.Stroke(
                    color = null,
                    alpha = .75f,
                    width = 1f,
                    cap = VectorSet.Path.Stroke.Cap.Butt,
                    join = VectorSet.Path.Stroke.Join.Bevel,
                    miter = .25f,
                ),
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                path(
                  fill = SolidColor(Color(0xffffffff)),
                  fillAlpha = 0.5f,
                  stroke = null,
                  strokeAlpha = 0.75f,
                  strokeLineWidth = 1.0f,
                  strokeLineCap = StrokeCap.Butt,
                  strokeLineJoin = StrokeJoin.Bevel,
                  strokeLineMiter = 0.25f,
                  pathFillType = PathFillType.EvenOdd
                ) {
                  moveToRelative(2.0f, 2.0f)
                  lineToRelative(4.0f, 4.0f)
                  close()
                }
            """.trimIndent()
        )
    }

    @Test
    fun parse_path_for_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createPath(
            forBuilder = true,
            path = VectorSet.Path(
                fillType = VectorSet.Path.FillType.EvenOdd,
                fillColor = VectorSet.Path.FillColor(red = 0xff, green = 0xff, blue = 0xff, alpha = 0xff),
                commands = listOf(
                    Command.MoveTo(2f, 2f, isAbsolute = true),
                    Command.LineTo(4f, 4f, isAbsolute = true),
                    Command.Close
                ),
                alpha = .5f,
                stroke = VectorSet.Path.Stroke(
                    color = null,
                    alpha = .75f,
                    width = 1f,
                    cap = VectorSet.Path.Stroke.Cap.Butt,
                    join = VectorSet.Path.Stroke.Join.Bevel,
                    miter = .25f,
                ),
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                .path(
                  fill = SolidColor(Color(0xffffffff)),
                  fillAlpha = 0.5f,
                  stroke = null,
                  strokeAlpha = 0.75f,
                  strokeLineWidth = 1.0f,
                  strokeLineCap = StrokeCap.Butt,
                  strokeLineJoin = StrokeJoin.Bevel,
                  strokeLineMiter = 0.25f,
                  pathFillType = PathFillType.EvenOdd
                ) {
                  moveTo(2.0f, 2.0f)
                  lineTo(4.0f, 4.0f)
                  close()
                }
            """.trimIndent()
        )
    }

    @Test
    fun parse_group() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createGroup(
            forBuilder = false,
            group = VectorSet.Group(
                name = "group",
                groups = emptyList(),
                paths = emptyList(),
                rotate = 0.25f,
                pivot = Translation(x = .15f, y = .20f),
                translation = Translation(x = .25f, y = .30f),
                scale = Scale(x = .5f, y = .6f),
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                group(
                  name = "group",
                  rotate = 0.25f,
                  pivotX = 0.15f,
                  pivotY = 0.2f,
                  scaleX = 0.5f,
                  scaleY = 0.6f,
                  translationX = 0.25f,
                  translationY = 0.3f,
                  clipPathData = emptyList()
                ) {
                }
            """.trimIndent()
        )
    }

    @Test
    fun parse_group_for_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createGroup(
            forBuilder = true,
            group = VectorSet.Group(
                name = "group",
                groups = emptyList(),
                paths = emptyList(),
                rotate = 0.25f,
                pivot = Translation(x = .15f, y = .20f),
                translation = Translation(x = .25f, y = .30f),
                scale = Scale(x = .5f, y = .6f),
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                .group(
                  name = "group",
                  rotate = 0.25f,
                  pivotX = 0.15f,
                  pivotY = 0.2f,
                  scaleX = 0.5f,
                  scaleY = 0.6f,
                  translationX = 0.25f,
                  translationY = 0.3f,
                  clipPathData = emptyList()
                ) {
                }
            """.trimIndent()
        )
    }
}
