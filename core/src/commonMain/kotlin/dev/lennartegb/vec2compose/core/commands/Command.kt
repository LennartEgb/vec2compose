package dev.lennartegb.vec2compose.core.commands

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
sealed interface Command {

    val isAbsolute: Boolean

    /**
     * Curve to command indicated by c/C
     */
    data class CurveTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        val x3: Float,
        val y3: Float,
        override val isAbsolute: Boolean
    ) : Command

    /**
     * Reflective curve to command indicated by s/S
     */
    data class ReflectiveCurveTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        override val isAbsolute: Boolean
    ) : Command

    /**
     * Horizontal line to command indicated by h/H
     */
    data class HorizontalLineTo(val x: Float, override val isAbsolute: Boolean) : Command

    /**
     * Vertical line to command indicated by v/V
     */
    data class VerticalLineTo(val y: Float, override val isAbsolute: Boolean) : Command

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
