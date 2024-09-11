package dev.lennartegb.vec2compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.lennartegb.vec2compose.app.data.File
import io.github.vinceglb.filekit.compose.PickerResultLauncher
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType

@Composable
fun App(modifier: Modifier = Modifier) {
    val (files, setFiles) = remember { mutableStateOf(emptyList<File>()) }
    val launcher = rememberVectorPickerLauncher(setFiles)

    if (files.isEmpty()) {
        UploadPane(
            modifier = modifier,
            onUploadedFiles = setFiles,
            onUploadFilesClick = launcher::launch
        )
    } else {
        PreviewPane(
            modifier = modifier,
            files = files,
            onClose = { setFiles(emptyList()) })
    }
}

@Composable
private fun rememberVectorPickerLauncher(onResult: (List<File>) -> Unit): PickerResultLauncher {
    return rememberFilePickerLauncher(
        type = PickerType.File(extensions = listOf("xml", "svg")),
        mode = PickerMode.Multiple()
    ) { platformFiles ->
        platformFiles?.also { pFiles ->
            onResult(pFiles.map { File(name = it.name, path = it.file.path) })
        }
    }
}
