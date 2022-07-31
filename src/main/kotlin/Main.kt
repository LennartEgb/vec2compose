import androidvector.AndroidVectorParser
import androidvector.AndroidVectorSerializer
import parser.CommandParser
import parser.ImageVectorParser
import parser.PathParser
import parser.XMLFileParser
import java.io.File

fun main(args: Array<String>) {
    val commandParser = CommandParser()
    val pathParser = PathParser(commandParser = commandParser)
    val androidVectorSerializer = AndroidVectorSerializer()
    val androidVectorParser = AndroidVectorParser(serializer = androidVectorSerializer, pathParser = pathParser)
    val imageVectorParser = ImageVectorParser()
    val xmlParser = XMLFileParser(androidVectorParser, imageVectorParser)

    // load file content
    val filePath = args.first()
    val file = File(filePath)
    println(xmlParser.parse(file))
}