package dev.lennartegb.vec2compose.core.commands

/**
 * Arc to command indicated by a/A
 */
data object ArcTo : CommandSpec {
    override val name: String = "arcTo"
    override val argsCount: Int = 7

    override fun getArguments(args: List<String>): String =
        "${args[0]}f, ${args[1]}f, ${args[2]}f, ${args[3] == "1"}, ${args[4] == "1"}, ${args[5]}f, ${args[6]}f"

    operator fun invoke(
        horizontalEllipseRadius: Float,
        verticalEllipseRadius: Float,
        theta: Float,
        isMoreThanHalf: Boolean,
        isPositiveArc: Boolean,
        x1: Float,
        y1: Float
    ): Command =
        Command(
            "A$horizontalEllipseRadius,$verticalEllipseRadius,$theta " +
                "${isMoreThanHalf.toNum()},${isPositiveArc.toNum()} $x1 $y1"
        )

    private fun Boolean.toNum() = if (this) "1" else "0"
}
