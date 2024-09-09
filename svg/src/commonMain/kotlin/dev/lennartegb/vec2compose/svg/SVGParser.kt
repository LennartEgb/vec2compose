package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.VectorSet.Path.FillType
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke.Cap
import dev.lennartegb.vec2compose.core.VectorSet.Path.Stroke.Join
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.Command.ArcTo
import dev.lennartegb.vec2compose.core.commands.Command.Close
import dev.lennartegb.vec2compose.core.commands.Command.LineTo
import dev.lennartegb.vec2compose.core.commands.Command.MoveTo
import dev.lennartegb.vec2compose.core.commands.PathParser

internal class SVGParser(
    private val colorParser: SVGColorParser = SVGColorParser(),
    private val pathParser: PathParser = PathParser(),
    private val deserializer: SVGDeserializer = SVGDeserializer(),
) : VectorSetParser {

    override fun parse(content: String): Result<VectorSet> =
        runCatching { deserializer.deserialize(content).toVectorSet() }

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
            nodes = children.map { it.toNode() },
        )
    }

    private fun SVG.Child.toNode(): VectorSet.Node = when (this) {
        is SVG.Circle -> toVectorPath()
        is SVG.Group -> toVectorGroup()
        is SVG.Path -> toVectorPath()
        is SVG.Rectangle -> toVectorPath()
        is SVG.Ellipse -> toVectorPath()
    }

    private fun SVG.Group.toVectorGroup(): VectorSet.Group {
        return VectorSet.Group(
            name = name,
            nodes = children.map { it.toNode() },
            rotate = transform?.getRotation() ?: 0f,
            pivot = transform?.getPivot() ?: Translation(0f, 0f),
            translation = transform?.getTranslation() ?: Translation(0f, 0f),
            scale = transform?.getScale() ?: Scale(1f, 1f)
        )
    }

    private fun SVG.Path.toVectorPath(): VectorSet.Path {
        val stroke = toStroke()
        var fillColor: VectorSet.Path.FillColor? = fill?.let(colorParser::parse)
        // NOTE: Only when fill and strokeColor is null use black FillColor as default color as
        //       fill can be none resulting to null.
        fillColor = if (fill == null && strokeColor == null) Black else fillColor
        return VectorSet.Path(
            fillType = parseFillType(fillRule),
            commands = pathParser.parse(pathData),
            fillColor = fillColor,
            alpha = fillOpacity,
            stroke = stroke,
        )
    }

    private fun SVG.Ellipse.toVectorPath(): VectorSet.Path {
        val cx = centerX.toFloat()
        val cy = centerY.toFloat()
        val radiusX = radiusX?.toFloat() ?: radiusY?.toFloat()
        requireNotNull(radiusX) { "either rx or ry must be set for ellipse $this" }
        val radiusY = radiusY?.toFloat() ?: radiusX


        return VectorSet.Path(
            fillType = FillType.Default,
            fillColor = fill?.let { colorParser.parse(it) },
            stroke = Stroke(
                color = stroke?.let { colorParser.parse(it) },
                alpha = if (stroke != null) 1f else 0f,
                width = strokeWidth?.toFloat() ?: if (stroke != null) 1f else 0f,
                cap = strokeLineCap?.let(::getStrokeLineCap) ?: Cap.Butt,
                join = strokeLineJoin?.let(::getStrokeLineJoin) ?: Join.Bevel,
                miter = strokeMiterLimit?.toFloat() ?: 1f,
            ),
            alpha = 1f,
            commands = listOf(
                MoveTo(cx - radiusX, cy, isAbsolute = true),
                ArcTo(
                    horizontalEllipseRadius = radiusX,
                    verticalEllipseRadius = radiusY,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isAbsolute = true,
                    isPositiveArc = true,
                    x1 = cx + radiusX,
                    y1 = cy,
                ),
                ArcTo(
                    horizontalEllipseRadius = radiusX,
                    verticalEllipseRadius = radiusY,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isAbsolute = true,
                    isPositiveArc = true,
                    x1 = cx - radiusX,
                    y1 = cy,
                ),
                Close,
            )
        )
    }

    private fun SVG.Rectangle.toVectorPath(): VectorSet.Path {
        val x = x.toFloat()
        val y = y.toFloat()
        val width = width?.toFloat() ?: 0f
        val height = height?.toFloat() ?: 0f
        return VectorSet.Path(
            fillType = FillType.Default,
            fillColor = fill?.let(colorParser::parse),
            alpha = 1f,
            stroke = Stroke(width = 0f),
            commands = listOf(
                MoveTo(x = x, y = y, isAbsolute = true),
                LineTo(x = x + width, y = y, isAbsolute = true),
                LineTo(x = x + width, y = y + height, isAbsolute = true),
                LineTo(x = x, y = y + height, isAbsolute = true),
                Close,
            ),
        )
    }

    private fun SVG.Path.toStroke(): Stroke {
        return Stroke(
            color = strokeColor?.let(colorParser::parse),
            alpha = strokeAlpha?.toFloat(),
            width = strokeWidth?.toFloat(),
            cap = strokeLinecap?.let(::getStrokeLineCap),
            join = strokeLinejoin?.let(::getStrokeLineJoin),
            miter = strokeMiter?.toFloat()
        )
    }

    private fun SVG.Circle.toVectorPath(): VectorSet.Path {
        val cx = centerX.toFloat()
        val cy = centerY.toFloat()
        val r = radius.toFloat()
        val color = fill?.let(colorParser::parse)
            ?: VectorSet.Path.FillColor(0x00, 0x00, 0x00, alpha = 0xff)

        return VectorSet.Path(
            fillType = FillType.Default,
            fillColor = color,
            commands = listOf(
                MoveTo(x = cx - r, y = cy, isAbsolute = true),
                ArcTo(
                    horizontalEllipseRadius = r,
                    verticalEllipseRadius = r,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isAbsolute = true,
                    isPositiveArc = true,
                    x1 = cx + r,
                    y1 = cy
                ),
                ArcTo(
                    horizontalEllipseRadius = r,
                    verticalEllipseRadius = r,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isAbsolute = true,
                    isPositiveArc = true,
                    x1 = cx - r,
                    y1 = cy,
                ),
                Close
            ),
            alpha = opacity.toFloat(),
            stroke = Stroke(),
        )
    }

    private fun getStrokeLineJoin(value: String): Join {
        // NOTE: Documentation lists more supported values. Compose only supports three, so we
        //       need to ignore unsupported values for now.
        //       arcs | bevel | miter | miter-clip | round
        //       @see https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/stroke-linejoin
        return when (value) {
            "round" -> Join.Round
            "bevel" -> Join.Bevel
            "miter" -> Join.Miter
            else -> error("StrokeJoin not supported. Was: $value. Must be in: ${Join.entries}")
        }
    }

    private fun getStrokeLineCap(value: String): Cap {
        return when (value) {
            "butt" -> Cap.Butt
            "round" -> Cap.Round
            "square" -> Cap.Square
            else -> error("StrokeCap not supported. Was: $value. Must be in: ${Cap.entries}")
        }
    }

    private fun parseFillType(fillRule: String): FillType {
        return when (fillRule) {
            "evenodd" -> FillType.EvenOdd
            "nonzero" -> FillType.NonZero
            else -> FillType.Default
        }
    }

    private fun String.getRotation(): Float {
        return getFunction("rotate")
            ?.let { (a, _, _) -> a }
            ?: 0f
    }

    private fun String.getPivot(): Translation {
        return getFunction("rotate")
            ?.let { (_, x, y) -> Translation(x = x, y = y) }
            ?: Translation(0f, 0f)
    }

    private fun String.getTranslation(): Translation {
        return getFunction("translate")
            ?.let { (x, y) -> Translation(x = x, y = y) }
            ?: Translation(0f, 0f)
    }

    private fun String.getScale(): Scale {
        return getFunction("scale")
            ?.let { (x, y) -> Scale(x = x, y = y) }
            ?: Scale(1f, 1f)
    }

    private fun String.getFunction(key: String): List<Float>? {
        val startIndex = indexOf(key).takeIf { it != -1 } ?: return null
        val functionStart = substring(startIndex + key.length)
        val endIndex = functionStart.indexOfFirst { it == ')' }
        return functionStart.substring(1 until endIndex).split(" ").map { it.toFloat() }
    }

    private val Black: VectorSet.Path.FillColor =
        VectorSet.Path.FillColor(0x00, 0x00, 0x00, alpha = 0xff)
}
