package dev.lennartegb.vec2compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

fun interface Copier : (Copier.Data) -> Unit {
    sealed interface Data {
        val content: String

        data class FileContent(val extension: String, override val content: String) : Data
        data class ImageVector(override val content: String) : Data
    }
}

@Composable
fun rememberCopier(
    manager: ClipboardManager = LocalClipboardManager.current
): Copier = remember { Copier { data -> manager.setText(AnnotatedString(data.content)) } }
