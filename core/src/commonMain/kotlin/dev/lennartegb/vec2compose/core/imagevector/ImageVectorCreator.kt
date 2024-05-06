package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.VectorSet

class ImageVectorCreator(indentation: CharSequence) {

    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()
    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun create(name: String, vectorSet: VectorSet): String {
        return buildString {
            append(composeMethodCreator.createConstructor(name, vectorSet))
            val nodes = vectorSet.nodes.mergeLines { node ->
                when (node) {
                    is VectorSet.Group -> composeMethodCreator.createGroup(node)
                    is VectorSet.Path -> composeMethodCreator.createPath(node)
                }
            }
            val nodeIndices = nodes.indices
            nodes.forEachIndexed { index, line ->
                append(line)
                if (index != nodeIndices.last) appendLine()
            }
            append(".build()")
        }.replace(emptyLineRegex, "")
    }

    private fun <T> List<T>.mergeLines(transform: (T) -> String): List<String> {
        return joinToString(separator = "", transform = transform).lines()
    }
}
