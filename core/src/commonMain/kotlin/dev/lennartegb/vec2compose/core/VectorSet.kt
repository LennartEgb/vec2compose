package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.Command

data class VectorSet(
    val width: Int,
    val height: Int,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val nodes: List<Node>
) {

    sealed interface Node

    data class Group(
        val name: String?,
        val nodes: List<Node>,
        val rotate: Float,
        val pivot: Translation,
        val translation: Translation,
        val scale: Scale
    ) : Node

    data class Path(
        val fillType: FillType,
        val fillColor: FillColor? = null,
        val commands: List<Command>,
        val alpha: Float,
        val stroke: Stroke = Stroke()
    ) : Node {
        enum class FillType {
            NonZero,
            EvenOdd;

            companion object {
                val Default = NonZero
            }
        }

        data class Stroke(
            val color: FillColor? = null,
            val alpha: Float = 1f,
            val width: Float = 1f,
            val cap: Cap = Cap.Butt,
            val join: Join = Join.Bevel,
            val miter: Float = 1f
        ) {

            // NOTE: constructor with null values to fall back to default values.
            constructor(color: FillColor?, alpha: Float?, width: Float?, cap: Cap?, join: Join?, miter: Float?) : this(
                color = color,
                alpha = alpha ?: 1f,
                width = width ?: 1f,
                cap = cap ?: Cap.Butt,
                join = join ?: Join.Bevel,
                miter = miter ?: 1f
            )

            enum class Cap { Butt, Round, Square }
            enum class Join { Bevel, Miter, Round }
        }

        data class FillColor(val red: Int, val green: Int, val blue: Int, val alpha: Int) {
            override fun toString(): String {
                fun String.format() = if (length < 2) padStart(length = 2, padChar = '0') else this
                return buildString {
                    append("Color(0x")
                    append(alpha.toString(16).format())
                    append(red.toString(16).format())
                    append(green.toString(16).format())
                    append(blue.toString(16).format())
                    append(")")
                }
            }
        }
    }
}
