package imagevector

import commands.Command

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
        is Command.ArcTo -> "${horizontalEllipseRadius}f, " +
                "${verticalEllipseRadius}f, " +
                "${theta}f, " +
                "$isMoreThanHalf, " +
                "$isPositiveArc, " +
                "${x1}f, " +
                "${y1}f"
        is Command.CurveTo,
        is Command.HorizontalLineTo,
        is Command.LineTo,
        is Command.MoveTo,
        is Command.QuadraticBezierTo,
        is Command.ReflectiveCurveTo,
        is Command.ReflectiveQuadraticBezierTo,
        is Command.VerticalLineTo -> values().joinToString(separator = "f, ", postfix = "f")
    }