package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector

internal class ComposeMethodCreator(private val indentation: String) {

    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()

    fun createConstructor(name: String, set: ImageVector): String = buildString {
        append("ImageVector.Builder(").appendLine()
        indent().append("name = \"$name\",").appendLine()
        indent().append("defaultWidth = ${set.width}.dp,").appendLine()
        indent().append("defaultHeight = ${set.height}.dp,").appendLine()
        indent().append("viewportWidth = ${set.viewportWidth}f,").appendLine()
        indent().append("viewportHeight = ${set.viewportHeight}f").appendLine()
        append(")")
    }

    fun createNode(node: ImageVector.Node): String = when (node) {
        is ImageVector.Group -> createGroup(node)
        is ImageVector.Path -> createPath(node)
    }.replace(emptyLineRegex, "")

    private fun createPath(path: ImageVector.Path): String = buildString {
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

        path.commands.joinToString("\n")
            .prependIndent(indent = indentation)
            .also(::appendLine)

        append("}")
    }.removePrefix(indentation)

    private fun createGroup(group: ImageVector.Group): String = buildString {
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
        group.nodes.joinToString(separator = "\n", transform = ::createNode)
            .prependIndent(indentation)
            .also(::appendLine)
        append("}")
    }

    private fun StringBuilder.indent(): StringBuilder = append(indentation)
    private val ImageVector.Path.FillType.composeName: String
        get() = when (this) {
            ImageVector.Path.FillType.NonZero -> "PathFillType.NonZero"
            ImageVector.Path.FillType.EvenOdd -> "PathFillType.EvenOdd"
        }
}
