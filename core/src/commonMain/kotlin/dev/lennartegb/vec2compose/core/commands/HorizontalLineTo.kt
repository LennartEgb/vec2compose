package dev.lennartegb.vec2compose.core.commands

/**
 * Horizontal line to command indicated by h/H
 */
data object HorizontalLineTo : CommandSpec {
    override val name: String = "horizontalLineTo"
    override val argsCount: Int = 1
    override fun getArguments(args: List<String>): String = "${args[0]}f"
}
