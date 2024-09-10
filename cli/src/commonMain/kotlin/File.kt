import okio.FileSystem
import okio.Path.Companion.toPath

internal data class File(
    val name: String,
    val content: String
) {
    companion object {
        fun read(fileSystem: FileSystem, path: String): File {
            val okioPath = path.toPath()
            require(fileSystem.exists(okioPath)) { "File ${okioPath.name} does not exist" }
            return File(name = okioPath.name, content = fileSystem.read(okioPath) { readUtf8() })
        }

        fun write(fileSystem: FileSystem, path: String, content: String) {
            fileSystem.write(path.toPath()) { writeUtf8(content) }
        }
    }

    fun write(fileSystem: FileSystem, path: String) {
        write(fileSystem = fileSystem, path = path, content = content)
    }

    val extension: String get() = name.takeLastWhile { it != '.' }
}
