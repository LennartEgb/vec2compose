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
    fun values(): List<Float>

    // Z
    data class Close(override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = emptyList()

    }

    // M
    data class MoveTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(x, y)
    }

    // L
    data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(x, y)
    }

    // S
    data class ReflectiveCurveTo(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        override val isAbsolute: Boolean
    ) : Command {
        override fun values(): List<Float> = listOf(x1, y1, x2, y2)
    }

    // C
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

    // H
    data class HorizontalLineTo(val x: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(x)
    }

    // V
    data class VerticalLineTo(val y: Float, override val isAbsolute: Boolean) : Command {
        override fun values(): List<Float> = listOf(y)
    }
}