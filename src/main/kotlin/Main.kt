import androidvector.AndroidVectorParser
import androidvector.AndroidVectorSerializer
import androidvector.XML
import parser.CommandParser
import parser.ImageVectorParser
import parser.PathParser
import java.io.File

fun main(args: Array<String>) {
    val commandParser = CommandParser()
    val pathParser = PathParser(commandParser = commandParser)
    val androidVectorSerializer = AndroidVectorSerializer()
    val androidVectorParser = AndroidVectorParser(serializer = androidVectorSerializer, pathParser = pathParser)

    val imageVectorParser = ImageVectorParser()

    // load file content
    val filePath = args.first()
    val file = File(filePath)
    if (!file.exists()) error("File does not exist: $filePath")
    XML(content = file.readText())
        .let(androidVectorParser::parse)
        .mapCatching { imageVectorParser.parse(name = file.nameWithoutExtension, vectorSet = it) }
        .onSuccess { println(it) }
        .onFailure { println("Failed with exception: ${it.message}") }
}