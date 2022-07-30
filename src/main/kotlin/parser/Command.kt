package parser

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
sealed interface Command {
    companion object {
        val chars = listOf(
            'Z',
            'z',
            'M',
            'm',
            'L',
            'l',
            'C',
            'c',
            'H',
            'h',
            'V',
            'v',
        )
    }

    val isAbsolute: Boolean

    // Z
    data class Close(override val isAbsolute: Boolean) : Command

    // M
    data class MoveTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command

    // L
    data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command

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