package fileparser

import androidvector.AndroidVectorParser
import androidvector.XML
import imagevector.ImageVectorParser
import java.io.File

internal class XMLFileParser(
    private val androidVectorParser: AndroidVectorParser,
    private val imageVectorParser: ImageVectorParser,
) : FileParser {
    override fun parse(file: File): Result<String> {
        return XML(content = file.readText())
            .let(androidVectorParser::parse)
            .mapCatching { imageVectorParser.parse(name = file.nameWithoutExtension, vectorSet = it) }
    }
}