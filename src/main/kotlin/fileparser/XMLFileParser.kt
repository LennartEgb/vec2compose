package fileparser

import vectordrawable.VectorDrawableParser
import imagevector.ImageVectorParser
import java.io.File

internal class XMLFileParser(
    private val vectorDrawableParser: VectorDrawableParser,
    private val imageVectorParser: ImageVectorParser,
) : FileParser {
    override fun parse(file: File): Result<String> {
        return file.readText()
            .let(vectorDrawableParser::parse)
            .mapCatching { imageVectorParser.parse(name = file.nameWithoutExtension, vectorSet = it) }
    }
}