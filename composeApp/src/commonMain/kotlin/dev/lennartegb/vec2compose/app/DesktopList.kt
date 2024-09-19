package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object DesktopListDefaults {
    val width = 360.dp
    val elevation = 3.dp
    val contentPadding = PaddingValues(16.dp)
}

@Composable
fun DesktopList(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    width: Dp = DesktopListDefaults.width,
    elevation: Dp = DesktopListDefaults.elevation,
    bottomButton: @Composable () -> Unit = {},
    contentPadding: PaddingValues = DesktopListDefaults.contentPadding,
    content: @Composable (PaddingValues) -> Unit
) {
    MaterialTheme
    Surface(
        modifier = modifier.width(IntrinsicSize.Min),
        color = color,
        contentColor = contentColor,
        shadowElevation = elevation
    ) {
        Box(modifier = Modifier.fillMaxHeight().requiredWidth(width)) {
            content(contentPadding)
            Box(Modifier.align(Alignment.BottomCenter).padding(contentPadding)) {
                bottomButton()
            }
        }
    }
}
