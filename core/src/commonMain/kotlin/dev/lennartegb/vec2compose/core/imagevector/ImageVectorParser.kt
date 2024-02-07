package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.VectorSet

class ImageVectorParser(indentation: CharSequence) {
    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()
    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun parse(name: String, vectorSet: VectorSet): String {
        return buildString {
            append(composeMethodCreator.parseConstructor(name, vectorSet))
            vectorSet.groups.map(composeMethodCreator::parseGroup).forEach(::appendLine)
            vectorSet.paths.map(composeMethodCreator::parsePath).forEachIndexed { index, s ->
                append(s)
                if (index != vectorSet.paths.indices.last) appendLine()
            }
            append(".build()")
        }.replace(emptyLineRegex, "")
    }
}
