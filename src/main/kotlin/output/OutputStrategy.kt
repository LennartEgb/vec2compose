package output

fun interface OutputStrategy {
    fun write(content: String)
}
