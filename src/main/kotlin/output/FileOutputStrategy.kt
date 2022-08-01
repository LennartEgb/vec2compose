package output

import java.io.File

class FileOutputStrategy(
    private val pathname: String,
) : OutputStrategy {
    override fun write(content: String) {
        buildString {
            append("import androidx.compose.ui.graphics.vector.ImageVector").appendLine()
            appendLine()
            append(content)
        }.also { File(pathname).apply { writeText(it) }.createNewFile() }
    }
}