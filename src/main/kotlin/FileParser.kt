import imagevector.ImageVectorParser
import java.io.File

internal class FileParser(
    private val vectorSetParser: VectorSetParser,
    private val imageVectorParser: ImageVectorParser,
) {
    fun parse(file: File): Result<String> {
        return file.readText().let(vectorSetParser::parse).mapCatching {
            imageVectorParser.parse(name = file.nameWithoutExtension, vectorSet = it)
        }
    }
}
