package parser

import androidvector.XML
import svg.SVGParser
import java.io.File

internal class SVGFileParser(
    private val svgParser: SVGParser,
    private val imageVectorParser: ImageVectorParser,
) {
    @kotlin.jvm.Throws
    fun parse(file: File): String {
        return XML(content = file.readText())
            .let(svgParser::parse)
            .mapCatching { imageVectorParser.parse(name = file.name, vectorSet = it) }
            .getOrThrow()
    }
}