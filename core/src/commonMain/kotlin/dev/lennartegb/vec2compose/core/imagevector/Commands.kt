package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.commands.Command

internal val Command.method: String get() = "$methodName($methodParams)"

internal val Command.methodName: String
    get() = when (this) {
        is Command.Close -> "close"
        is Command.CurveTo -> if (isAbsolute) "curveTo" else "curveToRelative"
        is Command.HorizontalLineTo -> if (isAbsolute) "horizontalLineTo" else "horizontalLineToRelative"
        is Command.LineTo -> if (isAbsolute) "lineTo" else "lineToRelative"
        is Command.MoveTo -> if (isAbsolute) "moveTo" else "moveToRelative"
        is Command.ReflectiveCurveTo -> if (isAbsolute) "reflectiveCurveTo" else "reflectiveCurveToRelative"
        is Command.VerticalLineTo -> if (isAbsolute) "verticalLineTo" else "verticalLineToRelative"
        is Command.QuadraticBezierTo -> if (isAbsolute) "quadTo" else "quadToRelative"
        is Command.ReflectiveQuadraticBezierTo -> if (isAbsolute) "reflectiveQuadTo" else "reflectiveQuadToRelative"
        is Command.ArcTo -> if (isAbsolute) "arcTo" else "arcToRelative"
    }

internal val Command.methodParams: String
    get() = when (this) {
        Command.Close -> ""
        is Command.ArcTo -> floatParams(horizontalEllipseRadius, verticalEllipseRadius, theta) +
            ", $isMoreThanHalf, $isPositiveArc, " +
            floatParams(x1, y1)
        is Command.HorizontalLineTo -> "${x}f"
        is Command.VerticalLineTo -> "${y}f"
        is Command.CurveTo -> floatParams(x1, y1, x2, y2, x3, y3)
        is Command.LineTo -> floatParams(x, y)
        is Command.MoveTo -> floatParams(x, y)
        is Command.QuadraticBezierTo -> floatParams(x1, y1, x2, y2)
        is Command.ReflectiveCurveTo -> floatParams(x1, y1, x2, y2)
        is Command.ReflectiveQuadraticBezierTo -> floatParams(x, y)
    }

private fun floatParams(vararg values: Float): String = values.joinToString("f, ", postfix = "f")
