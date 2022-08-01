package parser

import androidvector.XML
import svg.SVGParser
import java.io.File

internal class SVGFileParser(
    private val svgParser: SVGParser,
    private val imageVectorParser: ImageVectorParser,
) : FileParser {
    override fun parse(file: File): Result<String> {
        return XML(content = file.readText())
            .let(svgParser::parse)
            .mapCatching { imageVectorParser.parse(name = file.nameWithoutExtension, vectorSet = it) }
    }
}