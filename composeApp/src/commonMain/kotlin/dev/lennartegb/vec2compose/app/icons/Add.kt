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
val internalAdd: ImageVector
    get() = cache ?: ImageVector.Builder(
        name = "Add",
        defaultWidth = 18.dp,
        defaultHeight = 18.dp,
        viewportWidth = 18.0f,
        viewportHeight = 18.0f
    ).path(
        fill = SolidColor(Color(0xffffffff)),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1.0f,
        strokeLineWidth = 1.0f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Bevel,
        strokeLineMiter = 1.0f,
        pathFillType = PathFillType.NonZero
    ) {
        moveTo(8.25f, 9.75f)
        horizontalLineTo(3.75f)
        verticalLineTo(8.25f)
        horizontalLineTo(8.25f)
        verticalLineTo(3.75f)
        horizontalLineTo(9.75f)
        verticalLineTo(8.25f)
        horizontalLineTo(14.25f)
        verticalLineTo(9.75f)
        horizontalLineTo(9.75f)
        verticalLineTo(14.25f)
        horizontalLineTo(8.25f)
        verticalLineTo(9.75f)
        close()
    }.build().also { cache = it }
