package dev.lennartegb.vec2compose.vectorDrawable.di

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import dev.lennartegb.vec2compose.vectorDrawable.VectorDrawableDeserializer
import dev.lennartegb.vec2compose.vectorDrawable.VectorDrawableParser

fun vectorSetParser(pathParser: PathParser): VectorSetParser = VectorDrawableParser(
    pathParser = pathParser,
    colorParser = HexColorParser(),
    deserializer = VectorDrawableDeserializer(),
)
