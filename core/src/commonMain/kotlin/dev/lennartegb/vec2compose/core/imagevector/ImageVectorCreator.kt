package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector

class ImageVectorCreator(indentation: CharSequence) {

    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()
    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun create(name: String, imageVector: ImageVector): String {
        return buildString {
            append(composeMethodCreator.createConstructor(name, imageVector))
            imageVector.nodes.joinToString(separator = "") { node ->
                when (node) {
                    is ImageVector.Group -> composeMethodCreator.createGroup(node)
                    is ImageVector.Path -> composeMethodCreator.createPath(node)
                }
            }.also { append(it) }
            append(".build()")
        }.replace(emptyLineRegex, "")
    }
}
