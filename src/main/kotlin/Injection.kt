import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import commands.CommandParser
import commands.PathParser
import svg.SVGDeserializer
import svg.SVGParser
import vectordrawable.VectorDrawableDeserializer
import vectordrawable.VectorDrawableParser

internal object Injection {
    private val objectMapper: ObjectMapper =
        XmlMapper(JacksonXmlModule())
            .registerKotlinModule()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val CommandParser = CommandParser()
    private val PathParser = PathParser(commandParser = CommandParser)
    private val VectorDrawableSerializer = VectorDrawableDeserializer(objectMapper)
    private val SVGDeserializer = SVGDeserializer(objectMapper)
    private val hexColorParser = HexColorParser()
    private val keywordColorParser = KeywordColorParser()
    private val combinedColorParser =
        object : ColorParser {
            override fun parse(color: String): VectorSet.Path.FillColor? =
                keywordColorParser.parse(color) ?: hexColorParser.parse(color)
        }

    val VectorDrawableParser =
        VectorDrawableParser(
            colorParser = hexColorParser,
            deserializer = VectorDrawableSerializer,
            pathParser = PathParser
        )
    val SVGParser =
        SVGParser(
            colorParser = combinedColorParser,
            deserializer = SVGDeserializer,
            pathParser = PathParser
        )
}
