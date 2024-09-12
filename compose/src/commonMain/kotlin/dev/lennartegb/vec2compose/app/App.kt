package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons
import io.github.vinceglb.filekit.compose.PickerResultLauncher
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.FileKit
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch

@Composable
fun App(
    modifier: Modifier = Modifier,
    isSystemDarkTheme: Boolean = isSystemInDarkTheme()
) {
    var darkTheme: Boolean by remember { mutableStateOf(isSystemDarkTheme) }
    val colors = if (darkTheme) darkColors() else lightColors()
    MaterialTheme(colors = colors) {
        Box(modifier = modifier) {
            val (files, setFiles) = remember { mutableStateOf(emptyList<File>()) }
            val launcher = rememberVectorPickerLauncher(setFiles)

            if (files.isEmpty()) {
                UploadPane(
                    modifier = Modifier.fillMaxSize(),
                    onUploadedFiles = setFiles,
                    onUploadFilesClick = launcher::launch
                )
            } else {
                PreviewPane(
                    modifier = Modifier.fillMaxSize(),
                    files = files,
                    onClose = { setFiles(emptyList()) })
            }
            FloatingActionButton(
                modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
                onClick = { darkTheme = !darkTheme }
            ) {
                val icon = if (darkTheme) Icons.Moon else Icons.Sun
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}

@Composable
private fun rememberVectorPickerLauncher(onResult: (List<File>) -> Unit): PickerResultLauncher {
    val scope = rememberCoroutineScope()
    return rememberFilePickerLauncher(
        type = PickerType.File(extensions = listOf("xml", "svg")),
        mode = PickerMode.Multiple()
    ) { platformFiles ->
        scope.launch {
            platformFiles
                ?.map { file ->
                    File(
                        name = file.name,
                        content = file.readBytes().decodeToString(),
                    )
                }
                ?.also(onResult)
        }
    }
}
