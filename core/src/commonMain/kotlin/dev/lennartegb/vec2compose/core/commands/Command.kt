package dev.lennartegb.vec2compose.core.commands

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
sealed interface Command {
    companion object {
        val validCommands: CharArray = charArrayOf('A', 'C', 'H', 'L', 'M', 'Q', 'S', 'T', 'V', 'Z')
            .flatMap { listOf(it, it.lowercaseChar()) }
            .toCharArray()
    }

    val isAbsolute: Boolean

    /**
     * Arc to command indicated by a/A
     */
    data class ArcTo(
        val horizontalEllipseRadius: Float,
        val verticalEllipseRadius: Float,
        val theta: Float,
        val isMoreThanHalf: Boolean,
        val isPositiveArc: Boolean,
        val x1: Float,
        val y1: Float,
        override val isAbsolute: Boolean
    ) : Command

    /**
     * Close command indicated by z/Z
     */
    data object Close : Command {
        override val isAbsolute: Boolean = true
    }

    /**
     * Move to command indicated by m/M.
     */
    data class MoveTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command

    /**
     * Line to command indicated by l/L
     */
    data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command

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
