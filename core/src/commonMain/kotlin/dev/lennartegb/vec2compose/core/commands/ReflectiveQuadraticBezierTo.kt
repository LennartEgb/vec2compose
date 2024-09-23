package dev.lennartegb.vec2compose.core.commands

/**
 * Reflective quadratic bezier to command indicated by t/T
 */
data object ReflectiveQuadraticBezierTo : CommandSpec {
    override val name: String = "reflectiveQuadTo"
    override val argsCount: Int = 2
    override fun getArguments(args: List<String>): String = "${args[0]}f, ${args[1]}f"
}
