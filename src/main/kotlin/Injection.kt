import androidvector.AndroidVectorParser
import androidvector.AndroidVectorSerializer
import parser.*
import svg.SVGDeserializer
import svg.SVGParser

internal object Injection {
    private val CommandParser = CommandParser()
    private val PathParser = PathParser(commandParser = CommandParser)

    private val AndroidVectorSerializer = AndroidVectorSerializer()
    private val AndroidVectorParser = AndroidVectorParser(
        serializer = AndroidVectorSerializer,
        pathParser = PathParser
    )

    private val SVGDeserializer = SVGDeserializer()
    private val SVGParser = SVGParser(SVGDeserializer, PathParser)
    private val ImageVectorParser = ImageVectorParser()

    val SVGFileParser = SVGFileParser(SVGParser, ImageVectorParser)

    val XMLFileParser = XMLFileParser(
        androidVectorParser = AndroidVectorParser,
        imageVectorParser = ImageVectorParser
    )
}