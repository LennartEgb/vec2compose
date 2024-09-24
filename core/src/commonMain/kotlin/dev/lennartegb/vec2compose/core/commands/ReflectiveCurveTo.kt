package dev.lennartegb.vec2compose.core.commands

/**
 * Reflective curve to command indicated by s/S
 */
data object ReflectiveCurveTo : CommandSpec {
    override val name: String = "reflectiveCurveTo"
    override val argsCount: Int = 4
    override fun getArguments(args: List<String>): String =
        "${args[0]}f, ${args[1]}f, ${args[2]}f, ${args[3]}f"
}
