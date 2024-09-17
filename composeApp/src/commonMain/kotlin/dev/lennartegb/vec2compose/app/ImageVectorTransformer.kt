package dev.lennartegb.vec2compose.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.Command
import dev.lennartegb.vec2compose.core.commands.CurveTo
import dev.lennartegb.vec2compose.core.commands.HorizontalLineTo
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import dev.lennartegb.vec2compose.core.commands.QuadraticBezierTo
import dev.lennartegb.vec2compose.core.commands.ReflectiveCurveTo
import dev.lennartegb.vec2compose.core.commands.ReflectiveQuadraticBezierTo
import dev.lennartegb.vec2compose.core.commands.VerticalLineTo
import androidx.compose.ui.graphics.vector.ImageVector as ComposeImageVector

fun ImageVector.toCompose(name: String): ComposeImageVector {
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
                if (node.nodes.isNotEmpty()) this.addNodes(node.nodes)
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
                addCommands(node.commands)
            }
        }
    }
}

private fun PathBuilder.addCommands(commands: List<Command>) {
    commands.forEach { command ->
        when (command) {
            Close -> close()
            is ArcTo -> if (command.isAbsolute) {
                arcTo(
                    horizontalEllipseRadius = command.horizontalEllipseRadius,
                    verticalEllipseRadius = command.verticalEllipseRadius,
                    theta = command.theta,
                    isMoreThanHalf = command.isMoreThanHalf,
                    isPositiveArc = command.isPositiveArc,
                    x1 = command.x1,
                    y1 = command.y1
                )
            } else {
                arcToRelative(
                    a = command.verticalEllipseRadius,
                    b = command.horizontalEllipseRadius,
                    dx1 = command.x1,
                    dy1 = command.y1,
                    theta = command.theta,
                    isMoreThanHalf = command.isMoreThanHalf,
                    isPositiveArc = command.isPositiveArc
                )
            }

            is CurveTo -> if (command.isAbsolute) {
                curveTo(
                    x1 = command.x1,
                    y1 = command.y1,
                    x2 = command.x2,
                    y2 = command.y2,
                    x3 = command.x3,
                    y3 = command.y3
                )
            } else {
                curveToRelative(
                    dx1 = command.x1,
                    dy1 = command.y1,
                    dx2 = command.x2,
                    dy2 = command.y2,
                    dx3 = command.x3,
                    dy3 = command.y3
                )
            }

            is HorizontalLineTo -> if (command.isAbsolute) {
                horizontalLineTo(command.x)
            } else {
                horizontalLineToRelative(command.x)
            }

            is LineTo -> if (command.isAbsolute) {
                lineTo(x = command.x, y = command.y)
            } else {
                lineToRelative(dx = command.x, dy = command.y)
            }

            is MoveTo -> if (command.isAbsolute) {
                moveTo(x = command.x, y = command.y)
            } else {
                moveToRelative(dx = command.x, dy = command.y)
            }

            is QuadraticBezierTo -> if (command.isAbsolute) {
                quadTo(
                    x1 = command.x1,
                    y1 = command.y1,
                    x2 = command.x2,
                    y2 = command.y2
                )
            } else {
                quadToRelative(
                    dx1 = command.x1,
                    dy1 = command.y1,
                    dx2 = command.x2,
                    dy2 = command.y2
                )
            }

            is ReflectiveCurveTo -> if (command.isAbsolute) {
                reflectiveCurveTo(
                    x1 = command.x1,
                    y1 = command.y1,
                    x2 = command.x2,
                    y2 = command.y2
                )
            } else {
                reflectiveCurveToRelative(
                    dx1 = command.x1,
                    dy1 = command.y1,
                    dx2 = command.x2,
                    dy2 = command.y2
                )
            }

            is ReflectiveQuadraticBezierTo -> if (command.isAbsolute) {
                reflectiveQuadTo(x1 = command.x, y1 = command.y)
            } else {
                reflectiveQuadToRelative(dx1 = command.x, dy1 = command.y)
            }

            is VerticalLineTo -> if (command.isAbsolute) {
                verticalLineTo(y = command.y)
            } else {
                verticalLineToRelative(dy = command.y)
            }
        }
    }
}
