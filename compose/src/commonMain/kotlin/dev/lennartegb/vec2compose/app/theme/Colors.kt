package dev.lennartegb.vec2compose.app.theme

import androidx.compose.ui.graphics.Color

internal fun darkColors() = androidx.compose.material.darkColors(
    background = Colors.Black,
    surface = Colors.Gray,
)

internal fun lightColors() = androidx.compose.material.lightColors(
    background = Color(0xFFEEEEEE),
    surface = Color.White,
)

private object Colors {
    val Black = Color(0xFF121212)
    val Gray = Color(0xFF333333)
}
