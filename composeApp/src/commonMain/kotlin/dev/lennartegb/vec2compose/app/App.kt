package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Colors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
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

private val Copier.Data.description: String
    get() = when (this){
        is Copier.Data.FileContent -> extension
        is Copier.Data.ImageVector -> "ImageVector"
    }

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
) = MaterialTheme(colors = appState.colors) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            Fab(modifier = Modifier.padding(16.dp), onClick = appState::toggleTheme) {
                val icon = if (appState.isDark) Icons.Moon else Icons.Sun
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    ) {
        appState.file?.also { file ->
            val scope = rememberCoroutineScope()
            PreviewPane(
                file = file,
                modifier = Modifier.fillMaxSize(),
                contentConverter = contentConverter,
                copy = {
                    scope.launch {
                        copier(it)
                        with(scaffoldState.snackbarHostState) {
                            currentSnackbarData?.dismiss()
                            showSnackbar("Copied ${it.description}")
                        }
                    }
                }
            )
        } ?: UploadPane(
            onUploadFilesClick = appState::pickFiles,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun rememberContentConverter(
    indentation: String = " ".repeat(4)
): ContentConverter = remember {
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

@Composable
private fun rememberCopier(manager: ClipboardManager = LocalClipboardManager.current): Copier {
    return remember { Copier { data -> manager.setText(AnnotatedString(data.content)) } }
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
                ?.let { file ->
                    File(
                        name = file.name,
                        content = file.readBytes().decodeToString()
                    )
                }
                ?.also(onResult)
        }
    }
}

@Composable
fun Fab(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    FloatingActionButton(
        modifier = modifier.size(48.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        content = content
    )
}
