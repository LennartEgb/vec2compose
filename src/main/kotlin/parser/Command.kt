package parser

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
sealed interface Command {
    companion object {
        val chars = listOf(
            'C',
            'c',
            'H',
            'h',
            'L',
            'l',
            'M',
            'm',
            'S',
            's',
            'V',
            'v',
            'Z',
            'z',
        )
    }

    val isAbsolute: Boolean

    // Z
    data class Close(override val isAbsolute: Boolean) : Command

    // M
    data class MoveTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command

    // L
    data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command

    // S
    data class ReflectiveCurveTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        override val isAbsolute: Boolean
    ) : Command

    // C
    data class CurveTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        val x3: Float,
        val y3: Float,
        override val isAbsolute: Boolean,
    ) : Command

    // H
    data class HorizontalLineTo(val x: Float, override val isAbsolute: Boolean) : Command

    // V
    data class VerticalLineTo(val y: Float, override val isAbsolute: Boolean) : Command
}