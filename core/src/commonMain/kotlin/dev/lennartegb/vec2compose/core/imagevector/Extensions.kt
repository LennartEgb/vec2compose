package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Cap
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Join

internal fun Join.property(): String = when (this) {
    Join.Bevel -> "StrokeJoin.Bevel"
    Join.Miter -> "StrokeJoin.Miter"
    Join.Round -> "StrokeJoin.Round"
}

internal fun Cap.property(): String = when (this) {
    Cap.Butt -> "StrokeCap.Butt"
    Cap.Square -> "StrokeCap.Square"
    Cap.Round -> "StrokeCap.Round"
}

internal fun ImageVector.Path.FillColor.solid(): String = "SolidColor($this)"
