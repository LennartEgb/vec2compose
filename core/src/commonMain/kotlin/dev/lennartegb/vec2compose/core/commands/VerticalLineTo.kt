package dev.lennartegb.vec2compose.core.commands

/**
 * Vertical line to command indicated by v/V
 */
data class VerticalLineTo(val y: Float, override val isAbsolute: Boolean) : Command
