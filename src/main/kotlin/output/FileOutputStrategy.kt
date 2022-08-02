package output

import imagevector.ImageVectorImportProvider
import java.io.File

internal class FileOutputStrategy(
    private val pathname: String,
    private val importProvider: ImageVectorImportProvider,
) : OutputStrategy {
    override fun write(content: String) {
        buildString {
            importProvider.createImports().forEach { append(it).appendLine() }
            appendLine()
            append(content)
        }.also { File(pathname).apply { writeText(it) }.createNewFile() }
    }
}