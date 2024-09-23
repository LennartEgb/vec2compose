package dev.lennartegb.vec2compose.core.commands

/**
 * Line to command indicated by l/L
 */
data object LineTo : CommandSpec {
    override val name: String = "lineTo"
    override val argsCount: Int = 2
    override fun getArguments(args: List<String>): String = "${args[0]}f, ${args[1]}f"
    operator fun invoke(x: Float, y: Float): Command = Command("L$x $y")
}
