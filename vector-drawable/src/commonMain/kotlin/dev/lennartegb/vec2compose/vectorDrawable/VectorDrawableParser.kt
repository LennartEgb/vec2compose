package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVectorParser
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.parsePath

private typealias DpString = String

fun xmlImageVectorParser(): ImageVectorParser = VectorDrawableParser(
    colorParser = HexColorParser(),
    deserializer = VectorDrawableDeserializer()
)

internal class VectorDrawableParser(
    private val colorParser: HexColorParser,
    private val deserializer: VectorDrawableDeserializer = VectorDrawableDeserializer()
) : ImageVectorParser {

    override fun parse(content: String): Result<ImageVector> {
        val androidVector = deserializer.deserialize(content)
        return runCatching { androidVector.toImageVector() }
    }

    private fun VectorDrawable.toImageVector(): ImageVector {
        return ImageVector(
            width = widthInDp.toIntDp(),
            height = heightInDp.toIntDp(),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            nodes = children.map {
                when (it) {
                    is VectorDrawable.Group -> it.toVectorGroup()
                    is VectorDrawable.Path -> it.toVectorPath()
                }
            }
        )
    }

    private fun VectorDrawable.Group.toVectorGroup(): ImageVector.Group {
        val tx = translateX ?: 0f
        val ty = translateY ?: 0f
        val sx = scaleX ?: 1f
        val sy = scaleY ?: 1f
        val px = pivotX ?: 0f
        val py = pivotY ?: 0f
        return ImageVector.Group(
            name = name,
            nodes = children.map {
                when (it) {
                    is VectorDrawable.Group -> it.toVectorGroup()
                    is VectorDrawable.Path -> it.toVectorPath()
                }
            },
            rotate = rotation ?: 0f,
            pivot = Translation(x = px, y = py),
            translation = Translation(x = tx, y = ty),
            scale = Scale(x = sx, y = sy)
        )
    }

    private fun VectorDrawable.Path.toVectorPath(): ImageVector.Path {
        return ImageVector.Path(
            fillType = ImageVector.Path.FillType.parse(fillType),
            commands = parsePath(pathData),
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

    private fun getCap(value: String): ImageVector.Path.Stroke.Cap {
        return when (value) {
            "round" -> ImageVector.Path.Stroke.Cap.Round
            "butt" -> ImageVector.Path.Stroke.Cap.Butt
            "square" -> ImageVector.Path.Stroke.Cap.Square
            else -> error("StrokeCap not supported. Was: $value. Must be in: ${ImageVector.Path.Stroke.Cap.entries}")
        }
    }

    private fun getJoin(value: String): ImageVector.Path.Stroke.Join {
        return when (value) {
            "round" -> ImageVector.Path.Stroke.Join.Round
            "bevel" -> ImageVector.Path.Stroke.Join.Bevel
            "miter" -> ImageVector.Path.Stroke.Join.Miter
            else -> error("StrokeJoin not supported. Was: $value. Must be in: ${ImageVector.Path.Stroke.Join.entries}")
        }
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

    private fun ImageVector.Path.FillType.Companion.parse(fillType: String): ImageVector.Path.FillType {
        return when (fillType) {
            "evenOdd" -> ImageVector.Path.FillType.EvenOdd
            "nonZero" -> ImageVector.Path.FillType.NonZero
            else -> Default
        }
    }
}
