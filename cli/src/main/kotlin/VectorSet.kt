import commands.Command

internal data class VectorSet(
    val width: Int,
    val height: Int,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val groups: List<Group>,
    val paths: List<Path>
) {

    data class Group(
        val name: String?,
        val groups: List<Group>,
        val paths: List<Path>,
        val rotate: Float,
        val pivot: Translation,
        val translation: Translation,
        val scale: Scale
    )

    data class Path(
        val fillType: FillType,
        val fillColor: FillColor? = null,
        val commands: List<Command>,
        val alpha: Float
    ) {
        enum class FillType {
            NonZero,
            EvenOdd;

            companion object {
                val Default = NonZero
            }
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
