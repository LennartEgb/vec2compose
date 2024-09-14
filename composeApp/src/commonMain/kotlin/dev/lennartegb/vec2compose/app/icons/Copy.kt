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
val internalCopy: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Copy",
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
        moveTo(9.0f, 18.0f)
        quadToRelative(-0.825f, 0.0f, -1.412f, -0.587f)
        reflectiveQuadTo(7.0f, 16.0f)
        verticalLineTo(4.0f)
        quadToRelative(0.0f, -0.825f, 0.588f, -1.412f)
        reflectiveQuadTo(9.0f, 2.0f)
        horizontalLineToRelative(9.0f)
        quadToRelative(0.825f, 0.0f, 1.413f, 0.588f)
        reflectiveQuadTo(20.0f, 4.0f)
        verticalLineToRelative(12.0f)
        quadToRelative(0.0f, 0.825f, -0.587f, 1.413f)
        reflectiveQuadTo(18.0f, 18.0f)
        close()
        moveToRelative(0.0f, -2.0f)
        horizontalLineToRelative(9.0f)
        verticalLineTo(4.0f)
        horizontalLineTo(9.0f)
        close()
        moveToRelative(-4.0f, 6.0f)
        quadToRelative(-0.825f, 0.0f, -1.412f, -0.587f)
        reflectiveQuadTo(3.0f, 20.0f)
        verticalLineTo(7.0f)
        quadToRelative(0.0f, -0.425f, 0.288f, -0.712f)
        reflectiveQuadTo(4.0f, 6.0f)
        reflectiveQuadToRelative(0.713f, 0.288f)
        reflectiveQuadTo(5.0f, 7.0f)
        verticalLineToRelative(13.0f)
        horizontalLineToRelative(10.0f)
        quadToRelative(0.425f, 0.0f, 0.713f, 0.288f)
        reflectiveQuadTo(16.0f, 21.0f)
        reflectiveQuadToRelative(-0.288f, 0.713f)
        reflectiveQuadTo(15.0f, 22.0f)
        close()
        moveToRelative(4.0f, -6.0f)
        verticalLineTo(4.0f)
        close()
    }.build().also { cache = it }
