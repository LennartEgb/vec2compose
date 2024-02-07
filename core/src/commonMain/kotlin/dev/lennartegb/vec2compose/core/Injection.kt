package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.CommandParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import vectordrawable.VectorDrawableParser

object Injection {

    private val CommandParser = CommandParser()
    val PathParser = PathParser(commandParser = CommandParser)
    private val hexColorParser: ColorParser = HexColorParser()

    val VectorDrawableParser: VectorSetParser = VectorDrawableParser(
        colorParser = hexColorParser,
        pathParser = PathParser
    )
}
