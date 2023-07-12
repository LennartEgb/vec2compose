import imagevector.ImageVectorParser

class FileParser(
    private val vectorSetParser: VectorSetParser,
    private val imageVectorParser: ImageVectorParser
) {
    fun parse(name: String, content: String): Result<String> {
        return vectorSetParser.parse(content)
            .mapCatching { imageVectorParser.parse(name = name, vectorSet = it) }
    }
}
