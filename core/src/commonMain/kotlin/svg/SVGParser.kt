package svg

import dev.lennartegb.vec2compose.core.ColorParser
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser

internal class SVGParser(
    private val colorParser: ColorParser,
    private val pathParser: PathParser,
    private val deserializer: SVGDeserializer = SVGDeserializer(),
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
            paths = path.map { it.toVectorPath() },
            rotate = transform?.getRotation() ?: 0f,
            pivot = transform?.getPivot() ?: Translation(0f, 0f),
            translation = transform?.getTranslation() ?: Translation(0f, 0f),
            scale = transform?.getScale() ?: Scale(1f, 1f)
        )
    }

    private fun SVG.Path.toVectorPath(): VectorSet.Path {
        // NOTE: Only when fill is null use black FillColor as default color. If fill="none" use null.
        val fillColor: VectorSet.Path.FillColor? = if (fill == null) {
            VectorSet.Path.FillColor(red = 0, green = 0, blue = 0, alpha = 0xff)
        } else {
            colorParser.parse(fill)
        }
        return VectorSet.Path(
            fillType = parseFillType(fillRule),
            commands = pathParser.parse(pathData),
            fillColor = fillColor,
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

    private fun String.getRotation(): Float {
        return getFunction("rotate")?.let { (a, _, _) -> a } ?: 0f
    }

    private fun String.getPivot(): Translation {
        return getFunction("rotate")?.let { (_, x, y) -> Translation(x = x, y = y) } ?: Translation(0f, 0f)
    }

    private fun String.getTranslation(): Translation {
        return getFunction("translate")?.let { (x, y) -> Translation(x = x, y = y) } ?: Translation(0f, 0f)
    }

    private fun String.getScale(): Scale {
        return getFunction("scale")?.let { (x, y) -> Scale(x = x, y = y) } ?: Scale(0f, 0f)
    }

    private fun String.getFunction(key: String): List<Float>? {
        val startIndex = indexOf(key).takeIf { it != -1 } ?: return null
        val functionStart = substring(startIndex + key.length)
        val endIndex = functionStart.indexOfFirst { it == ')' }
        return functionStart.substring(1 until endIndex).split(" ").map { it.toFloat() }
    }
}
