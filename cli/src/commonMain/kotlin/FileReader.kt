import okio.FileSystem
import okio.Path

internal class FileReader(private val fileSystem: FileSystem) {

    fun read(path: Path): File {
        require(fileSystem.exists(path)) { "File ${path.name} does not exist" }
        return File(name = path.name, content = fileSystem.read(path) { readUtf8() })
    }
}
