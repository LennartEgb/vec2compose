package dev.lennartegb.vec2compose.core.commands

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
    override fun toString(): String {
        var method = "arcTo"
        if (!isAbsolute) {
            method += "Relative"
        }
        method += "(${horizontalEllipseRadius}f, ${verticalEllipseRadius}f, ${theta}f" +
            ", $isMoreThanHalf, $isPositiveArc, ${x1}f, ${y1}f)"
        return method
    }
}
