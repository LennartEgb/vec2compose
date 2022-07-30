package androidvector

import models.VectorSet
import parser.parsePath

private typealias DpString = String

internal class AndroidVectorParser(private val serializer: AndroidVectorSerializer) {

    fun parse(xml: XML): Result<VectorSet> {
        val androidVector = serializer.serialize(xml = xml)
        return androidVector.map { it.toVectorSet() }
    }

    private fun AndroidVector.toVectorSet(): VectorSet {
        return VectorSet(
            width = widthInDp.toIntDp(),
            height = heightInDp.toIntDp(),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            paths = path.map { VectorSet.Path(parsePath(it.pathData)) }
        )
    }

    private fun DpString.toIntDp(): Int {
        val int = toIntOrNull()
        if (int != null) return int
        if (endsWith("dp")) {
            return dropLast(2).toIntOrNull() ?: error("Malformed dp string: $this")
        }
        error("Could not transform dp string to int: $this")
    }
}