import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import commands.CommandParser
import commands.PathParser
import svg.SVGDeserializer
import svg.SVGParser
import vectordrawable.VectorDrawableParser
import vectordrawable.VectorDrawableDeserializer

internal object Injection {
    private val objectMapper: ObjectMapper = XmlMapper(JacksonXmlModule()).registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val CommandParser = CommandParser()
    private val PathParser = PathParser(commandParser = CommandParser)
    private val VectorDrawableSerializer = VectorDrawableDeserializer(objectMapper)
    val VectorDrawableParser = VectorDrawableParser(VectorDrawableSerializer, PathParser)
    private val SVGDeserializer = SVGDeserializer(objectMapper)
    val SVGParser = SVGParser(SVGDeserializer, PathParser)
}