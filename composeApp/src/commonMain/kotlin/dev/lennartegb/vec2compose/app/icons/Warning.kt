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
val internalWarning: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Warning",
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
        moveTo(2.725f, 21.0f)
        quadToRelative(-0.275f, 0.0f, -0.5f, -0.137f)
        reflectiveQuadToRelative(-0.35f, -0.363f)
        reflectiveQuadToRelative(-0.137f, -0.488f)
        reflectiveQuadToRelative(0.137f, -0.512f)
        lineToRelative(9.25f, -16.0f)
        quadToRelative(0.15f, -0.25f, 0.388f, -0.375f)
        reflectiveQuadTo(12.0f, 3.0f)
        reflectiveQuadToRelative(0.488f, 0.125f)
        reflectiveQuadToRelative(0.387f, 0.375f)
        lineToRelative(9.25f, 16.0f)
        quadToRelative(0.15f, 0.25f, 0.138f, 0.513f)
        reflectiveQuadToRelative(-0.138f, 0.487f)
        reflectiveQuadToRelative(-0.35f, 0.363f)
        reflectiveQuadToRelative(-0.5f, 0.137f)
        close()
        moveToRelative(1.725f, -2.0f)
        horizontalLineToRelative(15.1f)
        lineTo(12.0f, 6.0f)
        close()
        moveTo(12.0f, 18.0f)
        quadToRelative(0.425f, 0.0f, 0.713f, -0.288f)
        reflectiveQuadTo(13.0f, 17.0f)
        reflectiveQuadToRelative(-0.288f, -0.712f)
        reflectiveQuadTo(12.0f, 16.0f)
        reflectiveQuadToRelative(-0.712f, 0.288f)
        reflectiveQuadTo(11.0f, 17.0f)
        reflectiveQuadToRelative(0.288f, 0.713f)
        reflectiveQuadTo(12.0f, 18.0f)
        moveToRelative(0.0f, -3.0f)
        quadToRelative(0.425f, 0.0f, 0.713f, -0.288f)
        reflectiveQuadTo(13.0f, 14.0f)
        verticalLineToRelative(-3.0f)
        quadToRelative(0.0f, -0.425f, -0.288f, -0.712f)
        reflectiveQuadTo(12.0f, 10.0f)
        reflectiveQuadToRelative(-0.712f, 0.288f)
        reflectiveQuadTo(11.0f, 11.0f)
        verticalLineToRelative(3.0f)
        quadToRelative(0.0f, 0.425f, 0.288f, 0.713f)
        reflectiveQuadTo(12.0f, 15.0f)
        moveToRelative(0.0f, -2.5f)
    }.build().also { cache = it }
