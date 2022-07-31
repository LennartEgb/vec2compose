package utils

import java.io.File

class FileLoader {
    fun load(name: String): File {
        val resource = javaClass.classLoader.getResource(name)
            ?: error("Resource $name does not exist.")
        return File(resource.file)
    }
}