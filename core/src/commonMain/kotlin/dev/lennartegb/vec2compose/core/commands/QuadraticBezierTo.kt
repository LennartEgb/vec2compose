package dev.lennartegb.vec2compose.core.commands

/**
 * Quadratic bezier to command indicated by q/Q
 */
data class QuadraticBezierTo(
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
    override val isAbsolute: Boolean
) : Command
