package output

import java.io.File

class FileOutputStrategy(
    private val pathname: String,
) : OutputStrategy {
    override fun write(content: String) {
        buildString {
            append("import androidx.compose.ui.graphics.Color").appendLine()
            append("import androidx.compose.ui.graphics.PathFillType").appendLine()
            append("import androidx.compose.ui.graphics.SolidColor").appendLine()
            append("import androidx.compose.ui.graphics.StrokeCap").appendLine()
            append("import androidx.compose.ui.graphics.StrokeJoin").appendLine()
            append("import androidx.compose.ui.graphics.vector.ImageVector").appendLine()
            append("import androidx.compose.ui.graphics.vector.path").appendLine()
            append("import androidx.compose.ui.unit.dp").appendLine()
            appendLine()
            append(content)
        }.also { File(pathname).apply { writeText(it) }.createNewFile() }
    }
}