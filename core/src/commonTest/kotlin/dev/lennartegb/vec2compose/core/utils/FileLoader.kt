package dev.lennartegb.vec2compose.core.utils

expect fun readBinaryResource(resourceName: String): ByteArray

internal class FileLoader {

    fun load(name: String): String {
        return readBinaryResource(name).decodeToString()
    }
}
