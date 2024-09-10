package dev.lennartegb.vec2compose.core.commands

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
) : Command {
    override fun toString(): String {
        var method = "curveTo"
        if (!isAbsolute) {
            method += "Relative"
        }
        method += "(${x1}f, ${y1}f, ${x2}f, ${y2}f, ${x3}f, ${y3}f)"
        return method
    }
}
