package dev.lennartegb.vec2compose.core.commands

/**
 * Close command indicated by z/Z
 */
data object Close : CommandSpec {
    override fun toString(): String = "$name()"
    override val name: String = "close"
    override val argsCount: Int = 0
    override fun getArguments(args: List<String>): String = ""
    operator fun invoke(): Command = Command("Z")
}
