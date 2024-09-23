package dev.lennartegb.vec2compose.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.raw
import androidx.compose.ui.graphics.vector.ImageVector as ComposeImageVector

fun ImageVector.toCompose(name: String): ComposeImageVector {
    ComposeImageVector.Builder(
        name = name,
        defaultWidth = width.dp,
        defaultHeight = height.dp,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight,
        tintColor = Color.Black,
        autoMirror = false
    )

    val builder = ComposeImageVector.Builder(
        name = name,
        defaultWidth = width.dp,
        defaultHeight = height.dp,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight,
        tintColor = Color.Black,
        autoMirror = false
    )

    builder.addNodes(nodes)

    return builder.build()
}

private fun ImageVector.Path.FillColor.toColor(): Color {
    return Color(red = red, green = green, blue = blue, alpha = alpha)
}

private fun ComposeImageVector.Builder.addNodes(nodes: List<ImageVector.Node>) {
    nodes.forEach { node ->
        when (node) {
            is ImageVector.Group -> group(
                name = node.name.orEmpty(),
                rotate = node.rotate,
                pivotX = node.pivot.x,
                pivotY = node.pivot.y,
                scaleX = node.scale.x,
                scaleY = node.scale.y,
                translationX = node.translation.x,
                translationY = node.translation.y
            ) {
                addNodes(node.nodes)
            }

            is ImageVector.Path -> path(
                fill = node.fillColor?.let { SolidColor(it.toColor()) },
                fillAlpha = node.alpha,
                stroke = node.stroke.color?.let { SolidColor(it.toColor()) },
                strokeAlpha = node.stroke.alpha,
                strokeLineWidth = node.stroke.width,
                strokeLineCap = when (node.stroke.cap) {
                    ImageVector.Path.Stroke.Cap.Butt -> StrokeCap.Butt
                    ImageVector.Path.Stroke.Cap.Round -> StrokeCap.Round
                    ImageVector.Path.Stroke.Cap.Square -> StrokeCap.Square
                },
                strokeLineJoin = when (node.stroke.join) {
                    ImageVector.Path.Stroke.Join.Bevel -> StrokeJoin.Bevel
                    ImageVector.Path.Stroke.Join.Miter -> StrokeJoin.Miter
                    ImageVector.Path.Stroke.Join.Round -> StrokeJoin.Round
                },
                strokeLineMiter = node.stroke.miter,
                pathFillType = when (node.fillType) {
                    ImageVector.Path.FillType.NonZero -> PathFillType.NonZero
                    ImageVector.Path.FillType.EvenOdd -> PathFillType.EvenOdd
                }
            ) {
                addCommands(addPathNodes(node.commands.raw))
            }
        }
    }
}

private fun PathBuilder.addCommands(commands: List<PathNode>) {
    commands.forEach { command ->
        when (command) {
            is PathNode.ArcTo -> arcTo(
                horizontalEllipseRadius = command.horizontalEllipseRadius,
                verticalEllipseRadius = command.verticalEllipseRadius,
                theta = command.theta,
                isMoreThanHalf = command.isMoreThanHalf,
                isPositiveArc = command.isPositiveArc,
                x1 = command.arcStartX,
                y1 = command.arcStartY
            )

            PathNode.Close -> close()
            is PathNode.CurveTo -> curveTo(
                x1 = command.x1,
                y1 = command.y1,
                x2 = command.x2,
                y2 = command.y2,
                x3 = command.x3,
                y3 = command.y3
            )

            is PathNode.HorizontalTo -> horizontalLineTo(x = command.x)
            is PathNode.LineTo -> lineTo(x = command.x, y = command.y)
            is PathNode.MoveTo -> moveTo(x = command.x, y = command.y)
            is PathNode.QuadTo -> quadTo(
                x1 = command.x1,
                y1 = command.y1,
                x2 = command.x2,
                y2 = command.y2
            )

            is PathNode.ReflectiveCurveTo -> reflectiveCurveTo(
                x1 = command.x1,
                y1 = command.y1,
                x2 = command.x2,
                y2 = command.y2
            )

            is PathNode.ReflectiveQuadTo -> reflectiveQuadTo(x1 = command.x, y1 = command.y)
            is PathNode.RelativeArcTo -> arcToRelative(
                a = command.horizontalEllipseRadius,
                b = command.verticalEllipseRadius,
                theta = command.theta,
                isMoreThanHalf = command.isMoreThanHalf,
                isPositiveArc = command.isPositiveArc,
                dx1 = command.arcStartDx,
                dy1 = command.arcStartDy
            )

            is PathNode.RelativeCurveTo -> curveToRelative(
                dx1 = command.dx1,
                dy1 = command.dy1,
                dx2 = command.dx2,
                dy2 = command.dy2,
                dx3 = command.dx3,
                dy3 = command.dy3
            )

            is PathNode.RelativeHorizontalTo -> horizontalLineToRelative(dx = command.dx)
            is PathNode.RelativeLineTo -> lineToRelative(dx = command.dx, dy = command.dy)
            is PathNode.RelativeMoveTo -> moveToRelative(dx = command.dx, dy = command.dy)
            is PathNode.RelativeQuadTo -> quadToRelative(
                dx1 = command.dx1,
                dy1 = command.dy1,
                dx2 = command.dx2,
                dy2 = command.dy2
            )

            is PathNode.RelativeReflectiveCurveTo -> reflectiveCurveToRelative(
                dx1 = command.dx1,
                dy1 = command.dy1,
                dx2 = command.dx2,
                dy2 = command.dy2
            )

            is PathNode.RelativeReflectiveQuadTo -> reflectiveQuadToRelative(
                dx1 = command.dx,
                dy1 = command.dy
            )

            is PathNode.RelativeVerticalTo -> verticalLineToRelative(dy = command.dy)
            is PathNode.VerticalTo -> verticalLineTo(y = command.y)
        }
    }
}
