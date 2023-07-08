package imagevector

import VectorSet

internal class ImageVectorParser(indentation: CharSequence = DEFAULT_INDENTATION) {
    private companion object {
        const val DEFAULT_INDENTATION = "    "
    }

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
