import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import dev.lennartegb.vec2compose.svg.vectorSetParser as svgVectorSetParser
import dev.lennartegb.vec2compose.vectorDrawable.vectorSetParser as androidVectorSetParser

object VectorSetParserFactory {

    fun create(fileExtension: String): VectorSetParser {
        val validExtensions = mapOf(
            "xml" to androidVectorSetParser(pathParser = PathParser()),
            "svg" to svgVectorSetParser(pathParser = PathParser())
        )
        val extension = fileExtension.lowercase()
        require(extension in validExtensions.keys) { "No parser found for file extension: $fileExtension" }
        return validExtensions.getValue(extension)
    }
}
