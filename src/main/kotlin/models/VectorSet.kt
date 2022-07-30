package models

import parser.Command

data class VectorSet(
    val width: Int,
    val height: Int,
    val viewportWidth: Int,
    val viewportHeight: Int,
    val paths: List<Path>
) {
    data class Path(val commands: List<Command>)
}
