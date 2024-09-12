package dev.lennartegb.vec2compose.app.data

data class File(val name: String, val content: String) {
    val extension: String = name.takeLastWhile { it != '.' }
}
