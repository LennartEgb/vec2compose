package dev.lennartegb.vec2compose.core

interface ColorParser {
    fun parse(color: String): VectorSet.Path.FillColor?
}
