package vectordrawable

import ColorDeserializer
import commands.PathParser
import VectorSet
import VectorSetParser

private typealias DpString = String

internal class VectorDrawableParser(
    private val serializer: VectorDrawableDeserializer,
    private val pathParser: PathParser,
) : VectorSetParser {

    private val colorDeserializer = ColorDeserializer()

    override fun parse(content: String): Result<VectorSet> {
        val androidVector = serializer.deserialize(content = content)
        return androidVector.mapCatching { it.toVectorSet() }
    }

    private fun VectorDrawable.toVectorSet(): VectorSet {
        return VectorSet(
            width = widthInDp.toIntDp(),
            height = heightInDp.toIntDp(),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            paths = path.map { it.toVectorPath() },
            groups = group.map { it.toVectorGroup() }
        )
    }

    private fun VectorDrawable.Group.toVectorGroup(): VectorSet.Group {
        return VectorSet.Group(
            name = name,
            groups = group.map { it.toVectorGroup() },
            paths = path.map { it.toVectorPath() },
        )
    }

    private fun VectorDrawable.Path.toVectorPath(): VectorSet.Path {
        return VectorSet.Path(
            fillType = VectorSet.Path.FillType.parse(fillType),
            commands = pathParser.parse(pathData),
            fillColor = fillColor?.let(colorDeserializer::deserialize)
        )
    }

    private fun DpString.toIntDp(): Int {
        val int = toIntOrNull()
        if (int != null) return int
        if (endsWith("dp")) return dropLast(2).toIntOrNull()
            ?: error("Malformed dp string: $this")
        error("Could not transform dp string to int: $this")
    }

    private fun VectorSet.Path.FillType.Companion.parse(fillType: String): VectorSet.Path.FillType {
        return when (fillType) {
            "evenOdd" -> VectorSet.Path.FillType.EvenOdd
            "nonZero" -> VectorSet.Path.FillType.NonZero
            else -> VectorSet.Path.FillType.Default
        }
    }
}

