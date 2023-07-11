import commands.CommandParser
import commands.PathParser
import svg.SVGDeserializer
import svg.SVGParser
import vectordrawable.VectorDrawableDeserializer
import vectordrawable.VectorDrawableParser

internal object Injection {

    private val CommandParser = CommandParser()
    private val PathParser = PathParser(commandParser = CommandParser)
    private val VectorDrawableDeserializer = VectorDrawableDeserializer()
    private val SVGDeserializer = SVGDeserializer()
    private val hexColorParser: ColorParser = HexColorParser()
    private val keywordColorParser: ColorParser = KeywordColorParser()
    private val combinedColorParser = object : ColorParser {
        override fun parse(color: String): VectorSet.Path.FillColor? = keywordColorParser.parse(color)
            ?: hexColorParser.parse(color)
    }

    val VectorDrawableParser: VectorSetParser = VectorDrawableParser(
        colorParser = hexColorParser,
        deserializer = VectorDrawableDeserializer,
        pathParser = PathParser
    )
    val SVGParser: VectorSetParser = SVGParser(
        colorParser = combinedColorParser,
        deserializer = SVGDeserializer,
        pathParser = PathParser
    )
}
