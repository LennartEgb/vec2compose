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
internal val internalMoon: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Moon",
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
        moveTo(12.0f, 21.0f)
        quadToRelative(-3.775f, 0.0f, -6.387f, -2.613f)
        reflectiveQuadTo(3.0f, 12.0f)
        quadToRelative(0.0f, -3.45f, 2.25f, -5.988f)
        reflectiveQuadTo(11.0f, 3.05f)
        quadToRelative(0.325f, -0.05f, 0.575f, 0.088f)
        reflectiveQuadToRelative(0.4f, 0.362f)
        reflectiveQuadToRelative(0.163f, 0.525f)
        reflectiveQuadToRelative(-0.188f, 0.575f)
        quadToRelative(-0.425f, 0.65f, -0.638f, 1.375f)
        reflectiveQuadTo(11.1f, 7.5f)
        quadToRelative(0.0f, 2.25f, 1.575f, 3.825f)
        reflectiveQuadTo(16.5f, 12.9f)
        quadToRelative(0.775f, 0.0f, 1.538f, -0.225f)
        reflectiveQuadToRelative(1.362f, -0.625f)
        quadToRelative(0.275f, -0.175f, 0.563f, -0.162f)
        reflectiveQuadToRelative(0.512f, 0.137f)
        quadToRelative(0.25f, 0.125f, 0.388f, 0.375f)
        reflectiveQuadToRelative(0.087f, 0.6f)
        quadToRelative(-0.35f, 3.45f, -2.937f, 5.725f)
        reflectiveQuadTo(12.0f, 21.0f)
        moveToRelative(0.0f, -2.0f)
        quadToRelative(2.2f, 0.0f, 3.95f, -1.213f)
        reflectiveQuadToRelative(2.55f, -3.162f)
        quadToRelative(-0.5f, 0.125f, -1.0f, 0.2f)
        reflectiveQuadToRelative(-1.0f, 0.075f)
        quadToRelative(-3.075f, 0.0f, -5.238f, -2.163f)
        reflectiveQuadTo(9.1f, 7.5f)
        quadToRelative(0.0f, -0.5f, 0.075f, -1.0f)
        reflectiveQuadToRelative(0.2f, -1.0f)
        quadToRelative(-1.95f, 0.8f, -3.163f, 2.55f)
        reflectiveQuadTo(5.0f, 12.0f)
        quadToRelative(0.0f, 2.9f, 2.05f, 4.95f)
        reflectiveQuadTo(12.0f, 19.0f)
        moveToRelative(-0.25f, -6.75f)
    }.build().also { cache = it }
