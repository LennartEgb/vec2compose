package dev.lennartegb.vec2compose.core.commands

/**
 * Vertical line to command indicated by v/V
 */
data object VerticalLineTo : CommandSpec {
    override val name: String = "verticalLineTo"
    override val argsCount: Int = 1
    override fun getArguments(args: List<String>): String = "${args[0]}f"
}
