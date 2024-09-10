package dev.lennartegb.vec2compose.core.commands

/**
 * Line to command indicated by l/L
 */
data class LineTo(val x: Float, val y: Float, override val isAbsolute: Boolean) : Command {
    override fun toString(): String {
        var method = "lineTo"
        if (!isAbsolute) {
            method += "Relative"
        }
        method += "(${x}f, ${y}f)"
        return method
    }
}
