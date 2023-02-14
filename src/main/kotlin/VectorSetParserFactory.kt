import java.io.File

internal object VectorSetParserFactory {
    fun createVectorSetParser(file: File): VectorSetParser {
        return when (file.extension.uppercase()) {
            "XML" -> Injection.VectorDrawableParser
            "SVG" -> Injection.SVGParser
            else -> throw IllegalArgumentException("No parser found for file ${file.name}")
        }
    }
}