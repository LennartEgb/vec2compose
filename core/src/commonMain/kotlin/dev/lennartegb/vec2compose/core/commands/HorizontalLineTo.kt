package dev.lennartegb.vec2compose.core.commands

/**
 * Horizontal line to command indicated by h/H
 */
data class HorizontalLineTo(val x: Float, override val isAbsolute: Boolean) : Command