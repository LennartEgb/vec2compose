package imagevector

import commands.Command
import models.VectorSet

internal class ImageVectorParser(private val indentation: CharSequence = DEFAULT_INDENTATION) {
    companion object {
        const val DEFAULT_INDENTATION = "    "
    }

    fun parse(name: String, vectorSet: VectorSet): String {
        return buildString {
            append("ImageVector.Builder(").appendLine()
            indent().append("name = \"$name\",").appendLine()
            indent().append("defaultWidth = ${vectorSet.width}.dp,").appendLine()
            indent().append("defaultHeight = ${vectorSet.height}.dp,").appendLine()
            indent().append("viewportWidth = ${vectorSet.viewportWidth}f,").appendLine()
            indent().append("viewportHeight = ${vectorSet.viewportWidth}f").appendLine()
            append(")")
            vectorSet.paths.forEach { path ->
                append(".path(").appendLine()
                indent().append("fill = SolidColor(Color.Black),").appendLine()
                indent().append("fillAlpha = 1f,").appendLine()
                indent().append("stroke = null,").appendLine()
                indent().append("strokeAlpha = 1f,").appendLine()
                indent().append("strokeLineWidth = 1f,").appendLine()
                indent().append("strokeLineCap = StrokeCap.Butt,").appendLine()
                indent().append("strokeLineJoin = StrokeJoin.Bevel,").appendLine()
                indent().append("strokeLineMiter = 1f,").appendLine()
                indent().append("pathFillType = PathFillType.NonZero").appendLine()
                append(") {").appendLine()

                path.commands.forEach { command ->
                    indent().append(command.toComposeMethod()).appendLine()
                }
                append("}")
            }
            append(".build()")
        }
    }

    private fun StringBuilder.indent(): StringBuilder = append(indentation)

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
}

