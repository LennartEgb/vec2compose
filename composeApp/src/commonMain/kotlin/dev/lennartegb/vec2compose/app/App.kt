package dev.lennartegb.vec2compose.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.icons.Icons
import dev.lennartegb.vec2compose.app.theme.ComposeTheme
import org.jetbrains.compose.resources.painterResource
import vec2compose.composeapp.generated.resources.Res
import vec2compose.composeapp.generated.resources.logo

@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
    contentConverter: ContentConverter = rememberContentConverter(),
    copier: Copier = rememberCopier(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = ComposeTheme(isDark = appState.isDark) {
    DesktopScaffold(
        modifier = modifier,
        floatingActionButton = {
            Fab(modifier = Modifier.padding(16.dp), onClick = appState::toggleTheme) {
                AnimatedContent(if (appState.isDark) Icons.Moon else Icons.Sun) { icon ->
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        listContent = {
            DesktopList(
                bottomButton = {
                    Button(onClick = appState::pickFile) {
                        Icon(imageVector = Icons.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Upload")
                    }
                }
            ) {
                // todo: add files in a list
            }
        }
    ) {
        Empty(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun Empty(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(Res.drawable.logo),
            alpha = .5f,
            contentDescription = null
        )
    }
}
