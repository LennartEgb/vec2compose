package fileparser

import Injection
import java.io.File

internal object FileParserFactory {
    fun createFileParser(file: File): FileParser {
        return when (file.extension.uppercase()) {
            "XML" -> Injection.XMLFileParser
            "SVG" -> Injection.SVGFileParser
            else -> throw IllegalArgumentException("No parser found for file $file")
        }
    }
}