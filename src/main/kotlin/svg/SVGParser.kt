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
            paths = path.map { it.toVectorPath() },
            groups = g.map { it.toVectorGroup() }
        )
    }

    private fun SVG.Group.toVectorGroup(): VectorSet.Group {
        return VectorSet.Group(
            name = name,
            groups = g.map { it.toVectorGroup() },
            paths = path.map { it.toVectorPath() },
        )
    }

    private fun SVG.Path.toVectorPath(): VectorSet.Path {
        return VectorSet.Path(
            fillType = parseFillType(fillRule),
            commands = pathParser.parse(pathData)
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

