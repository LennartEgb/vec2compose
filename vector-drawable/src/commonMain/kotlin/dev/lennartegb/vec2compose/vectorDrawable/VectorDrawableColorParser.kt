package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.ImageVector

internal class VectorDrawableColorParser(
    private val hexColorParser: HexColorParser = HexColorParser(),
    private val androidColorParser: AndroidColorParser = AndroidColorParser(hexColorParser)
) {
    fun parse(value: String): ImageVector.Path.FillColor? =
        androidColorParser.parse(value) ?: hexColorParser.parse(value)
}
