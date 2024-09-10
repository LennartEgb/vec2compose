package dev.lennartegb.vec2compose.core.commands

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
sealed interface Command {

    val isAbsolute: Boolean

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

    /**
     * Reflective quadratic bezier to command indicated by t/T
     */
    data class ReflectiveQuadraticBezierTo(
        val x: Float,
        val y: Float,
        override val isAbsolute: Boolean
    ) : Command
}
