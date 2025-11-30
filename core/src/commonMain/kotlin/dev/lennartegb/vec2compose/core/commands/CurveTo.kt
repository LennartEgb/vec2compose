package dev.lennartegb.vec2compose.core.commands

/**
 * Curve to command indicated by c/C
 */
data object CurveTo : CommandSpec {
    override val name: String = "curveTo"
    override val argsCount: Int = 6
    override fun getArguments(args: List<String>): String = "${args[0]}f, ${args[1]}f, ${args[2]}f, ${args[3]}f, ${args[4]}f, ${args[5]}f"
}
