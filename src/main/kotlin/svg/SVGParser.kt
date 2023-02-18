package svg

import ColorParser
import VectorSet
import VectorSetParser
import commands.PathParser

internal class SVGParser(
    private val colorParser: ColorParser,
    private val deserializer: SVGDeserializer,
    private val pathParser: PathParser
) : VectorSetParser {

    override fun parse(content: String): Result<VectorSet> {
        return deserializer.deserialize(content).mapCatching { it.toVectorSet() }
    }

    private fun SVG.toVectorSet(): VectorSet {
        val width = width.filter { it.isDigit() }.toInt()
        val height = height.filter { it.isDigit() }.toInt()
        val rect: List<Float> = viewBox?.split(" ")
            ?.map { it.toFloat() }
            ?: listOf(0f, 0f, width.toFloat(), height.toFloat())
        return VectorSet(
            width = width,
            height = height,
            viewportWidth = rect[2] - rect[0],
            viewportHeight = rect[3] - rect[1],
            paths = path.map { it.toVectorPath() },
            groups = g.map { it.toVectorGroup() }
        )
    }

    private fun SVG.Group.toVectorGroup(): VectorSet.Group {
        return VectorSet.Group(
            name = name,
            groups = g.map { it.toVectorGroup() },
            paths = path.map { it.toVectorPath() }
        )
    }

    private fun SVG.Path.toVectorPath(): VectorSet.Path {
        return VectorSet.Path(
            fillType = parseFillType(fillRule),
            commands = pathParser.parse(pathData),
            fillColor = fill?.let(colorParser::parse),
            alpha = fillOpacity
        )
    }

    private fun parseFillType(fillRule: String): VectorSet.Path.FillType {
        return when (fillRule) {
            "evenodd" -> VectorSet.Path.FillType.EvenOdd
            "nonzero" -> VectorSet.Path.FillType.NonZero
            else -> VectorSet.Path.FillType.Default
        }
    }
}
