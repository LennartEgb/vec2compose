package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector

class ImageVectorCreator(indentation: String) {

    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun create(name: String, imageVector: ImageVector): String {
        var content = composeMethodCreator.createConstructor(name, imageVector)
        content += imageVector.nodes.joinToString(
            separator = ".",
            prefix = if (imageVector.nodes.isNotEmpty()) "." else "",
            transform = composeMethodCreator::createNode
        )
        content += ".build()"
        return content
    }
}
