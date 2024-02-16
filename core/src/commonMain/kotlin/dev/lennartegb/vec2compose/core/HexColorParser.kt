package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.HexColorParser.Strategy.ARGB
import dev.lennartegb.vec2compose.core.HexColorParser.Strategy.RGBA

class HexColorParser {

    enum class Strategy { ARGB, RGBA }

    fun parse(color: String, strategy: Strategy = ARGB): VectorSet.Path.FillColor? {
        if (color.startsWith("#")) return parse(color.drop(1), strategy)
        return when (color.length) {
            3, 4 -> parse(color.fold(initial = "") { acc, char -> "$acc$char$char" }, strategy)
            6 -> when (strategy) {
                ARGB -> "FF$color"
                RGBA -> "${color}FF"
            }.let { parse(it, strategy) }

            8 -> color.windowed(size = 2, step = 2).let { (first, second, third, fourth) ->
                val (alpha, red, green, blue) = when (strategy) {
                    ARGB -> listOf(first, second, third, fourth)
                    RGBA -> listOf(fourth, first, second, third)
                }
                VectorSet.Path.FillColor(
                    red = red.toInt(16),
                    green = green.toInt(16),
                    blue = blue.toInt(16),
                    alpha = alpha.toInt(16)
                )
            }

            else -> null
        }
    }
}
