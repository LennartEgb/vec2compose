package dev.lennartegb.vec2compose.app.dragdrop

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalWindowInfo
import dev.lennartegb.vec2compose.app.data.File
import org.w3c.files.FileReader

external fun registerDragAndDropListener(
    onDragOver: () -> Unit,
    onDragLeave: () -> Unit,
    onDrop: (org.w3c.files.File) -> Unit,
)

external fun unregisterDragAndDropListener()

actual fun Modifier.onExternalDrag(
    onDragStart: () -> Unit,
    onDragExit: () -> Unit,
    onDrop: (List<File>) -> Unit,
    enabled: Boolean,
): Modifier = composed {
    DisposableEffect(LocalWindowInfo.current) {
        registerDragAndDropListener(
            onDragOver = onDragStart,
            onDragLeave = onDragExit,
            onDrop = { file ->
                val reader = FileReader()
                reader.onload = {
                    val content = reader.result?.toString().orEmpty()
                    onDrop(listOf(File(name = file.name, content = content)))
                }
                reader.readAsText(file)
            }
        )
        onDispose { unregisterDragAndDropListener() }
    }
    Modifier
}