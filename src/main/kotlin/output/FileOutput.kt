package output

import java.io.File

class FileOutput(
    private val pathname: String,
) : Output {
    override fun write(content: String) {
        File(pathname).apply { writeText(text = content) }.createNewFile()
    }
}