package dev.lennartegb.vec2compose.core.commands

/**
 * Commands for path data for vector formats found [here](https://www.w3.org/TR/SVG/paths.html#PathData)
 */
sealed interface Command {
    val isAbsolute: Boolean
}
