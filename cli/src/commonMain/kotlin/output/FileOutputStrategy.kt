package output

import imagevector.ImageVectorImportProvider
import okio.FileSystem
import okio.Path.Companion.toPath

internal class FileOutputStrategy(
    private val name: String,
    private val pathname: String,
    private val importProvider: ImageVectorImportProvider,
    private val fileSystem: FileSystem,
) : OutputStrategy {
    override fun write(content: String) {
        val output = buildString {
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
        fileSystem.write(pathname.toPath()) {
            writeUtf8(output)
        }
    }

    private fun StringBuilder.indent() = append("    ")

    private fun <T> List<T>.forEachMeta(action: (T, isFirst: Boolean, isLast: Boolean) -> Unit) {
        forEachIndexed { index, t -> action(t, index == indices.first, index == indices.last) }
    }
}
