package dev.lennartegb.vec2compose.svg.di

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import dev.lennartegb.vec2compose.svg.KeywordColorParser
import dev.lennartegb.vec2compose.svg.SVGColorParser
import dev.lennartegb.vec2compose.svg.SVGDeserializer
import dev.lennartegb.vec2compose.svg.SVGParser

fun vectorSetParser(pathParser: PathParser): VectorSetParser = SVGParser(
    pathParser = pathParser,
    colorParser = SVGColorParser(hexColorParser = HexColorParser(), keywordColorParser = KeywordColorParser()),
    deserializer = SVGDeserializer(),
)
