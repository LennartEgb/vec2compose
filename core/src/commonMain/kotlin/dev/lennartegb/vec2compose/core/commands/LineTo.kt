package dev.lennartegb.vec2compose.core.commands

/**
 * Line to command indicated by l/L
 */
data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command
