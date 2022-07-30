package parser

import models.VectorSet

class ImageVectorParser(private val indentation: CharSequence = DEFAULT_INDENTATION) {
    companion object {
        const val DEFAULT_INDENTATION = "    "
    }

    fun parse(vectorSet: VectorSet): String {
        return buildString {
            append("ImageVector.Builder(")
            append("defaultWidth = ${vectorSet.width}.dp, ")
            append("defaultHeight = ${vectorSet.height}.dp, ")
            append("viewportWidth = ${vectorSet.viewportWidth}, ")
            append("viewportHeight = ${vectorSet.viewportWidth}")
            append(") {").nl()
            vectorSet.paths.forEach { path ->
                path.commands.forEach { command ->
                    indent().append(command.toComposeMethod()).nl()
                }
            }
            append("}")
        }
    }

    private fun StringBuilder.nl(): StringBuilder = append("\n")
    private fun StringBuilder.indent(): StringBuilder = append(indentation)

    private fun Command.toComposeMethod(): String {
        return when (this) {
            is Command.Close -> "close()"
            is Command.CurveTo -> if (isAbsolute) "curveTo(${x1}f, ${y1}f, ${x2}f, ${y2}f, ${x3}f, ${y3}f)" else "curveToRelative(${x1}f, ${y1}f, ${x2}f, ${y2}f, ${x3}f, $y3)"
            is Command.HorizontalLineTo -> if (isAbsolute) "horizontalLineTo(${x}f)" else "horizontalLineToRelative(${x}f)"
            is Command.LineTo -> if (isAbsolute) "lineTo(${x}f, ${y}f)" else "lineToRelative(${x}f, ${y}f)"
            is Command.MoveTo -> if (isAbsolute) "moveTo(${x}f, ${y}f)" else "moveToRelative(${x}f, ${y}f)"
            is Command.ReflectiveCurveTo -> if (isAbsolute) "reflectiveCurveTo(${x1}f, ${y1}f, ${x2}f, ${y2}f)" else "reflectiveCurveToRelative(${x1}f, ${y1}f, ${x2}f, ${y2}f)"
            is Command.VerticalLineTo -> if (isAbsolute) "verticalLineTo(${y}f)" else "verticalLineToRelative(${y}f)"
        }
    }
}

