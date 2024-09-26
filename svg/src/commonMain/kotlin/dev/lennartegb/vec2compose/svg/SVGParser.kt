package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVector.Path.FillType
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Cap
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Join
import dev.lennartegb.vec2compose.core.ImageVectorParser
import dev.lennartegb.vec2compose.core.Path
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo

fun svgImageVectorParser(): ImageVectorParser = SVGParser()

internal class SVGParser(
    private val colorParser: SVGColorParser = SVGColorParser(),
    private val deserializer: SVGDeserializer = SVGDeserializer()
) : ImageVectorParser {

    override fun parse(content: String): Result<ImageVector> =
        runCatching { deserializer.deserialize(content).toImageVector() }

    private fun SVG.toImageVector(): ImageVector {
        val width = width.filter { it.isDigit() }.toInt()
        val height = height.filter { it.isDigit() }.toInt()
        val rect: List<Float> = viewBox?.split(" ")
            ?.map { it.toFloat() }
            ?: listOf(0f, 0f, width.toFloat(), height.toFloat())
        return ImageVector(
            width = width,
            height = height,
            viewportWidth = rect[2] - rect[0],
            viewportHeight = rect[3] - rect[1],
            nodes = children.map { it.toNode() }
        )
    }

    private fun SVG.Child.toNode(): ImageVector.Node = when (this) {
        is SVG.Circle -> toVectorPath()
        is SVG.Group -> toVectorGroup()
        is SVG.Path -> toVectorPath()
        is SVG.Rectangle -> toVectorPath()
        is SVG.Ellipse -> toVectorPath()
    }

    private fun SVG.Group.toVectorGroup(): ImageVector.Group {
        return ImageVector.Group(
            name = name,
            nodes = children.map { it.toNode() },
            rotate = transform?.getRotation() ?: 0f,
            pivot = transform?.getPivot() ?: Translation(0f, 0f),
            translation = transform?.getTranslation() ?: Translation(0f, 0f),
            scale = transform?.getScale() ?: Scale(1f, 1f)
        )
    }

    private fun SVG.Path.toVectorPath(): ImageVector.Path {
        val stroke = toStroke()
        var fillColor: ImageVector.Path.FillColor? = fill?.let(colorParser::parse)
        // NOTE: Only when fill and strokeColor is null use black FillColor as default color as
        //       fill can be none resulting to null.
        fillColor = if (fill == null && strokeColor == null) Black else fillColor
        return ImageVector.Path(
            fillType = parseFillType(fillRule),
            commands = Path(pathData),
            fillColor = fillColor,
            alpha = fillOpacity,
            stroke = stroke
        )
    }

    private fun SVG.Ellipse.toVectorPath(): ImageVector.Path {
        val cx = centerX.toFloat()
        val cy = centerY.toFloat()
        val radiusX = radiusX?.toFloat() ?: radiusY?.toFloat()
        requireNotNull(radiusX) { "either rx or ry must be set for ellipse $this" }
        val radiusY = radiusY?.toFloat() ?: radiusX

        return ImageVector.Path(
            fillType = FillType.Default,
            fillColor = fill?.let { colorParser.parse(it) },
            stroke = Stroke(
                color = stroke?.let { colorParser.parse(it) },
                alpha = if (stroke != null) 1f else 0f,
                width = strokeWidth?.toFloat() ?: if (stroke != null) 1f else 0f,
                cap = strokeLineCap?.let { Cap(it) } ?: Cap.Butt,
                join = strokeLineJoin?.let { Join(it) } ?: Join.Bevel,
                miter = strokeMiterLimit?.toFloat() ?: 1f
            ),
            alpha = 1f,
            commands = listOf(
                MoveTo(x = cx - radiusX, y = cy),
                ArcTo(
                    horizontalEllipseRadius = radiusX,
                    verticalEllipseRadius = radiusY,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = cx + radiusX,
                    y1 = cy
                ),
                ArcTo(
                    horizontalEllipseRadius = radiusX,
                    verticalEllipseRadius = radiusY,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = cx - radiusX,
                    y1 = cy
                ),
                Close()
            )
        )
    }

    private fun SVG.Rectangle.toVectorPath(): ImageVector.Path {
        val x = x.toFloat()
        val y = y.toFloat()
        val width = width?.toFloat() ?: 0f
        val height = height?.toFloat() ?: 0f
        return ImageVector.Path(
            fillType = FillType.Default,
            fillColor = fill?.let(colorParser::parse),
            alpha = 1f,
            stroke = Stroke(width = 0f),
            commands = listOf(
                MoveTo(x = x, y = y),
                LineTo(x = x + width, y = y),
                LineTo(x = x + width, y = y + height),
                LineTo(x = x, y = y + height),
                Close()
            )
        )
    }

    private fun SVG.Path.toStroke(): Stroke {
        return Stroke(
            color = strokeColor?.let(colorParser::parse),
            alpha = strokeAlpha?.toFloat(),
            width = strokeWidth?.toFloat(),
            cap = strokeLinecap?.let { Cap(it) },
            join = strokeLinejoin?.let { Join(it) },
            miter = strokeMiter?.toFloat()
        )
    }

    private fun SVG.Circle.toVectorPath(): ImageVector.Path {
        val cx = centerX.toFloat()
        val cy = centerY.toFloat()
        val r = radius.toFloat()
        val color = fill?.let(colorParser::parse)
            ?: ImageVector.Path.FillColor(0x00, 0x00, 0x00, alpha = 0xff)

        return ImageVector.Path(
            fillType = FillType.Default,
            fillColor = color,
            commands = listOf(
                MoveTo(x = cx - r, y = cy),
                ArcTo(
                    horizontalEllipseRadius = r,
                    verticalEllipseRadius = r,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = cx + r,
                    y1 = cy
                ),
                ArcTo(
                    horizontalEllipseRadius = r,
                    verticalEllipseRadius = r,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = cx - r,
                    y1 = cy
                ),
                Close()
            ),
            alpha = opacity.toFloat(),
            stroke = Stroke()
        )
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

    @Suppress("PrivatePropertyName", "ktlint")
    private val Black: ImageVector.Path.FillColor =
        ImageVector.Path.FillColor(0x00, 0x00, 0x00, alpha = 0xff)
}
