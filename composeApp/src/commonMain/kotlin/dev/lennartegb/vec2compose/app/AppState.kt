package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.lennartegb.vec2compose.app.data.File
import io.github.vinceglb.filekit.compose.PickerResultLauncher
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme()
): AppState {
    val file = remember { mutableStateOf<File?>(null) }
    val picker = rememberVectorPickerLauncher { file.value = it }
    return remember {
        AppState(
            isSystemDarkTheme = isSystemDarkTheme,
            file = file,
            picker = picker
        )
    }
}

class AppState(
    isSystemDarkTheme: Boolean,
    file: State<File?>,
    private val picker: PickerResultLauncher
) {
    val file by file
    var isDark by mutableStateOf(isSystemDarkTheme)
        private set

    fun toggleTheme() {
        isDark = !isDark
    }

    fun pickFile() {
        picker.launch()
    }
}

@Composable
private fun rememberVectorPickerLauncher(onResult: (File) -> Unit): PickerResultLauncher {
    val scope = rememberCoroutineScope()
    return rememberFilePickerLauncher(
        type = PickerType.File(extensions = listOf("xml", "svg")),
        mode = PickerMode.Single
    ) { platformFile ->
        scope.launch {
            platformFile
                ?.let { File(name = it.name, content = it.readBytes().decodeToString()) }
                ?.also(onResult)
        }
    }
}
