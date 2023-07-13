package utils

actual fun readBinaryResource(resourceName: String): ByteArray {
    error("Resource loading on JS is not supported")
}
