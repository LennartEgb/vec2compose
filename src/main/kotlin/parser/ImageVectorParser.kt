package parser

import models.VectorSet

class ImageVectorParser(private val indentation: CharSequence = DEFAULT_INDENTATION) {
    companion object {
        const val DEFAULT_INDENTATION = "    "
    }

    fun parse(name: String, vectorSet: VectorSet): String {
        return buildString {
            append("ImageVector.Builder(").appendLine()
            indent().append("name = \"$name\",").appendLine()
            indent().append("defaultWidth = ${vectorSet.width}.dp,").appendLine()
            indent().append("defaultHeight = ${vectorSet.height}.dp,").appendLine()
            indent().append("viewportWidth = ${vectorSet.viewportWidth},").appendLine()
            indent().append("viewportHeight = ${vectorSet.viewportWidth}").appendLine()
            append(") {").appendLine()
            vectorSet.paths.forEach { path ->
                path.commands.forEach { command ->
                    indent().append(command.toComposeMethod()).appendLine()
                }
            }
            append("}")
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
        }
}

