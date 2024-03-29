import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

internal class FileReader(private val fileSystem: FileSystem) {

    fun read(path: String): File {
        val okioPath = path.toPath()
        require(fileSystem.exists(okioPath)) { "File ${okioPath.name} does not exist" }
        return File(name = okioPath.name, content = fileSystem.read(okioPath) { readUtf8() })
    }
}
