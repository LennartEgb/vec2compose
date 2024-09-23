package dev.lennartegb.vec2compose.core.commands

internal interface CommandSpec {
    val name: String
    val argsCount: Int
    fun getArguments(args: List<String>): String
}
