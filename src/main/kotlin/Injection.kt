import androidvector.AndroidVectorParser
import androidvector.AndroidVectorSerializer
import parser.CommandParser
import parser.ImageVectorParser
import parser.PathParser
import parser.XMLFileParser

internal object Injection {
    private val CommandParser = CommandParser()
    private val PathParser = PathParser(commandParser = CommandParser)

    private val AndroidVectorSerializer = AndroidVectorSerializer()
    private val AndroidVectorParser = AndroidVectorParser(
        serializer = AndroidVectorSerializer,
        pathParser = PathParser
    )

    private val ImageVectorParser = ImageVectorParser()

    val XMLFileParser = XMLFileParser(
        androidVectorParser = AndroidVectorParser,
        imageVectorParser = ImageVectorParser
    )
}