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
internal val internalSun: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Sun",
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
        moveTo(12.0f, 5.0f)
        quadToRelative(-0.425f, 0.0f, -0.712f, -0.288f)
        reflectiveQuadTo(11.0f, 4.0f)
        verticalLineTo(2.0f)
        quadToRelative(0.0f, -0.425f, 0.288f, -0.712f)
        reflectiveQuadTo(12.0f, 1.0f)
        reflectiveQuadToRelative(0.713f, 0.288f)
        reflectiveQuadTo(13.0f, 2.0f)
        verticalLineToRelative(2.0f)
        quadToRelative(0.0f, 0.425f, -0.288f, 0.713f)
        reflectiveQuadTo(12.0f, 5.0f)
        moveToRelative(4.95f, 2.05f)
        quadToRelative(-0.275f, -0.275f, -0.275f, -0.687f)
        reflectiveQuadToRelative(0.275f, -0.713f)
        lineToRelative(1.4f, -1.425f)
        quadToRelative(0.3f, -0.3f, 0.712f, -0.3f)
        reflectiveQuadToRelative(0.713f, 0.3f)
        quadToRelative(0.275f, 0.275f, 0.275f, 0.7f)
        reflectiveQuadToRelative(-0.275f, 0.7f)
        lineTo(18.35f, 7.05f)
        quadToRelative(-0.275f, 0.275f, -0.7f, 0.275f)
        reflectiveQuadToRelative(-0.7f, -0.275f)
        moveTo(20.0f, 13.0f)
        quadToRelative(-0.425f, 0.0f, -0.713f, -0.288f)
        reflectiveQuadTo(19.0f, 12.0f)
        reflectiveQuadToRelative(0.288f, -0.712f)
        reflectiveQuadTo(20.0f, 11.0f)
        horizontalLineToRelative(2.0f)
        quadToRelative(0.425f, 0.0f, 0.713f, 0.288f)
        reflectiveQuadTo(23.0f, 12.0f)
        reflectiveQuadToRelative(-0.288f, 0.713f)
        reflectiveQuadTo(22.0f, 13.0f)
        close()
        moveToRelative(-8.0f, 10.0f)
        quadToRelative(-0.425f, 0.0f, -0.712f, -0.288f)
        reflectiveQuadTo(11.0f, 22.0f)
        verticalLineToRelative(-2.0f)
        quadToRelative(0.0f, -0.425f, 0.288f, -0.712f)
        reflectiveQuadTo(12.0f, 19.0f)
        reflectiveQuadToRelative(0.713f, 0.288f)
        reflectiveQuadTo(13.0f, 20.0f)
        verticalLineToRelative(2.0f)
        quadToRelative(0.0f, 0.425f, -0.288f, 0.713f)
        reflectiveQuadTo(12.0f, 23.0f)
        moveTo(5.65f, 7.05f)
        lineToRelative(-1.425f, -1.4f)
        quadToRelative(-0.3f, -0.3f, -0.3f, -0.725f)
        reflectiveQuadToRelative(0.3f, -0.7f)
        quadToRelative(0.275f, -0.275f, 0.7f, -0.275f)
        reflectiveQuadToRelative(0.7f, 0.275f)
        lineTo(7.05f, 5.65f)
        quadToRelative(0.275f, 0.275f, 0.275f, 0.7f)
        reflectiveQuadToRelative(-0.275f, 0.7f)
        quadToRelative(-0.3f, 0.275f, -0.7f, 0.275f)
        reflectiveQuadToRelative(-0.7f, -0.275f)
        moveToRelative(12.7f, 12.725f)
        lineToRelative(-1.4f, -1.425f)
        quadToRelative(-0.275f, -0.3f, -0.275f, -0.712f)
        reflectiveQuadToRelative(0.275f, -0.688f)
        reflectiveQuadToRelative(0.688f, -0.275f)
        reflectiveQuadToRelative(0.712f, 0.275f)
        lineToRelative(1.425f, 1.4f)
        quadToRelative(0.3f, 0.275f, 0.288f, 0.7f)
        reflectiveQuadToRelative(-0.288f, 0.725f)
        quadToRelative(-0.3f, 0.3f, -0.725f, 0.3f)
        reflectiveQuadToRelative(-0.7f, -0.3f)
        moveTo(2.0f, 13.0f)
        quadToRelative(-0.425f, 0.0f, -0.712f, -0.288f)
        reflectiveQuadTo(1.0f, 12.0f)
        reflectiveQuadToRelative(0.288f, -0.712f)
        reflectiveQuadTo(2.0f, 11.0f)
        horizontalLineToRelative(2.0f)
        quadToRelative(0.425f, 0.0f, 0.713f, 0.288f)
        reflectiveQuadTo(5.0f, 12.0f)
        reflectiveQuadToRelative(-0.288f, 0.713f)
        reflectiveQuadTo(4.0f, 13.0f)
        close()
        moveToRelative(2.225f, 6.775f)
        quadToRelative(-0.275f, -0.275f, -0.275f, -0.7f)
        reflectiveQuadToRelative(0.275f, -0.7f)
        lineTo(5.65f, 16.95f)
        quadToRelative(0.275f, -0.275f, 0.687f, -0.275f)
        reflectiveQuadToRelative(0.713f, 0.275f)
        quadToRelative(0.3f, 0.3f, 0.3f, 0.713f)
        reflectiveQuadToRelative(-0.3f, 0.712f)
        lineToRelative(-1.4f, 1.4f)
        quadToRelative(-0.3f, 0.3f, -0.725f, 0.3f)
        reflectiveQuadToRelative(-0.7f, -0.3f)
        moveTo(12.0f, 18.0f)
        quadToRelative(-2.5f, 0.0f, -4.25f, -1.75f)
        reflectiveQuadTo(6.0f, 12.0f)
        reflectiveQuadToRelative(1.75f, -4.25f)
        reflectiveQuadTo(12.0f, 6.0f)
        reflectiveQuadToRelative(4.25f, 1.75f)
        reflectiveQuadTo(18.0f, 12.0f)
        reflectiveQuadToRelative(-1.75f, 4.25f)
        reflectiveQuadTo(12.0f, 18.0f)
    }.build().also { cache = it }
