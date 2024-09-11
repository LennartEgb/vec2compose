package dev.lennartegb.vec2compose.app.dragdrop

import androidx.compose.ui.Modifier
import dev.lennartegb.vec2compose.app.data.File

expect fun Modifier.onExternalDrag(
    onDragStart: () -> Unit,
    onDragExit: () -> Unit,
    onDrop: (List<File>) -> Unit,
    enabled: Boolean = true
): Modifier