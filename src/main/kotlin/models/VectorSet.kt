package models

import commands.Command

internal data class VectorSet(
    val width: Int,
    val height: Int,
    val viewportWidth: Int,
    val viewportHeight: Int,
    val paths: List<Path>
) {

    data class Path(
        val fillType: FillType,
        val commands: List<Command>,
    ) {
        enum class FillType {
            NonZero,
            EvenOdd;

            companion object {
                val Default = NonZero
            }
        }
    }
}
