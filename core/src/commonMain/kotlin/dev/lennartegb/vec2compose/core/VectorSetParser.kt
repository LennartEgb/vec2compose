package dev.lennartegb.vec2compose.core

interface VectorSetParser {
    fun parse(content: String): Result<VectorSet>
}
