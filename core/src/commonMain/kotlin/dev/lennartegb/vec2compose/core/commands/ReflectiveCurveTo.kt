package dev.lennartegb.vec2compose.core.commands

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
    override fun toString(): String {
        var method = "reflectiveCurveTo"
        if (!isAbsolute) {
            method += "Relative"
        }
        method += "(${x1}f, ${y1}f, ${x2}f, ${y2}f)"
        return method
    }
}
