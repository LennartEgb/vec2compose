package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * TODO: Use other float values to test rounding issues
 */
internal class ComposeMethodCreatorTest {

    private val testImageVector = ImageVector(
        width = 6,
        height = 12,
        viewportWidth = 24f,
        viewportHeight = 48f,
        nodes = emptyList()
    )

    @Test
    fun parse_constructor_returns_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createConstructor(name = "hello", set = testImageVector)
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
        val actual = creator.createNode(
            node = ImageVector.Path(
                fillType = ImageVector.Path.FillType.EvenOdd,
                fillColor = ImageVector.Path.FillColor(
                    red = 0xff,
                    green = 0xff,
                    blue = 0xff,
                    alpha = 0xff
                ),
                commands = listOf(
                    MoveTo(2f, 2f),
                    LineTo(4f, 4f),
                    Close()
                ),
                alpha = .5f,
                stroke = ImageVector.Path.Stroke(
                    color = null,
                    alpha = .75f,
                    width = 1f,
                    cap = ImageVector.Path.Stroke.Cap.Butt,
                    join = ImageVector.Path.Stroke.Join.Bevel,
                    miter = .25f
                )
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
                  moveTo(2.0f, 2.0f)
                  lineTo(4.0f, 4.0f)
                  close()
                }
            """.trimIndent()
        )
    }

    @Test
    fun parse_path_for_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createNode(
            node = ImageVector.Path(
                fillType = ImageVector.Path.FillType.EvenOdd,
                fillColor = null,
                commands = listOf(
                    MoveTo(2f, 2f),
                    LineTo(4f, 4f),
                    Close()
                ),
                alpha = .5f,
                stroke = ImageVector.Path.Stroke(
                    color = null,
                    alpha = .75f,
                    width = 1f,
                    cap = ImageVector.Path.Stroke.Cap.Butt,
                    join = ImageVector.Path.Stroke.Join.Bevel,
                    miter = .25f
                )
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                path(
                  fill = null,
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
    fun parse_group_with_path() {
        val creator = ComposeMethodCreator(indentation = " ".repeat(4))
        val actual = creator.createNode(
            node = ImageVector.Group(
                name = "group",
                nodes = listOf(
                    ImageVector.Path(
                        fillType = ImageVector.Path.FillType.EvenOdd,
                        fillColor = ImageVector.Path.FillColor(
                            red = 0xff,
                            green = 0xff,
                            blue = 0xff,
                            alpha = 0xff
                        ),
                        commands = emptyList(),
                        alpha = .5f,
                        stroke = ImageVector.Path.Stroke(
                            color = null,
                            alpha = .75f,
                            width = 1f,
                            cap = ImageVector.Path.Stroke.Cap.Square,
                            join = ImageVector.Path.Stroke.Join.Bevel,
                            miter = .25f
                        )
                    )
                ),
                rotate = 0.25f,
                pivot = Translation(x = 0f, y = .25f),
                translation = Translation(x = .5f, y = .75f),
                scale = Scale(x = 1f, y = .25f)
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                group(
                    name = "group",
                    rotate = 0.25f,
                    pivotX = 0.0f,
                    pivotY = 0.25f,
                    scaleX = 1.0f,
                    scaleY = 0.25f,
                    translationX = 0.5f,
                    translationY = 0.75f,
                    clipPathData = emptyList()
                ) {
                    path(
                        fill = SolidColor(Color(0xffffffff)),
                        fillAlpha = 0.5f,
                        stroke = null,
                        strokeAlpha = 0.75f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Square,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 0.25f,
                        pathFillType = PathFillType.EvenOdd
                    ) {
                    }
                }
            """.trimIndent()
        )
    }

    @Test
    fun parse_group_for_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.createNode(
            node = ImageVector.Group(
                name = "group",
                nodes = emptyList(),
                rotate = 0.25f,
                pivot = Translation(x = .0f, y = .25f),
                translation = Translation(x = .5f, y = .75f),
                scale = Scale(x = 1f, y = .75f)
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                group(
                  name = "group",
                  rotate = 0.25f,
                  pivotX = 0.0f,
                  pivotY = 0.25f,
                  scaleX = 1.0f,
                  scaleY = 0.75f,
                  translationX = 0.5f,
                  translationY = 0.75f,
                  clipPathData = emptyList()
                ) {
                }
            """.trimIndent()
        )
    }
}
