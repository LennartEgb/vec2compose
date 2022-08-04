package imagevector

import commands.Command
import models.VectorSet

internal class ComposeMethodCreator(private val indentation: CharSequence) {

    fun parseConstructor(name: String, set: VectorSet): String = buildString {
        append("ImageVector.Builder(").appendLine()
        indent().append("name = \"$name\",").appendLine()
        indent().append("defaultWidth = ${set.width}.dp,").appendLine()
        indent().append("defaultHeight = ${set.height}.dp,").appendLine()
        indent().append("viewportWidth = ${set.viewportWidth}f,").appendLine()
        indent().append("viewportHeight = ${set.viewportWidth}f").appendLine()
        append(")")
    }

    fun parsePath(path: VectorSet.Path): String = buildString {
        append(".path(").appendLine()
        indentAppend("fill = SolidColor(Color.Black),").appendLine()
        indentAppend("fillAlpha = 1f,").appendLine()
        indentAppend("stroke = null,").appendLine()
        indentAppend("strokeAlpha = 1f,").appendLine()
        indentAppend("strokeLineWidth = 1f,").appendLine()
        indentAppend("strokeLineCap = StrokeCap.Butt,").appendLine()
        indentAppend("strokeLineJoin = StrokeJoin.Bevel,").appendLine()
        indentAppend("strokeLineMiter = 1f,").appendLine()
        indentAppend("pathFillType = ${path.fillType.composeName}").appendLine()
        append(") {").appendLine()
        path.commands.forEach { command ->
            indentAppend(command.toComposeMethod()).appendLine()
        }
        append("}")
    }

    private fun Command.toComposeMethod(): String {
        return method + (values().takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "f, ", prefix = "(", postfix = "f)")
            ?: "()")
    }

    private val Command.method: String
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

    private fun StringBuilder.indent() = append(indentation)
    private fun StringBuilder.indentAppend(value: String) = indent().append(value)
    private val VectorSet.Path.FillType.composeName: String
        get() = when (this) {
            VectorSet.Path.FillType.NonZero -> "PathFillType.NonZero"
            VectorSet.Path.FillType.EvenOdd -> "PathFillType.EvenOdd"
        }
}



