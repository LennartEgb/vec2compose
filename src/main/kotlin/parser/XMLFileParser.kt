package parser

import androidvector.AndroidVectorParser
import androidvector.XML
import java.io.File

internal class XMLFileParser(
    private val androidVectorParser: AndroidVectorParser,
    private val imageVectorParser: ImageVectorParser,
) {
    @kotlin.jvm.Throws
    fun parse(file: File): String {
        return XML(content = file.readText())
            .let(androidVectorParser::parse)
            .mapCatching { imageVectorParser.parse(name = file.nameWithoutExtension, vectorSet = it) }
            .getOrThrow()
    }
}