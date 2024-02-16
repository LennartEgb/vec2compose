package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSet

internal class SVGColorParser(
    private val hexColorParser: HexColorParser,
    private val keywordColorParser: KeywordColorParser,
) {

    fun parse(color: String): VectorSet.Path.FillColor? {
        if (color == "none") return null
        return keywordColorParser.parse(color) ?: hexColorParser.parse(color, strategy = HexColorParser.Strategy.RGBA)
    }
}
