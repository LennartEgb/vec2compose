internal data class File(
    val name: String,
    val content: String,
) {
    val extension: String get() = name.takeLastWhile { it != '.' }
}
