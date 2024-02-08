package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.ColorParser
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.PathParser

private typealias DpString = String

internal class VectorDrawableParser(
    private val pathParser: PathParser,
    private val colorParser: ColorParser,
    private val deserializer: VectorDrawableDeserializer = VectorDrawableDeserializer()
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
        val tx = translateX ?: 0f
        val ty = translateY ?: 0f
        val sx = scaleX ?: 1f
        val sy = scaleY ?: 1f
        val px = pivotX ?: 0f
        val py = pivotY ?: 0f
        return VectorSet.Group(
            name = name,
            groups = group.map { it.toVectorGroup() },
            paths = path.map { it.toVectorPath() },
            rotate = rotation ?: 0f,
            pivot = Translation(x = px, y = py),
            translation = Translation(x = tx, y = ty),
            scale = Scale(x = sx, y = sy)
        )
    }

    private fun VectorDrawable.Path.toVectorPath(): VectorSet.Path {
        return VectorSet.Path(
            fillType = VectorSet.Path.FillType.parse(fillType),
            commands = pathParser.parse(pathData),
            fillColor = fillColor?.let(colorParser::parse),
            alpha = alpha,
            stroke = toStroke()
        )
    }

    private fun VectorDrawable.Path.toStroke(): VectorSet.Path.Stroke {
        return VectorSet.Path.Stroke(
            color = strokeColor?.let { colorParser.parse(it) },
            alpha = strokeAlpha?.toFloat(),
            width = strokeWidth?.toFloat(),
            cap = strokeLineCap?.let(::getCap),
            join = strokeLineJoin?.let(::getJoin),
            miter = strokeMiterLimit?.toFloat(),
        )
    }

    private fun getCap(value: String): VectorSet.Path.Stroke.Cap {
        return when (value) {
            "round" -> VectorSet.Path.Stroke.Cap.Round
            "butt" -> VectorSet.Path.Stroke.Cap.Butt
            "square" -> VectorSet.Path.Stroke.Cap.Square
            else -> error("StrokeCap not supported. Was: $value. Must be in: ${VectorSet.Path.Stroke.Cap.entries}")
        }
    }

    private fun getJoin(value: String): VectorSet.Path.Stroke.Join {
        return when (value) {
            "round" -> VectorSet.Path.Stroke.Join.Round
            "bevel" -> VectorSet.Path.Stroke.Join.Bevel
            "miter" -> VectorSet.Path.Stroke.Join.Miter
            else -> error("StrokeJoin not supported. Was: $value. Must be in: ${VectorSet.Path.Stroke.Join.entries}")
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

    private fun VectorSet.Path.FillType.Companion.parse(fillType: String): VectorSet.Path.FillType {
        return when (fillType) {
            "evenOdd" -> VectorSet.Path.FillType.EvenOdd
            "nonZero" -> VectorSet.Path.FillType.NonZero
            else -> Default
        }
    }
}
