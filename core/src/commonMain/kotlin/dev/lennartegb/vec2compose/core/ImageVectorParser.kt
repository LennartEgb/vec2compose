package dev.lennartegb.vec2compose.core

interface ImageVectorParser {
    fun parse(content: String): Result<ImageVector>
}
