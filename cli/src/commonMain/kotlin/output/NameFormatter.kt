package output

internal class NameFormatter {
    private val regex = "[-_ ]".toRegex()

    fun format(name: String): String {
        val nameWithoutExtension = name.substring(
            startIndex = 0,
            endIndex = name.indexOf(char = '.').takeIf { it != -1 } ?: name.indices.last
        )
        return nameWithoutExtension.split(regex).joinToString(separator = "") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }
}
