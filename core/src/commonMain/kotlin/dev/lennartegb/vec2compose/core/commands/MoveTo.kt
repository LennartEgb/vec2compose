package dev.lennartegb.vec2compose.core.commands

/**
 * Move to command indicated by m/M.
 */
data object MoveTo : CommandSpec {
    override val name: String = "moveTo"
    override val argsCount: Int = 2
    override fun getArguments(args: List<String>): String = "${args[0]}f, ${args[1]}f"
    operator fun invoke(x: Float, y: Float): Command = Command("M$x $y")
}
