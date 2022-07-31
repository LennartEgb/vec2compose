package output

import java.io.File

class FileOutputStrategy(
    private val pathname: String,
) : OutputStrategy {
    override fun write(content: String) {
        File(pathname).apply { writeText(text = content) }.createNewFile()
    }
}