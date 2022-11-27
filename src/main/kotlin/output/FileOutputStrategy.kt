package output

import imagevector.ImageVectorImportProvider
import java.io.File
import java.lang.StringBuilder

internal class FileOutputStrategy(
    private val name: String,
    private val pathname: String,
    private val importProvider: ImageVectorImportProvider,
) : OutputStrategy {
    override fun write(content: String) {
        buildString {
            importProvider.createImports().forEach { append(it).appendLine() }
            appendLine()

            append("private var cache: ImageVector? = null").appendLine()
            append("val $name: ImageVector").appendLine()

            val lines = content.lines()
            lines.forEach { line ->
                if (line == lines.first()) {
                    indent().append("get() = cache ?: $line")
                } else {
                    indent().append(line)
                }
                appendLine()
            }
            append(".also { cache = it }")
        }.also { File(pathname).apply { writeText(it) }.createNewFile() }
    }

    private fun StringBuilder.indent() = append("    ")
}