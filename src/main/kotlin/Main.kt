import androidvector.AndroidVectorParser
import androidvector.AndroidVectorSerializer
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import parser.CommandParser
import parser.ImageVectorParser
import parser.PathParser
import parser.XMLFileParser
import java.io.File

fun main(args: Array<String>) {
    val argParser = ArgParser(programName = "vec2compose")
    val input by argParser.option(ArgType.String, shortName = "i", description = "Input file").required()
    argParser.parse(args)

    val commandParser = CommandParser()
    val pathParser = PathParser(commandParser = commandParser)
    val androidVectorSerializer = AndroidVectorSerializer()
    val androidVectorParser = AndroidVectorParser(serializer = androidVectorSerializer, pathParser = pathParser)
    val imageVectorParser = ImageVectorParser()
    val xmlParser = XMLFileParser(androidVectorParser, imageVectorParser)

    // load file content
    val file = File(input)
    println(xmlParser.parse(file))
}