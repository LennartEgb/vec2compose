import dev.lennartegb.vec2compose.core.Injection
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.svg.vectorSetParser

object VectorSetParserFactory {

    fun create(fileExtension: String): VectorSetParser {
        val validExtensions = mapOf(
            "xml" to Injection.VectorDrawableParser,
            "svg" to vectorSetParser(pathParser = Injection.PathParser)
        )
        val extension = fileExtension.lowercase()
        require(extension in validExtensions.keys) { "No parser found for file extension: $fileExtension" }
        return validExtensions.getValue(extension)
    }
}
