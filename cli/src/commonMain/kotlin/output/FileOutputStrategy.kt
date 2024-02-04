package output

import imagevector.ImageVectorImportProvider
import okio.FileSystem
import okio.Path.Companion.toPath

internal class FileOutputStrategy(
    private val name: String,
    private val pathname: String,
    private val importProvider: ImageVectorImportProvider,
    private val fileSystem: FileSystem,
    private val indentation: String = "\t",
) : OutputStrategy {
    override fun write(content: String) {
        val output = buildString {
            importProvider.createImports().forEach { appendLine(it) }
            appendLine()
            appendLine("private var cache: ImageVector? = null")
            appendLine("val $name: ImageVector")
            content.lines().forEachIndexed { index, line ->
                indent()
                if (index == indices.first) append("get() = cache ?: ")
                append(line)
                if (index != indices.last) appendLine()
            }
            appendLine(".also { cache = it }")
        }
        fileSystem.write(pathname.toPath()) {
            writeUtf8(output)
        }
    }

    private fun StringBuilder.indent() = append(indentation)

}
