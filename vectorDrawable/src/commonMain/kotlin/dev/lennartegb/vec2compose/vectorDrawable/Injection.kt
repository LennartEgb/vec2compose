package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser

fun vectorSetParser(pathParser: PathParser): VectorSetParser = VectorDrawableParser(
    pathParser = pathParser,
    colorParser = HexColorParser(),
)
