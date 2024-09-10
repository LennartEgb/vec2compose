package dev.lennartegb.vec2compose.core.commands

import dev.lennartegb.vec2compose.core.CommandParser
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommandParserTest {

    @Test
    fun parse_Move_command() {
        val movePath = "M27.05,24.55"
        assertEquals(
            expected = listOf(Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true)),
            actual = CommandParser.parse(movePath)
        )
    }

    // @see https://www.w3.org/TR/SVG2/paths.html#PathDataMovetoCommands
    @Test
    fun parse_multiple_Move_command() {
        val movePath = "M27.05,24.55 27.05,24.55"
        assertEquals(
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                Command.LineTo(x = 27.05f, y = 24.55f, isAbsolute = true)
            ),
            actual = CommandParser.parse(movePath)
        )
    }

    @Test
    fun parse_LineTo_path() {
        val linePath = "L12.15,19.65"
        assertEquals(
            expected = listOf(Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true)),
            actual = CommandParser.parse(linePath)
        )
    }

    @Test
    fun parse_multiple_LineTo_path() {
        val linePath = "L12.15,19.65 12.15,19.65"
        assertEquals(
            expected = listOf(
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true)
            ),
            actual = CommandParser.parse(linePath)
        )
    }

    @Test
    fun parse_CurveTo_path() {
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
            actual = CommandParser.parse(curveToPath)
        )
    }

    @Test
    fun parse_Close_path() {
        val closePath = "Z"
        assertEquals(
            expected = listOf(Command.Close),
            actual = CommandParser.parse(closePath)
        )
    }

    @Test
    fun parse_Close_path_relative_with_lowercase_z() {
        val closePath = "z"
        assertEquals(
            expected = listOf(Command.Close),
            actual = CommandParser.parse(closePath)
        )
    }

    @Test
    fun parse_HorizontalLineTo_path() {
        val horizontalLineToPath = "H20.05"
        assertEquals(
            expected = listOf(Command.HorizontalLineTo(x = 20.05f, isAbsolute = true)),
            actual = CommandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun parse_multiple_HorizontalLineTo_path() {
        val horizontalLineToPath = "H20.05 20.05"
        assertEquals(
            expected = listOf(
                Command.HorizontalLineTo(x = 20.05f, isAbsolute = true),
                Command.HorizontalLineTo(x = 20.05f, isAbsolute = true)
            ),
            actual = CommandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun parse_HorizontalLineTo_path_with_relative_command() {
        val horizontalLineToPath = "h20.05"
        assertEquals(
            expected = listOf(Command.HorizontalLineTo(x = 20.05f, isAbsolute = false)),
            actual = CommandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun parse_VerticalLineTo_path() {
        val horizontalLineToPath = "V20.05"
        assertEquals(
            expected = listOf(Command.VerticalLineTo(y = 20.05f, isAbsolute = true)),
            actual = CommandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun parse_multiple_VerticalLineTo_path() {
        val horizontalLineToPath = "V20.05 20.05"
        assertEquals(
            expected = listOf(
                Command.VerticalLineTo(y = 20.05f, isAbsolute = true),
                Command.VerticalLineTo(y = 20.05f, isAbsolute = true)
            ),
            actual = CommandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun parse_ReflectiveCurveTo_path() {
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
            actual = CommandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun parse_quadratic_bezier_path() {
        val quadraticBezierToPath = "Q400,50 600,300"
        assertEquals(
            expected = listOf(Command.QuadraticBezierTo(400f, 50f, 600f, 300f, isAbsolute = true)),
            actual = CommandParser.parse(quadraticBezierToPath)
        )
    }

    @Test
    fun parse_relative_quadratic_bezier_path() {
        val quadraticBezierToPath = "q400,50 600,300"
        assertEquals(
            expected = listOf(Command.QuadraticBezierTo(400f, 50f, 600f, 300f, isAbsolute = false)),
            actual = CommandParser.parse(quadraticBezierToPath)
        )
    }

    @Test
    fun parse_reflective_quadratic_bezier_path() {
        val quadraticBezierToPath = "T1000,300"
        assertEquals(
            expected = listOf(Command.ReflectiveQuadraticBezierTo(1000f, 300f, isAbsolute = true)),
            actual = CommandParser.parse(quadraticBezierToPath)
        )
    }

    @Test
    fun parse_relative_reflective_quadratic_bezier_path() {
        val quadraticBezierToPath = "t1000,300"
        assertEquals(
            expected = listOf(Command.ReflectiveQuadraticBezierTo(1000f, 300f, isAbsolute = false)),
            actual = CommandParser.parse(quadraticBezierToPath)
        )
    }

    @Test
    fun parse_arc_path() {
        val arcPath = "A25,25 -30 0,1 50,-25"
        assertEquals(
            expected = listOf(
                Command.ArcTo(
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
            actual = CommandParser.parse(arcPath)
        )
    }

    @Test
    fun parse_relative_arc_path() {
        val arcPath = "a25,25 -30 0,1 50,-25"
        assertEquals(
            expected = listOf(
                Command.ArcTo(
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
            actual = CommandParser.parse(arcPath)
        )
    }
}
