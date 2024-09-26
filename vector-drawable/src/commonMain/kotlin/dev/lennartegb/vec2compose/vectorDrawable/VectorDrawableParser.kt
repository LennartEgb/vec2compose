package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVector.Path.FillType
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Cap
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Join
import dev.lennartegb.vec2compose.core.ImageVectorParser
import dev.lennartegb.vec2compose.core.Path
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation

private typealias DpString = String

fun xmlImageVectorParser(): ImageVectorParser = VectorDrawableParser(
    colorParser = VectorDrawableColorParser(HexColorParser()),
    deserializer = VectorDrawableDeserializer()
)

internal class VectorDrawableParser(
    private val colorParser: VectorDrawableColorParser,
    private val deserializer: VectorDrawableDeserializer = VectorDrawableDeserializer()
) : ImageVectorParser {

    override fun parse(content: String): Result<ImageVector> =
        runCatching { deserializer.deserialize(content) }
            .mapCatching { it.toImageVector() }

    private fun VectorDrawable.Child.toNode(): ImageVector.Node {
        return when (this) {
            is VectorDrawable.Group -> toVectorGroup()
            is VectorDrawable.Path -> toVectorPath()
        }
    }

    private fun VectorDrawable.toImageVector(): ImageVector {
        return ImageVector(
            width = widthInDp.toIntDp(),
            height = heightInDp.toIntDp(),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            nodes = children.map { it.toNode() }
        )
    }

    private fun VectorDrawable.Group.toVectorGroup(): ImageVector.Group {
        val translation = Translation(x = translateX ?: 0f, y = translateY ?: 0f)
        val scale = Scale(x = scaleX ?: 1f, y = scaleY ?: 1f)
        val pivot = Translation(x = pivotX ?: 0f, y = pivotY ?: 0f)
        return ImageVector.Group(
            name = name,
            nodes = children.map { it.toNode() },
            rotate = rotation ?: 0f,
            pivot = pivot,
            translation = translation,
            scale = scale
        )
    }

    private fun VectorDrawable.Path.toVectorPath(): ImageVector.Path {
        return ImageVector.Path(
            fillType = FillType(fillType),
            commands = Path(pathData),
            fillColor = fillColor?.let(colorParser::parse),
            alpha = alpha,
            stroke = toStroke()
        )
    }

    private fun VectorDrawable.Path.toStroke(): ImageVector.Path.Stroke {
        return ImageVector.Path.Stroke(
            color = strokeColor?.let { colorParser.parse(it) },
            alpha = strokeAlpha?.toFloat(),
            width = strokeWidth?.toFloat(),
            cap = strokeLineCap?.let(::getCap),
            join = strokeLineJoin?.let(::getJoin),
            miter = strokeMiterLimit?.toFloat()
        )
    }

    private fun getCap(value: String): Cap {
        return when (value) {
            "round" -> Cap.Round
            "butt" -> Cap.Butt
            "square" -> Cap.Square
            else -> error("StrokeCap not supported. Was: $value. Must be in: ${Cap.entries}")
        }
    }

    private fun getJoin(value: String): Join {
        return when (value) {
            "round" -> Join.Round
            "bevel" -> Join.Bevel
            "miter" -> Join.Miter
            else -> error("StrokeJoin not supported. Was: $value. Must be in: ${Join.entries}")
        }
    }

    private fun DpString.toIntDp(): Int = toIntOrNull() ?: if (endsWith("dp")) {
        requireNotNull(dropLast(2).toIntOrNull()) { "Malformed dp string: $this" }
    } else {
        error("Could not transform dp string to int: $this")
    }
}
