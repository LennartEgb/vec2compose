package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Cap.Butt
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Cap.Square
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Join.Bevel
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Join.Miter

internal class ComposeMethodCreator(private val indentation: String) {

    fun createConstructor(name: String, set: ImageVector): String = buildString {
        append("ImageVector.Builder(").appendLine()
        indent().append("name = \"$name\",").appendLine()
        indent().append("defaultWidth = ${set.width}.dp,").appendLine()
        indent().append("defaultHeight = ${set.height}.dp,").appendLine()
        indent().append("viewportWidth = ${set.viewportWidth}f,").appendLine()
        indent().append("viewportHeight = ${set.viewportHeight}f").appendLine()
        append(")")
    }

    fun createPath(path: ImageVector.Path, forBuilder: Boolean = true): String = buildString {
        if (forBuilder) append(".")
        append("path(").appendLine()
        indent().append("fill = ${path.fillColor?.solid()},").appendLine()
        indent().append("fillAlpha = ${path.alpha}f,").appendLine()
        indent().append("stroke = ${path.stroke.color?.solid()},").appendLine()
        indent().append("strokeAlpha = ${path.stroke.alpha}f,").appendLine()
        indent().append("strokeLineWidth = ${path.stroke.width}f,").appendLine()
        indent().append("strokeLineCap = ${path.stroke.cap.property()},").appendLine()
        indent().append("strokeLineJoin = ${path.stroke.join.property()},").appendLine()
        indent().append("strokeLineMiter = ${path.stroke.miter}f,").appendLine()
        indent().append("pathFillType = ${path.fillType.composeName}").appendLine()
        appendLine(") {")

        path.commands
            .joinToString("\n")
            .prependIndent(indent = indentation)
            .also { appendLine(it) }

        append("}")
    }.removePrefix(indentation)

    fun createGroup(group: ImageVector.Group, forBuilder: Boolean = true): String = buildString {
        if (forBuilder) append(".")
        append("group(").appendLine()
        group.name?.also { indent().append("name = \"$it\",").appendLine() }
        indent().append("rotate = ${group.rotate}f,").appendLine()
        indent().append("pivotX = ${group.pivot.x}f,").appendLine()
        indent().append("pivotY = ${group.pivot.y}f,").appendLine()
        indent().append("scaleX = ${group.scale.x}f,").appendLine()
        indent().append("scaleY = ${group.scale.y}f,").appendLine()
        indent().append("translationX = ${group.translation.x}f,").appendLine()
        indent().append("translationY = ${group.translation.y}f,").appendLine()
        indent().append("clipPathData = emptyList()").appendLine()
        append(") {").appendLine()
        group.nodes.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") {
                when (it) {
                    is ImageVector.Group -> createGroup(it, forBuilder = false)
                    is ImageVector.Path -> createPath(it, forBuilder = false)
                }
            }
            ?.prependIndent(indentation)
            ?.let(::appendLine)
        append("}")
    }

    private fun StringBuilder.indent(): StringBuilder = append(indentation)
    private val ImageVector.Path.FillType.composeName: String
        get() = when (this) {
            ImageVector.Path.FillType.NonZero -> "PathFillType.NonZero"
            ImageVector.Path.FillType.EvenOdd -> "PathFillType.EvenOdd"
        }

    private fun ImageVector.Path.FillColor?.solid(): String =
        this?.let { "SolidColor($it)" } ?: "null"

    private fun ImageVector.Path.Stroke.Cap.property(): String = when (this) {
        Butt -> "StrokeCap.Butt"
        Square -> "StrokeCap.Square"
        ImageVector.Path.Stroke.Cap.Round -> "StrokeCap.Round"
    }

    private fun ImageVector.Path.Stroke.Join.property(): String = when (this) {
        Bevel -> "StrokeJoin.Bevel"
        Miter -> "StrokeJoin.Miter"
        ImageVector.Path.Stroke.Join.Round -> "StrokeJoin.Round"
    }
}
