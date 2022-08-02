package commands

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
internal sealed interface Command {
    companion object {
        val validCommands: CharArray = charArrayOf('A', 'C', 'H', 'L', 'M', 'Q', 'S', 'T', 'V', 'Z')
            .flatMap { listOf(it, it.lowercaseChar()) }
            .toCharArray()
    }

    val isAbsolute: Boolean
    fun values(): List<Float>

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
    ) : Command {
        override fun values(): List<Float> = error("Complex data structure can't return float values.")
    }

    /**
     * Close command indicated by z/Z
     */
    object Close : Command {
        override val isAbsolute: Boolean = true
        override fun values(): List<Float> = emptyList()

    }

    /**
     * Move to command indicated by m/M.
     */
    data class MoveTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(x, y)
    }

    /**
     * Line to command indicated by l/L
     */
    data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(x, y)
    }

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
        override val isAbsolute: Boolean,
    ) : Command {
        override fun values(): List<Float> = listOf(x1, y1, x2, y2, x3, y3)
    }

    /**
     * Reflective curve to command indicated by s/S
     */
    data class ReflectiveCurveTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        override val isAbsolute: Boolean
    ) : Command {
        override fun values(): List<Float> = listOf(x1, y1, x2, y2)
    }

    /**
     * Horizontal line to command indicated by h/H
     */
    data class HorizontalLineTo(val x: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(x)
    }

    /**
     * Vertical line to command indicated by v/V
     */
    data class VerticalLineTo(val y: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(y)
    }

    /**
     * Quadratic bezier to command indicated by q/Q
     */
    data class QuadraticBezierTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        override val isAbsolute: Boolean
    ) : Command {
        override fun values(): List<Float> = listOf(x1, y1, x2, y2)
    }

    /**
     * Reflective quadratic bezier to command indicated by t/T
     */
    data class ReflectiveQuadraticBezierTo(
        val x: Float,
        val y: Float,
        override val isAbsolute: Boolean,
    ) : Command {
        override fun values(): List<Float> = listOf(x, y)
    }
}