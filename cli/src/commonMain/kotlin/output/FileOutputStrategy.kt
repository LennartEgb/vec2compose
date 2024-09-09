package output

import dev.lennartegb.vec2compose.core.imagevector.ImageVectorImportProvider
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
            importProvider.createImports(hasGroup = content.contains("group")).forEach { appendLine(it) }
            appendLine()
            appendLine("private var cache: ImageVector? = null")
            appendLine("val $name: ImageVector")
            appendLine("get() = cache ?: $content.also { cache = it }".prependIndent(indentation))
        }
        fileSystem.write(pathname.toPath()) {
            writeUtf8(output)
        }
    }
}
