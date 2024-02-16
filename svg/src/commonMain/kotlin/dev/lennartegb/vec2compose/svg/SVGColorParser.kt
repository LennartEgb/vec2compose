package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSet

internal class SVGColorParser(
    private val hexColorParser: HexColorParser,
    private val keywordColorParser: KeywordColorParser,
) {

    fun parse(color: String): VectorSet.Path.FillColor? = hexColorParser.parse(color) ?: keywordColorParser.parse(color)
}
