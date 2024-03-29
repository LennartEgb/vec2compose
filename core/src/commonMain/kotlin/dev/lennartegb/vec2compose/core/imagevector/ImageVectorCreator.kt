package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.VectorSet

class ImageVectorCreator(indentation: CharSequence) {

    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()
    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun create(name: String, vectorSet: VectorSet): String {
        return buildString {
            append(composeMethodCreator.createConstructor(name, vectorSet))

            val groups = vectorSet.groups.mergeLines(composeMethodCreator::createGroup)
            val groupIndices = groups.indices
            groups.forEachIndexed { index, line ->
                append(line)
                if (index != groupIndices.last) appendLine()
            }

            val paths = vectorSet.paths.mergeLines(composeMethodCreator::createPath)
            val pathIndices = paths.indices
            paths.forEachIndexed { index, s ->
                append(s)
                if (index != pathIndices.last) appendLine()
            }

            append(".build()")
        }.replace(emptyLineRegex, "")
    }

    private fun <T> List<T>.mergeLines(transform: (T) -> String): List<String> {
        return joinToString(separator = "", transform = transform).lines()
    }
}
