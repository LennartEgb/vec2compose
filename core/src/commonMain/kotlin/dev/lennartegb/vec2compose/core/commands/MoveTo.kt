package dev.lennartegb.vec2compose.core.commands

/**
 * Move to command indicated by m/M.
 */
data class MoveTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command
