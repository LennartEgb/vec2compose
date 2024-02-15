package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke.Cap.Butt
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke.Cap.Square
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke.Join.Bevel
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke.Join.Miter
import dev.lennartegb.vec2compose.core.commands.Command

internal class ComposeMethodCreator(private val indentation: CharSequence) {

    fun createConstructor(name: String, set: VectorSet): String = buildString {
        append("ImageVector.Builder(").appendLine()
        indent().append("name = \"$name\",").appendLine()
        indent().append("defaultWidth = ${set.width}.dp,").appendLine()
        indent().append("defaultHeight = ${set.height}.dp,").appendLine()
        indent().append("viewportWidth = ${set.viewportWidth}f,").appendLine()
        indent().append("viewportHeight = ${set.viewportHeight}f").appendLine()
        append(")")
    }

    fun createPath(path: VectorSet.Path, forBuilder: Boolean = true): String = buildString {
        if (forBuilder) append(".")
        append("path(").appendLine()
        val fillColor = path.fillColor?.solid()
        indent().append("fill = $fillColor,").appendLine()
        indent().append("fillAlpha = ${path.alpha}f,").appendLine()
        indent().append("stroke = ${path.stroke.color?.solid()},").appendLine()
        indent().append("strokeAlpha = ${path.stroke.alpha}f,").appendLine()
        indent().append("strokeLineWidth = ${path.stroke.width}f,").appendLine()
        indent().append("strokeLineCap = ${path.stroke.cap.property()},").appendLine()
        indent().append("strokeLineJoin = ${path.stroke.join.property()},").appendLine()
        indent().append("strokeLineMiter = ${path.stroke.miter}f,").appendLine()
        indent().append("pathFillType = ${path.fillType.composeName}").appendLine()
        append(") {").appendLine()

        path.commands.map(Command::method)
            .forEach { indent().append(it).appendLine() }

        append("}")
    }.removePrefix(indentation)

    fun createGroup(group: VectorSet.Group, forBuilder: Boolean = true): String = buildString {
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
        group.groups.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { createGroup(it, forBuilder = false) }
            ?.setupIndent()
            ?.let(::append)
            ?.appendLine()
        group.paths.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { createPath(it, forBuilder = false) }
            ?.setupIndent()
            ?.let(::append)
            ?.appendLine()
        append("}")
    }

    private fun String.setupIndent(): String = prependIndent(indent = indentation.toString())
    private fun StringBuilder.indent(): StringBuilder = append(indentation)
    private val VectorSet.Path.FillType.composeName: String
        get() = when (this) {
            VectorSet.Path.FillType.NonZero -> "PathFillType.NonZero"
            VectorSet.Path.FillType.EvenOdd -> "PathFillType.EvenOdd"
        }

    private fun VectorSet.Path.FillColor?.solid(): String = this?.let { "SolidColor($it)" } ?: "null"
    private fun VectorSet.Path.Stroke.Cap.property(): String = when (this) {
        Butt -> "StrokeCap.Butt"
        Square -> "StrokeCap.Square"
        VectorSet.Path.Stroke.Cap.Round -> "StrokeCap.Round"
    }

    private fun VectorSet.Path.Stroke.Join.property(): String = when (this) {
        Bevel -> "StrokeJoin.Bevel"
        Miter -> "StrokeJoin.Miter"
        VectorSet.Path.Stroke.Join.Round -> "StrokeJoin.Round"
    }
}
