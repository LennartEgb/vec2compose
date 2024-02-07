package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.ColorParser
import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser

fun vectorSetParser(
    pathParser: PathParser
): VectorSetParser = SVGParser(
    pathParser = pathParser,
    colorParser = object : ColorParser {
        private val hexParser = HexColorParser()
        private val keywordColorParser = KeywordColorParser()
        override fun parse(color: String): VectorSet.Path.FillColor? {
            return hexParser.parse(color) ?: keywordColorParser.parse(color)
        }
    }
)
