package imagevector

import models.VectorSet

internal class ImageVectorParser(private val indentation: CharSequence = DEFAULT_INDENTATION) {
    companion object {
        const val DEFAULT_INDENTATION = "    "
    }

    private val composeMethodCreator = ComposeMethodCreator(indentation)

    fun parse(name: String, vectorSet: VectorSet): String {
        return buildString {
            append(composeMethodCreator.parseConstructor(name, vectorSet))
            vectorSet.paths.forEach { path ->
                append(composeMethodCreator.parsePath(path = path))
            }
            append(".build()")
        }
    }
}

