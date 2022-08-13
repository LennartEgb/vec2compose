package models

import commands.Command

internal data class VectorSet(
    val width: Int,
    val height: Int,
    val viewportWidth: Int,
    val viewportHeight: Int,
    val groups: List<Group>,
    val paths: List<Path>
) {

    data class Group(
        val name: String?,
        val groups: List<Group>,
        val paths: List<Path>,
    )

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
