package dev.lennartegb.vec2compose.app.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cache: ImageVector? = null
val internalClose: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Close",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).path(
        fill = SolidColor(Color(0xff000000)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1.0f,
        strokeLineWidth = 1.0f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1.0f,
        pathFillType = PathFillType.NonZero
    ) {
        moveToRelative(12.0f, 13.4f)
        lineToRelative(-4.9f, 4.9f)
        quadToRelative(-0.275f, 0.275f, -0.7f, 0.275f)
        reflectiveQuadToRelative(-0.7f, -0.275f)
        reflectiveQuadToRelative(-0.275f, -0.7f)
        reflectiveQuadToRelative(0.275f, -0.7f)
        lineToRelative(4.9f, -4.9f)
        lineToRelative(-4.9f, -4.9f)
        quadToRelative(-0.275f, -0.275f, -0.275f, -0.7f)
        reflectiveQuadToRelative(0.275f, -0.7f)
        reflectiveQuadToRelative(0.7f, -0.275f)
        reflectiveQuadToRelative(0.7f, 0.275f)
        lineToRelative(4.9f, 4.9f)
        lineToRelative(4.9f, -4.9f)
        quadToRelative(0.275f, -0.275f, 0.7f, -0.275f)
        reflectiveQuadToRelative(0.7f, 0.275f)
        reflectiveQuadToRelative(0.275f, 0.7f)
        reflectiveQuadToRelative(-0.275f, 0.7f)
        lineTo(13.4f, 12.0f)
        lineToRelative(4.9f, 4.9f)
        quadToRelative(0.275f, 0.275f, 0.275f, 0.7f)
        reflectiveQuadToRelative(-0.275f, 0.7f)
        reflectiveQuadToRelative(-0.7f, 0.275f)
        reflectiveQuadToRelative(-0.7f, -0.275f)
        close()
    }.build().also { cache = it }
