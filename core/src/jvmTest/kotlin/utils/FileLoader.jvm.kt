package utils

actual fun readBinaryResource(resourceName: String): ByteArray {
    return requireNotNull(ClassLoader.getSystemResourceAsStream(resourceName)).readBytes()
}
