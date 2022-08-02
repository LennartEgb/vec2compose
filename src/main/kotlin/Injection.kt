import vectordrawable.VectorDrawableParser
import vectordrawable.VectorDrawableSerializer
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import commands.CommandParser
import commands.PathParser
import imagevector.ImageVectorParser
import fileparser.*
import svg.SVGDeserializer
import svg.SVGParser

internal object Injection {
    private val objectMapper: ObjectMapper = XmlMapper(JacksonXmlModule()).registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val CommandParser = CommandParser()
    private val PathParser = PathParser(commandParser = CommandParser)

    private val VectorDrawableSerializer = VectorDrawableSerializer(objectMapper)
    private val VectorDrawableParser = VectorDrawableParser(
        serializer = VectorDrawableSerializer,
        pathParser = PathParser
    )

    private val SVGDeserializer = SVGDeserializer(objectMapper)
    private val SVGParser = SVGParser(SVGDeserializer, PathParser)
    private val ImageVectorParser = ImageVectorParser()

    val SVGFileParser = SVGFileParser(SVGParser, ImageVectorParser)

    val XMLFileParser = XMLFileParser(
        vectorDrawableParser = VectorDrawableParser,
        imageVectorParser = ImageVectorParser
    )
}