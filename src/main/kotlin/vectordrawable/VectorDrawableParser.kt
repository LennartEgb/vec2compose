package vectordrawable

import models.VectorSet
import commands.PathParser

private typealias DpString = String

internal class VectorDrawableParser(
    private val serializer: VectorDrawableSerializer,
    private val pathParser: PathParser,
) {

    fun parse(xml: XML): Result<VectorSet> {
        val androidVector = serializer.serialize(xml = xml)
        return androidVector.mapCatching { it.toVectorSet() }
    }

    private fun VectorDrawable.toVectorSet(): VectorSet {
        return VectorSet(
            width = widthInDp.toIntDp(),
            height = heightInDp.toIntDp(),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            paths = path.map { VectorSet.Path(pathParser.parse(it.pathData)) }
        )
    }

    private fun DpString.toIntDp(): Int {
        val int = toIntOrNull()
        if (int != null) return int
        if (endsWith("dp")) return dropLast(2).toIntOrNull()
            ?: error("Malformed dp string: $this")
        error("Could not transform dp string to int: $this")
    }
}