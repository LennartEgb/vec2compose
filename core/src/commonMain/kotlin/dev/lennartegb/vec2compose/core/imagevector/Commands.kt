package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.Command
import dev.lennartegb.vec2compose.core.commands.CurveTo
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo

internal val Command.method: String get() = "$methodName($methodParams)"

private val Command.methodName: String
    get() = when (this) {
        is ArcTo -> if (isAbsolute) "arcTo" else "arcToRelative"
        is Close -> "close"
        is CurveTo -> if (isAbsolute) "curveTo" else "curveToRelative"
        is Command.HorizontalLineTo -> if (isAbsolute) "horizontalLineTo" else "horizontalLineToRelative"
        is LineTo -> if (isAbsolute) "lineTo" else "lineToRelative"
        is MoveTo -> if (isAbsolute) "moveTo" else "moveToRelative"
        is Command.QuadraticBezierTo -> if (isAbsolute) "quadTo" else "quadToRelative"
        is Command.ReflectiveCurveTo -> if (isAbsolute) "reflectiveCurveTo" else "reflectiveCurveToRelative"
        is Command.ReflectiveQuadraticBezierTo -> if (isAbsolute) "reflectiveQuadTo" else "reflectiveQuadToRelative"
        is Command.VerticalLineTo -> if (isAbsolute) "verticalLineTo" else "verticalLineToRelative"
    }

private val Command.methodParams: String
    get() = when (this) {
        is ArcTo -> floatParams(horizontalEllipseRadius, verticalEllipseRadius, theta) +
            ", $isMoreThanHalf, $isPositiveArc, " +
            floatParams(x1, y1)

        Close -> ""
        is CurveTo -> floatParams(x1, y1, x2, y2, x3, y3)
        is Command.HorizontalLineTo -> "${x}f"
        is LineTo -> floatParams(x, y)
        is MoveTo -> floatParams(x, y)
        is Command.QuadraticBezierTo -> floatParams(x1, y1, x2, y2)
        is Command.ReflectiveCurveTo -> floatParams(x1, y1, x2, y2)
        is Command.ReflectiveQuadraticBezierTo -> floatParams(x, y)
        is Command.VerticalLineTo -> "${y}f"
    }

private fun floatParams(vararg values: Float): String = values.joinToString("f, ", postfix = "f")
