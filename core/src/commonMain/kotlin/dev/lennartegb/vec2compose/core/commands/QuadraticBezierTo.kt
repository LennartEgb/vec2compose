package dev.lennartegb.vec2compose.core.commands

/**
 * Quadratic bezier to command indicated by q/Q
 */
data object QuadraticBezierTo : CommandSpec {
    override val name: String = "quadTo"
    override val argsCount: Int = 4
    override fun getArguments(args: List<String>): String =
        "${args[0]}f, ${args[1]}f, ${args[2]}f, ${args[3]}f"
}
