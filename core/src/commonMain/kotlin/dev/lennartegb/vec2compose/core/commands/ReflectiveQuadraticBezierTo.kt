package dev.lennartegb.vec2compose.core.commands

/**
 * Reflective quadratic bezier to command indicated by t/T
 */
data class ReflectiveQuadraticBezierTo(
    val x: Float,
    val y: Float,
    override val isAbsolute: Boolean
) : Command {
    override fun toString(): String {
        var method = "reflectiveQuadTo"
        if (!isAbsolute) {
            method += "Relative"
        }
        method += "(${x}f, ${y}f)"
        return method
    }
}
