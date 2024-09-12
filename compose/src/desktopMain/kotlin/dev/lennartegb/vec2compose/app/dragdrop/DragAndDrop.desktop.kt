package dev.lennartegb.vec2compose.app.dragdrop

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.onExternalDrag
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.data.toFiles

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.onExternalDrag(
    onDragStart: () -> Unit,
    onDragExit: () -> Unit,
    onDrop: (List<File>) -> Unit,
    enabled: Boolean,
): Modifier = composed {
    onExternalDrag(
        enabled = enabled,
        onDragStart = { onDragStart() },
        onDrop = { onDrop(it.toFiles()) },
        onDragExit = onDragExit,
    )
}