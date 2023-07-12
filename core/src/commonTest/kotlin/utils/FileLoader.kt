package utils

import okio.FileSystem
import okio.Path.Companion.toPath

internal class FileLoader(private val fileSystem: FileSystem = FileSystem.RESOURCES) {

    fun load(name: String): String {
        return fileSystem.read(name.toPath()) { readUtf8() }
    }
}
