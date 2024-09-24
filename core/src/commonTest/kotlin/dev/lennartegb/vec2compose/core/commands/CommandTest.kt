package dev.lennartegb.vec2compose.core.commands

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CommandTest {

    @Test
    fun parse_invalid_command_throws_IllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            Command("ø")
        }
    }

    @Test
    fun parse_valid_command_with_invalid_parameters_throws_IllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            Command("M1")
        }
    }

    @Test
    fun parse_arc_path() {
        val command = Command("A25,25 -30 0,1 50,-25")
        assertEquals(
            actual = command.toString(),
            expected = "arcTo(25f, 25f, -30f, false, true, 50f, -25f)"
        )
    }

    @Test
    fun parse_relative_arc_path() {
        val command = Command("a25,25 -30 0,1 50,-25")
        assertEquals(
            actual = command.toString(),
            expected = "arcToRelative(25f, 25f, -30f, false, true, 50f, -25f)"
        )
    }

    @Test
    fun close_command() {
        val command = Command("z")
        assertEquals(expected = "close()", actual = command.toString())
    }

    @Test
    fun close_command_uppercase() {
        val command = Command("Z")
        assertEquals(expected = "close()", actual = command.toString())
    }

    @Test
    fun curveTo_path() {
        val command = Command("C11.85,19.35 11.483,19.2 11.05,19.2")
        assertEquals(
            actual = command.toString(),
            expected = "curveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f)"
        )
    }

    @Test
    fun curveToRelative_path() {
        val command = Command("c11.85,19.35 11.483,19.2 11.05,19.2")
        assertEquals(
            actual = command.toString(),
            expected = "curveToRelative(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f)"
        )
    }

    @Test
    fun parse_lineTo_path() {
        assertEquals(
            actual = Command("L2.03-1.58").toString(),
            expected = "lineTo(2.03f, -1.58f)"
        )
    }

    @Test
    fun parse_lineToRelative_path() {
        assertEquals(
            actual = Command("l2.03-1.58").toString(),
            expected = "lineToRelative(2.03f, -1.58f)"
        )
    }

    @Test
    fun parse_HorizontalLineTo_path() {
        assertEquals(
            actual = Command("H20.05").toString(),
            expected = "horizontalLineTo(20.05f)"
        )
    }

    @Test
    fun parse_multiple_HorizontalLineTo_path() {
        assertEquals(
            actual = Command("H20.05 20.05").toString(),
            expected = "horizontalLineTo(20.05f)\nhorizontalLineTo(20.05f)"
        )
    }

    @Test
    fun parse_HorizontalLineTo_path_with_relative_command() {
        assertEquals(
            actual = Command("h20.05").toString(),
            expected = "horizontalLineToRelative(20.05f)"
        )
    }

    @Test
    fun parse_VerticalLineTo_path() {
        assertEquals(
            actual = Command("V20.05").toString(),
            expected = "verticalLineTo(20.05f)"
        )
    }

    @Test
    fun parse_multiple_VerticalLineTo_path() {
        assertEquals(
            actual = Command("V20.05 20.05").toString(),
            expected = "verticalLineTo(20.05f)\nverticalLineTo(20.05f)"
        )
    }

    @Test
    fun parse_ReflectiveCurveTo_path() {
        assertEquals(
            actual = Command("S4.47,10 9.99,10").toString(),
            expected = "reflectiveCurveTo(4.47f, 10f, 9.99f, 10f)"
        )
    }

    @Test
    fun parse_relative_ReflectiveCurveTo_path() {
        assertEquals(
            actual = Command("s4.47,10 9.99,10").toString(),
            expected = "reflectiveCurveToRelative(4.47f, 10f, 9.99f, 10f)"
        )
    }

    @Test
    fun parse_quadratic_bezier_path() {
        assertEquals(
            actual = Command("Q400,50 600,300").toString(),
            expected = "quadTo(400f, 50f, 600f, 300f)"
        )
    }

    @Test
    fun parse_relative_quadratic_bezier_path() {
        assertEquals(
            actual = Command("q400,50 600,300").toString(),
            expected = "quadToRelative(400f, 50f, 600f, 300f)"
        )
    }

    @Test
    fun parse_reflective_quadratic_bezier_path() {
        assertEquals(
            actual = Command("T1000,300").toString(),
            expected = "reflectiveQuadTo(1000f, 300f)"
        )
    }

    @Test
    fun parse_relative_reflective_quadratic_bezier_path() {
        val command = Command("t1000,300")
        assertEquals(
            actual = command.toString(),
            expected = "reflectiveQuadToRelative(1000f, 300f)"
        )
    }

    @Test
    fun arcTo_toString_with_returns_arcTo() {
        assertEquals(
            actual = ArcTo(
                horizontalEllipseRadius = 1f,
                verticalEllipseRadius = 2f,
                theta = 3f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                x1 = 4f,
                y1 = 5f
            ).toString(),
            expected = "arcTo(1.0f, 2.0f, 3.0f, false, true, 4.0f, 5.0f)"
        )
    }

    @Test
    fun close_toString_returns_close() {
        assertEquals(actual = Close().toString(), expected = "close()")
    }

    @Test
    fun lineTo_toString_returns_lineTo() {
        assertEquals(
            actual = LineTo(x = 10f, y = 20f).toString(),
            expected = "lineTo(10.0f, 20.0f)"
        )
    }

    @Test
    fun moveTo_toString_returns_moveTo() {
        assertEquals(
            actual = MoveTo(x = 10f, y = 20f).toString(),
            expected = "moveTo(10.0f, 20.0f)"
        )
    }

    // @see https://www.w3.org/TR/SVG2/paths.html#PathDataMovetoCommands
    @Test
    fun parse_multiple_Move_command() {
        val movePath = "M27.05,24.55 27.05,24.55"

        val command = Command(movePath)
        assertEquals(
            actual = command.toString(),
            expected = """
                moveTo(27.05f, 24.55f)
                lineTo(27.05f, 24.55f)
            """.trimIndent()
        )
    }
}
