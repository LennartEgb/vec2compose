package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons
import dev.lennartegb.vec2compose.app.theme.darkColors
import dev.lennartegb.vec2compose.app.theme.lightColors
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.svg.svgImageVectorParser
import dev.lennartegb.vec2compose.vectorDrawable.xmlImageVectorParser
import io.github.vinceglb.filekit.compose.PickerResultLauncher
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme(),
): AppState {
    val files = remember { mutableStateOf(emptyList<File>()) }
    val picker = rememberVectorPickerLauncher { files.value = it }
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
    files: State<List<File>>,
    private val picker: PickerResultLauncher
) {
    val files by files
    var isDark by mutableStateOf(isSystemDarkTheme)
        private set
    var colors: Colors by mutableStateOf(getColor())
        private set

    fun toggleTheme() {
        isDark = !isDark
        colors = getColor()
    }

    fun pickFiles() {
        picker.launch()
    }

    private fun getColor(): Colors = if (isDark) darkColors() else lightColors()
}


@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
    contentConverter: ContentConverter = rememberContentConverter(),
    copier: Copier = rememberCopier()
) {
    MaterialTheme(colors = appState.colors) {
        Box(modifier = modifier) {
            if (appState.files.isEmpty()) {
                UploadPane(
                    onUploadFilesClick = appState::pickFiles,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                PreviewPane(
                    files = appState.files,
                    modifier = Modifier.fillMaxSize(),
                    contentConverter = contentConverter,
                    copier = copier
                )
            }
            FloatingActionButton(
                modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
                onClick = appState::toggleTheme
            ) {
                val icon = if (appState.isDark) Icons.Moon else Icons.Sun
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}

@Composable
private fun rememberContentConverter(indentation: String = " ".repeat(4)): ContentConverter {
    return remember {
        val creator = ImageVectorCreator(indentation = indentation)
        ContentConverter { file ->
            when (file.extension) {
                "xml" -> xmlImageVectorParser()
                "svg" -> svgImageVectorParser()
                else -> error("Only XML and SVG files are allowed")
            }.parse(file.content)
                .mapCatching { creator.create(name = file.name, imageVector = it) }
        }
    }
}

@Composable
private fun rememberCopier(): Copier {
    val manager = LocalClipboardManager.current
    return remember { Copier { manager.setText(AnnotatedString(it)) } }
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
