package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorImportProvider

class KotlinFileContentCreator(
    private val indentation: String,
    private val importProvider: ImageVectorImportProvider = ImageVectorImportProvider(),
    private val imageVectorCreator: ImageVectorCreator = ImageVectorCreator(indentation)
) {
    fun create(packageName: String?, name: String, vectorSet: VectorSet): String {
        val hasGroups = vectorSet.nodes.any { it is VectorSet.Group }
        val content = imageVectorCreator.create(name = name, vectorSet = vectorSet)
        return buildString {
            if (packageName != null) {
                appendSection("package $packageName")
            }

            appendSection(importProvider.getImports(hasGroup = hasGroups))

            appendLine("private var cache: ImageVector? = null")
            appendLine("val $name: ImageVector")

            appendLine("get() = cache ?: $content.also { cache = it }".prependIndent(indentation))
        }
    }

    private fun StringBuilder.appendSection(value: String) {
        appendLine(value)
        appendLine()
    }
}