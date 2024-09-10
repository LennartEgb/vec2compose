package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.CurveTo
import dev.lennartegb.vec2compose.core.commands.HorizontalLineTo
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import dev.lennartegb.vec2compose.core.commands.QuadraticBezierTo
import dev.lennartegb.vec2compose.core.commands.ReflectiveCurveTo
import dev.lennartegb.vec2compose.core.commands.ReflectiveQuadraticBezierTo
import dev.lennartegb.vec2compose.core.commands.VerticalLineTo
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandsTest {

    @Test
    fun `ArcTo method with absolute=true returns arcTo`() {
        assertEquals(
            actual = ArcTo(
                horizontalEllipseRadius = 1f,
                verticalEllipseRadius = 2f,
                theta = 3f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                x1 = 4f,
                y1 = 5f,
                isAbsolute = true
            ).method,
            expected = "arcTo(1.0f, 2.0f, 3.0f, false, true, 4.0f, 5.0f)"
        )
    }

    @Test
    fun `ArcTo toString with absolute=true returns arcTo`() {
        assertEquals(
            actual = ArcTo(
                horizontalEllipseRadius = 1f,
                verticalEllipseRadius = 2f,
                theta = 3f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                x1 = 4f,
                y1 = 5f,
                isAbsolute = true
            ).toString(),
            expected = "arcTo(1.0f, 2.0f, 3.0f, false, true, 4.0f, 5.0f)"
        )
    }

    @Test
    fun `ArcTo method with absolute=false returns arcToRelative`() {
        assertEquals(
            actual = ArcTo(
                horizontalEllipseRadius = 1f,
                verticalEllipseRadius = 2f,
                theta = 3f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                x1 = 4f,
                y1 = 5f,
                isAbsolute = false
            ).method,
            expected = "arcToRelative(1.0f, 2.0f, 3.0f, false, true, 4.0f, 5.0f)"
        )
    }

    @Test
    fun `ArcTo toString with absolute=false returns arcToRelative`() {
        assertEquals(
            actual = ArcTo(
                horizontalEllipseRadius = 1f,
                verticalEllipseRadius = 2f,
                theta = 3f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                x1 = 4f,
                y1 = 5f,
                isAbsolute = false
            ).toString(),
            expected = "arcToRelative(1.0f, 2.0f, 3.0f, false, true, 4.0f, 5.0f)"
        )
    }

    // NOTE: will be removed once toString methods are in place
    @Test
    fun `Close method returns close`() {
        assertEquals(actual = Close.method, expected = "close()")
    }

    @Test
    fun `Close toString returns close`() {
        assertEquals(actual = "$Close", expected = "close()")
    }

    @Test
    fun `CurveTo method with absolute=true returns curveTo`() {
        assertEquals(
            actual = CurveTo(1f, 2f, 3f, 4f, 5f, 6f, isAbsolute = true).method,
            expected = "curveTo(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)"
        )
    }

    @Test
    fun `CurveTo toString with absolute=true returns curveTo`() {
        assertEquals(
            actual = CurveTo(1f, 2f, 3f, 4f, 5f, 6f, isAbsolute = true).toString(),
            expected = "curveTo(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)"
        )
    }

    @Test
    fun `CurveTo method with absolute=false returns curveToRelative`() {
        assertEquals(
            actual = CurveTo(1f, 2f, 3f, 4f, 5f, 6f, isAbsolute = false).method,
            expected = "curveToRelative(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)"
        )
    }

    @Test
    fun `CurveTo toString with absolute=false returns curveToRelative`() {
        assertEquals(
            actual = CurveTo(1f, 2f, 3f, 4f, 5f, 6f, isAbsolute = false).toString(),
            expected = "curveToRelative(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f)"
        )
    }

    @Test
    fun `HorizontalLineTo method with absolute=true returns horizontalLineTo`() {
        assertEquals(
            actual = HorizontalLineTo(x = 10f, isAbsolute = true).method,
            expected = "horizontalLineTo(10.0f)"
        )
    }

    @Test
    fun `HorizontalLineTo toString with absolute=true returns horizontalLineTo`() {
        assertEquals(
            actual = HorizontalLineTo(x = 10f, isAbsolute = true).toString(),
            expected = "horizontalLineTo(10.0f)"
        )
    }

    @Test
    fun `HorizontalLineTo method with absolute=false returns horizontalLineTo`() {
        assertEquals(
            actual = HorizontalLineTo(x = 10f, isAbsolute = false).method,
            expected = "horizontalLineToRelative(10.0f)"
        )
    }

    @Test
    fun `HorizontalLineTo toString with absolute=false returns horizontalLineTo`() {
        assertEquals(
            actual = HorizontalLineTo(x = 10f, isAbsolute = false).toString(),
            expected = "horizontalLineToRelative(10.0f)"
        )
    }

    @Test
    fun `LineTo method with absolute=true returns lineTo`() {
        assertEquals(
            actual = LineTo(x = 10f, y = 20f, isAbsolute = true).method,
            expected = "lineTo(10.0f, 20.0f)"
        )
    }

    @Test
    fun `LineTo toString with absolute=true returns lineTo`() {
        assertEquals(
            actual = LineTo(x = 10f, y = 20f, isAbsolute = true).toString(),
            expected = "lineTo(10.0f, 20.0f)"
        )
    }

    @Test
    fun `LineTo method with absolute=false returns lineToRelative`() {
        assertEquals(
            actual = LineTo(x = 10f, y = 20f, isAbsolute = false).method,
            expected = "lineToRelative(10.0f, 20.0f)"
        )
    }

    @Test
    fun `LineTo toString with absolute=false returns lineToRelative`() {
        assertEquals(
            actual = LineTo(x = 10f, y = 20f, isAbsolute = false).toString(),
            expected = "lineToRelative(10.0f, 20.0f)"
        )
    }

    @Test
    fun `MoveTo method with absolute=true returns moveTo`() {
        assertEquals(
            actual = MoveTo(x = 10f, y = 20f, isAbsolute = true).method,
            expected = "moveTo(10.0f, 20.0f)"
        )
    }

    @Test
    fun `MoveTo toString with absolute=true returns moveTo`() {
        assertEquals(
            actual = MoveTo(x = 10f, y = 20f, isAbsolute = true).toString(),
            expected = "moveTo(10.0f, 20.0f)"
        )
    }

    @Test
    fun `MoveTo method with absolute=false returns moveToRelative`() {
        assertEquals(
            actual = MoveTo(x = 10f, y = 20f, isAbsolute = false).method,
            expected = "moveToRelative(10.0f, 20.0f)"
        )
    }

    @Test
    fun `MoveTo toString with absolute=false returns moveToRelative`() {
        assertEquals(
            actual = MoveTo(x = 10f, y = 20f, isAbsolute = false).toString(),
            expected = "moveToRelative(10.0f, 20.0f)"
        )
    }

    @Test
    fun `QuadraticBezierTo with absolute=true method returns quadTo`() {
        assertEquals(
            actual = QuadraticBezierTo(
                x1 = 1f,
                y1 = 2f,
                x2 = 3f,
                y2 = 4f,
                isAbsolute = true
            ).method,
            expected = "quadTo(1.0f, 2.0f, 3.0f, 4.0f)"
        )
    }

    @Test
    fun `QuadraticBezierTo with absolute=false method returns quadToRelative`() {
        assertEquals(
            actual = QuadraticBezierTo(
                x1 = 1f,
                y1 = 2f,
                x2 = 3f,
                y2 = 4f,
                isAbsolute = false
            ).method,
            expected = "quadToRelative(1.0f, 2.0f, 3.0f, 4.0f)"
        )
    }

    @Test
    fun `ReflectiveCurveTo with absolute=true method returns reflectiveCurveTo`() {
        assertEquals(
            actual = ReflectiveCurveTo(
                x1 = 1f,
                y1 = 2f,
                x2 = 3f,
                y2 = 4f,
                isAbsolute = true
            ).method,
            expected = "reflectiveCurveTo(1.0f, 2.0f, 3.0f, 4.0f)"
        )
    }

    @Test
    fun `ReflectiveCurveTo with absolute=false method returns reflectiveCurveToRelative`() {
        assertEquals(
            actual = ReflectiveCurveTo(
                x1 = 1f,
                y1 = 2f,
                x2 = 3f,
                y2 = 4f,
                isAbsolute = false
            ).method,
            expected = "reflectiveCurveToRelative(1.0f, 2.0f, 3.0f, 4.0f)"
        )
    }

    @Test
    fun `ReflectiveQuadraticBezierTo with absolute=true method returns reflectiveQuadraticBezierTo`() {
        assertEquals(
            actual = ReflectiveQuadraticBezierTo(x = 1f, y = 2f, isAbsolute = true).method,
            expected = "reflectiveQuadTo(1.0f, 2.0f)"
        )
    }

    @Test
    fun `ReflectiveQuadraticBezierTo with absolute=false method returns reflectiveQuadraticBezierTo`() {
        assertEquals(
            actual = ReflectiveQuadraticBezierTo(x = 1f, y = 2f, isAbsolute = false).method,
            expected = "reflectiveQuadToRelative(1.0f, 2.0f)"
        )
    }

    @Test
    fun `VerticalLineTo with absolute=true method returns verticalLineTo`() {
        assertEquals(
            actual = VerticalLineTo(y = 2f, isAbsolute = true).method,
            expected = "verticalLineTo(2.0f)"
        )
    }

    @Test
    fun `VerticalLineTo with absolute=false method returns verticalLineToRelative`() {
        assertEquals(
            actual = VerticalLineTo(y = 2f, isAbsolute = false).method,
            expected = "verticalLineToRelative(2.0f)"
        )
    }
}
