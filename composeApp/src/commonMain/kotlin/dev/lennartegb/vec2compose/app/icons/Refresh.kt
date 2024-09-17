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
val internalRefresh: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Refresh",
        defaultWidth = 48.dp,
        defaultHeight = 48.dp,
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
        moveTo(12.0f, 20.0f)
        quadToRelative(-3.35f, 0.0f, -5.675f, -2.325f)
        reflectiveQuadTo(4.0f, 12.0f)
        reflectiveQuadToRelative(2.325f, -5.675f)
        reflectiveQuadTo(12.0f, 4.0f)
        quadToRelative(1.725f, 0.0f, 3.3f, 0.712f)
        reflectiveQuadTo(18.0f, 6.75f)
        verticalLineTo(4.0f)
        horizontalLineToRelative(2.0f)
        verticalLineToRelative(7.0f)
        horizontalLineToRelative(-7.0f)
        verticalLineTo(9.0f)
        horizontalLineToRelative(4.2f)
        quadToRelative(-0.8f, -1.4f, -2.187f, -2.2f)
        reflectiveQuadTo(12.0f, 6.0f)
        quadTo(9.5f, 6.0f, 7.75f, 7.75f)
        reflectiveQuadTo(6.0f, 12.0f)
        reflectiveQuadToRelative(1.75f, 4.25f)
        reflectiveQuadTo(12.0f, 18.0f)
        quadToRelative(1.925f, 0.0f, 3.475f, -1.1f)
        reflectiveQuadTo(17.65f, 14.0f)
        horizontalLineToRelative(2.1f)
        quadToRelative(-0.7f, 2.65f, -2.85f, 4.325f)
        reflectiveQuadTo(12.0f, 20.0f)
    }.build().also { cache = it }
