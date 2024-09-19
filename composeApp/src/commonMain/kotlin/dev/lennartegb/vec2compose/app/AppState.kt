package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
    val files = remember { mutableStateListOf<File>() }
    val picker = rememberVectorPickerLauncher { files.addAll(it) }
    return remember {
        AppState(
            isSystemDarkTheme = isSystemDarkTheme,
            files = files,
            picker = picker
        )
    }
}

class AppState(
    isSystemDarkTheme: Boolean,
    val files: SnapshotStateList<File>,
    private val picker: PickerResultLauncher
) {
    var selectedFile by mutableStateOf<File?>(null)
        private set

    var isDark by mutableStateOf(isSystemDarkTheme)
        private set

    fun toggleTheme() {
        isDark = !isDark
    }

    fun pickFile() {
        picker.launch()
    }

    fun select(file: File) {
        selectedFile = file
    }
}

@Composable
private fun rememberVectorPickerLauncher(onResult: (List<File>) -> Unit): PickerResultLauncher {
    val scope = rememberCoroutineScope()
    return rememberFilePickerLauncher(
        type = PickerType.File(extensions = listOf("xml", "svg")),
        mode = PickerMode.Multiple()
    ) { platformFile ->
        scope.launch {
            platformFile
                ?.map { File(name = it.name, content = it.readBytes().decodeToString()) }
                ?.also(onResult)
        }
    }
}
