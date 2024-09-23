package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector

class ImageVectorCreator(indentation: String) {

    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()
    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun create(name: String, imageVector: ImageVector): String {
        var content = composeMethodCreator.createConstructor(name, imageVector)
        content += imageVector.nodes.joinToString(separator = "") { node ->
            when (node) {
                is ImageVector.Group -> composeMethodCreator.createGroup(node)
                is ImageVector.Path -> composeMethodCreator.createPath(node)
            }
        }
        content += ".build()"
        return content.replace(emptyLineRegex, "")
    }
}
