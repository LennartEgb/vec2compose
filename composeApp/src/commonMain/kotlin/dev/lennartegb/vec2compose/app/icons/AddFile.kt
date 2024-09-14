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
val internalUpload: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Upload",
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
        moveTo(11.0f, 14.825f)
        verticalLineTo(18.0f)
        quadToRelative(0.0f, 0.425f, 0.288f, 0.713f)
        reflectiveQuadTo(12.0f, 19.0f)
        reflectiveQuadToRelative(0.713f, -0.288f)
        reflectiveQuadTo(13.0f, 18.0f)
        verticalLineToRelative(-3.175f)
        lineToRelative(0.9f, 0.9f)
        quadToRelative(0.15f, 0.15f, 0.338f, 0.225f)
        reflectiveQuadToRelative(0.375f, 0.063f)
        reflectiveQuadToRelative(0.362f, -0.088f)
        reflectiveQuadToRelative(0.325f, -0.225f)
        quadToRelative(0.275f, -0.3f, 0.288f, -0.7f)
        reflectiveQuadToRelative(-0.288f, -0.7f)
        lineToRelative(-2.6f, -2.6f)
        quadToRelative(-0.15f, -0.15f, -0.325f, -0.212f)
        reflectiveQuadTo(12.0f, 11.425f)
        reflectiveQuadToRelative(-0.375f, 0.063f)
        reflectiveQuadToRelative(-0.325f, 0.212f)
        lineToRelative(-2.6f, 2.6f)
        quadToRelative(-0.3f, 0.3f, -0.287f, 0.7f)
        reflectiveQuadToRelative(0.312f, 0.7f)
        quadToRelative(0.3f, 0.275f, 0.7f, 0.288f)
        reflectiveQuadToRelative(0.7f, -0.288f)
        close()
        moveTo(6.0f, 22.0f)
        quadToRelative(-0.825f, 0.0f, -1.412f, -0.587f)
        reflectiveQuadTo(4.0f, 20.0f)
        verticalLineTo(4.0f)
        quadToRelative(0.0f, -0.825f, 0.588f, -1.412f)
        reflectiveQuadTo(6.0f, 2.0f)
        horizontalLineToRelative(7.175f)
        quadToRelative(0.4f, 0.0f, 0.763f, 0.15f)
        reflectiveQuadToRelative(0.637f, 0.425f)
        lineToRelative(4.85f, 4.85f)
        quadToRelative(0.275f, 0.275f, 0.425f, 0.638f)
        reflectiveQuadToRelative(0.15f, 0.762f)
        verticalLineTo(20.0f)
        quadToRelative(0.0f, 0.825f, -0.587f, 1.413f)
        reflectiveQuadTo(18.0f, 22.0f)
        close()
        moveToRelative(7.0f, -14.0f)
        verticalLineTo(4.0f)
        horizontalLineTo(6.0f)
        verticalLineToRelative(16.0f)
        horizontalLineToRelative(12.0f)
        verticalLineTo(9.0f)
        horizontalLineToRelative(-4.0f)
        quadToRelative(-0.425f, 0.0f, -0.712f, -0.288f)
        reflectiveQuadTo(13.0f, 8.0f)
        moveTo(6.0f, 4.0f)
        verticalLineToRelative(5.0f)
        close()
        verticalLineToRelative(16.0f)
        close()
    }.build().also { cache = it }
