package svg

import commands.PathParser
import models.VectorSet
import vectordrawable.XML

internal class SVGParser(
    private val deserializer: SVGDeserializer,
    private val pathParser: PathParser,
) {

    fun parse(xml: XML): Result<VectorSet> {
        return deserializer.deserialize(xml).mapCatching { it.toVectorSet() }
    }

    private fun SVG.toVectorSet(): VectorSet {
        val rect = viewBox.split(" ").map { it.toInt() }
        return VectorSet(
            width = width.filter { it.isDigit() }.toInt(),
            height = height.filter { it.isDigit() }.toInt(),
            viewportWidth = rect[2] - rect[0],
            viewportHeight = rect[3] - rect[1],
            paths = path.map {
                VectorSet.Path(fillType = parseFillType(it.fillRule), commands = pathParser.parse(it.pathData))
            },
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

