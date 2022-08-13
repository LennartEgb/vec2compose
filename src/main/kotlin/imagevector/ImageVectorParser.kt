package imagevector

import models.VectorSet

internal class ImageVectorParser(indentation: CharSequence = DEFAULT_INDENTATION) {
    private companion object {
        const val DEFAULT_INDENTATION = "    "
    }

    private val emptyLineRegex = "(?m)^[ \t]*\r?\n".toRegex()
    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun parse(name: String, vectorSet: VectorSet): String {
        return buildString {
            append(composeMethodCreator.parseConstructor(name, vectorSet))
            vectorSet.groups.map(composeMethodCreator::parseGroup).forEach(::append)
            vectorSet.paths.map(composeMethodCreator::parsePath).forEach(::append)
            append(".build()")
        }.replace(emptyLineRegex, "")
    }
}

