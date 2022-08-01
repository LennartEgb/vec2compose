package svg

import androidvector.XML
import models.VectorSet
import parser.PathParser

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
            paths = path.map { it.toVectorSetPath() },
        )
    }

    private fun SVG.Path.toVectorSetPath(): VectorSet.Path {
        val path = buildString {
            pathData.forEachIndexed { index, c ->
                val next = pathData.getOrNull(index + 1) ?: return@buildString
                append(c)
                if (c.isDigit() && next == '-') append(' ')
            }
        }
        return VectorSet.Path(commands = pathParser.parse(path))
    }
}

