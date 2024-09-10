import kotlinx.io.buffered
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.readString
import kotlinx.io.writeString

internal data class File(
    val name: String,
    val content: String
) {
    companion object {
        fun read(fileSystem: FileSystem, path: String): File {
            val ioPath = Path(path)
            require(fileSystem.exists(ioPath)) { "File ${ioPath.name} does not exist" }
            val source = fileSystem.source(ioPath)
            val content = source.buffered().readString()
            source.close()
            return File(name = ioPath.name, content = content)
        }

        fun write(fileSystem: FileSystem, path: String, content: String) {
            val sink = fileSystem.sink(path = Path(path)).buffered()
            sink.writeString(content)
            sink.close()
        }
    }

    fun write(fileSystem: FileSystem, path: String) {
        write(fileSystem = fileSystem, path = path, content = content)
    }

    val nameWithoutExtension: String
        get() {
            val regex = "[-_ ]".toRegex()
            return name.takeWhile { it != '.' }.split(regex).joinToString(separator = "") { word ->
                word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
        }
    val extension: String get() = name.takeLastWhile { it != '.' }
}
