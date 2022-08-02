package models

import commands.Command

internal data class VectorSet(
    val width: Int,
    val height: Int,
    val viewportWidth: Int,
    val viewportHeight: Int,
    val paths: List<Path>
) {
    internal data class Path(val commands: List<Command>)
}
