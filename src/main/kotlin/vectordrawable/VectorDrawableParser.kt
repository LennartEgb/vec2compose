package vectordrawable

import ColorParser
import VectorSet
import VectorSetParser
import commands.PathParser

private typealias DpString = String

internal class VectorDrawableParser(
    private val colorParser: ColorParser,
    private val deserializer: VectorDrawableDeserializer,
    private val pathParser: PathParser
) : VectorSetParser {

    override fun parse(content: String): Result<VectorSet> {
        val androidVector = deserializer.deserialize(content = content)
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
            rotate = rotation ?: 0f
        )
    }

    private fun VectorDrawable.Path.toVectorPath(): VectorSet.Path {
        return VectorSet.Path(
            fillType = VectorSet.Path.FillType.parse(fillType),
            commands = pathParser.parse(pathData),
            fillColor = fillColor?.let(colorParser::parse),
            alpha = alpha
        )
    }

    private fun DpString.toIntDp(): Int {
        val int = toIntOrNull()
        if (int != null) return int
        if (endsWith("dp")) {
            return dropLast(2).toIntOrNull()
                ?: error("Malformed dp string: $this")
        }
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
