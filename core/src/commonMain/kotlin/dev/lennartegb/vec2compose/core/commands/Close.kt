package dev.lennartegb.vec2compose.core.commands

/**
 * Close command indicated by z/Z
 */
data object Close : Command {
    override val isAbsolute: Boolean = true
}
