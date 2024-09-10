package dev.lennartegb.vec2compose.core.commands

/**
 * Vertical line to command indicated by v/V
 */
data class VerticalLineTo(val y: Float, override val isAbsolute: Boolean) : Command {
    override fun toString(): String {
        var method = "verticalLineTo"
        if (!isAbsolute) {
            method += "Relative"
        }
        method += "(${y}f)"
        return method
    }
}
