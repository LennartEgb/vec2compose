package output

import imagevector.ImageVectorImportProvider
import java.io.File

internal class FileOutputStrategy(
    private val name: String,
    private val pathname: String,
    private val importProvider: ImageVectorImportProvider,
) : OutputStrategy {
    override fun write(content: String) {
        buildString {
                importProvider.createImports().forEach { appendLine(it) }
                appendLine()
                appendLine("private var cache: ImageVector? = null")
                appendLine("val $name: ImageVector")
                content.lines().forEachMeta { line, isFirst, isLast ->
                    indent()
                    if (isFirst) append("get() = cache ?: ")
                    append(line)
                    if (!isLast) appendLine()
                }
                appendLine(".also { cache = it }")
            }
            .also { File(pathname).apply { writeText(it) }.createNewFile() }
    }

    private fun StringBuilder.indent() = append("    ")

    private fun <T> List<T>.forEachMeta(action: (T, isFirst: Boolean, isLast: Boolean) -> Unit) {
        forEachIndexed { index, t -> action(t, index == indices.first, index == indices.last) }
    }
}
