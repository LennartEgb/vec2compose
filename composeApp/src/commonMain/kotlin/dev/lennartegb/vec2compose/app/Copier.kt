package dev.lennartegb.vec2compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

fun interface Copier : suspend (String) -> Unit

@Composable
fun rememberCopier(
    manager: ClipboardManager = LocalClipboardManager.current
): Copier = remember { Copier { data -> manager.setText(AnnotatedString(data)) } }
