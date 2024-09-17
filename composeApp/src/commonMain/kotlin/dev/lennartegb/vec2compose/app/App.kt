package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.icons.Icons
import dev.lennartegb.vec2compose.app.theme.ComposeTheme
import kotlinx.coroutines.launch

private val Copier.Data.description: String
    get() = when (this) {
        is Copier.Data.FileContent -> extension
        is Copier.Data.ImageVector -> "ImageVector"
    }

@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
    contentConverter: ContentConverter = rememberContentConverter(),
    copier: Copier = rememberCopier()
) = ComposeTheme(isDark = appState.isDark) {
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
            onUploadFilesClick = appState::pickFile,
            modifier = Modifier.fillMaxSize()
        )
    }
}
