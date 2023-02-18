import java.io.File

internal class VectorSetParserFactory(private val file: File) {
  fun create(): VectorSetParser =
      when (file.extension.uppercase()) {
        "XML" -> Injection.VectorDrawableParser
        "SVG" -> Injection.SVGParser
        else -> throw IllegalArgumentException("No parser found for file ${file.name}")
      }
}
