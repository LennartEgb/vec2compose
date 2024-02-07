package dev.lennartegb.vec2compose.core.utils

actual fun readBinaryResource(resourceName: String): ByteArray {
    return requireNotNull(ClassLoader.getSystemResourceAsStream(resourceName)).readBytes()
}
