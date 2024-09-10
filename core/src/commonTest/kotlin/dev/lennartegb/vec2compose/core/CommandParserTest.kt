package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Command
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommandParserTest {

    @Test
    fun `parse Move command`() {
        val movePath = "M27.05,24.55"
        assertEquals(
            expected = listOf(Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true)),
            actual = parseCommand(movePath)
        )
    }

    // @see https://www.w3.org/TR/SVG2/paths.html#PathDataMovetoCommands
    @Test
    fun `parse multiple Move command`() {
        val movePath = "M27.05,24.55 27.05,24.55"
        assertEquals(
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                Command.LineTo(x = 27.05f, y = 24.55f, isAbsolute = true)
            ),
            actual = parseCommand(movePath)
        )
    }

    @Test
    fun `parse LineTo path`() {
        val linePath = "L12.15,19.65"
        assertEquals(
            expected = listOf(Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true)),
            actual = parseCommand(linePath)
        )
    }

    @Test
    fun `parse multiple LineTo path`() {
        val linePath = "L12.15,19.65 12.15,19.65"
        assertEquals(
            expected = listOf(
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true)
            ),
            actual = parseCommand(linePath)
        )
    }

    @Test
    fun `parse CurveTo path`() {
        val curveToPath = "C11.85,19.35 11.483,19.2 11.05,19.2"
        assertEquals(
            expected = listOf(
                Command.CurveTo(
                    x1 = 11.85f,
                    y1 = 19.35f,
                    x2 = 11.483f,
                    y2 = 19.2f,
                    x3 = 11.05f,
                    y3 = 19.2f,
                    isAbsolute = true
                )
            ),
            actual = parseCommand(curveToPath)
        )
    }

    @Test
    fun `parse Close path`() {
        val closePath = "Z"
        assertEquals(
            expected = listOf(Command.Close),
            actual = parseCommand(closePath)
        )
    }

    @Test
    fun `parse Close path relative with lowercase z`() {
        val closePath = "z"
        assertEquals(
            expected = listOf(Command.Close),
            actual = parseCommand(closePath)
        )
    }

    @Test
    fun `parse HorizontalLineTo path`() {
        val horizontalLineToPath = "H20.05"
        assertEquals(
            expected = listOf(Command.HorizontalLineTo(x = 20.05f, isAbsolute = true)),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse multiple HorizontalLineTo path`() {
        val horizontalLineToPath = "H20.05 20.05"
        assertEquals(
            expected = listOf(
                Command.HorizontalLineTo(x = 20.05f, isAbsolute = true),
                Command.HorizontalLineTo(x = 20.05f, isAbsolute = true)
            ),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse HorizontalLineTo path with relative command`() {
        val horizontalLineToPath = "h20.05"
        assertEquals(
            expected = listOf(Command.HorizontalLineTo(x = 20.05f, isAbsolute = false)),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse VerticalLineTo path`() {
        val horizontalLineToPath = "V20.05"
        assertEquals(
            expected = listOf(Command.VerticalLineTo(y = 20.05f, isAbsolute = true)),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse multiple VerticalLineTo path`() {
        val horizontalLineToPath = "V20.05 20.05"
        assertEquals(
            expected = listOf(
                Command.VerticalLineTo(y = 20.05f, isAbsolute = true),
                Command.VerticalLineTo(y = 20.05f, isAbsolute = true)
            ),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse ReflectiveCurveTo path`() {
        val horizontalLineToPath = "s4.47,10 9.99,10"
        assertEquals(
            expected = listOf(
                Command.ReflectiveCurveTo(
                    x1 = 4.47f,
                    y1 = 10f,
                    x2 = 9.99f,
                    y2 = 10f,
                    isAbsolute = false
                )
            ),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse quadratic bezier path`() {
        val quadraticBezierToPath = "Q400,50 600,300"
        assertEquals(
            expected = listOf(Command.QuadraticBezierTo(400f, 50f, 600f, 300f, isAbsolute = true)),
            actual = parseCommand(quadraticBezierToPath)
        )
    }

    @Test
    fun `parse relative quadratic bezier path`() {
        val quadraticBezierToPath = "q400,50 600,300"
        assertEquals(
            expected = listOf(Command.QuadraticBezierTo(400f, 50f, 600f, 300f, isAbsolute = false)),
            actual = parseCommand(quadraticBezierToPath)
        )
    }

    @Test
    fun `parse reflective quadratic bezier path`() {
        val quadraticBezierToPath = "T1000,300"
        assertEquals(
            expected = listOf(Command.ReflectiveQuadraticBezierTo(1000f, 300f, isAbsolute = true)),
            actual = parseCommand(quadraticBezierToPath)
        )
    }

    @Test
    fun `parse relative reflective quadratic bezier path`() {
        val quadraticBezierToPath = "t1000,300"
        assertEquals(
            expected = listOf(Command.ReflectiveQuadraticBezierTo(1000f, 300f, isAbsolute = false)),
            actual = parseCommand(quadraticBezierToPath)
        )
    }

    @Test
    fun `parse arc path`() {
        val arcPath = "A25,25 -30 0,1 50,-25"
        assertEquals(
            expected = listOf(
                ArcTo(
                    horizontalEllipseRadius = 25f,
                    verticalEllipseRadius = 25f,
                    theta = -30f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 50f,
                    y1 = -25f,
                    isAbsolute = true
                )
            ),
            actual = parseCommand(arcPath)
        )
    }

    @Test
    fun `parse relative arc path`() {
        val arcPath = "a25,25 -30 0,1 50,-25"
        assertEquals(
            expected = listOf(
                ArcTo(
                    horizontalEllipseRadius = 25f,
                    verticalEllipseRadius = 25f,
                    theta = -30f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 50f,
                    y1 = -25f,
                    isAbsolute = false
                )
            ),
            actual = parseCommand(arcPath)
        )
    }
}
