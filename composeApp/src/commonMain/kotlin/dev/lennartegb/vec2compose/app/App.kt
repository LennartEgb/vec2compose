package dev.lennartegb.vec2compose.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons
import dev.lennartegb.vec2compose.app.theme.ComposeTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import vec2compose.composeapp.generated.resources.Res
import vec2compose.composeapp.generated.resources.converter_error
import vec2compose.composeapp.generated.resources.copied_image_vector
import vec2compose.composeapp.generated.resources.logo
import vec2compose.composeapp.generated.resources.upload

@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
    imageVectorCreator: ImageVectorCreator = rememberImageVectorCreator(),
    contentConverter: ContentConverter = rememberContentConverter(),
    copier: Copier = rememberCopier(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = ComposeTheme(isDark = appState.isDark) {
    val scope = rememberCoroutineScope()
    DesktopScaffold(
        modifier = modifier,
        floatingActionButton = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = spacedBy(16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = appState.selectedFile != null,
                    enter = scaleIn()
                ) {
                    val fabMessage = stringResource(Res.string.copied_image_vector)
                    SmallFab(
                        onClick = {
                            val file = appState.selectedFile ?: return@SmallFab
                            scope.launch {
                                val result = contentConverter(file).onSuccess { copier(it) }
                                if (result.isSuccess) {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(fabMessage)
                                }
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Copy, contentDescription = null)
                    }
                }
                Fab(onClick = appState::toggleTheme) {
                    AnimatedContent(if (appState.isDark) Icons.Moon else Icons.Sun) { icon ->
                        Icon(imageVector = icon, contentDescription = null)
                    }
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
                        Text(text = stringResource(Res.string.upload))
                    }
                }
            ) { padding ->
                LazyColumn(
                    contentPadding = padding,
                    verticalArrangement = spacedBy(4.dp),
                    horizontalAlignment = CenterHorizontally
                ) {
                    items(appState.files) { file ->
                        FileItem(
                            modifier = Modifier.fillMaxWidth(),
                            selected = file == appState.selectedFile,
                            onClick = { appState.select(file) }
                        ) {
                            Text(file.name)
                            imageVectorCreator(file).getOrNull()?.toCompose(file.name)?.also {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = it,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        DesktopDetail(modifier = Modifier.fillMaxSize(), file = appState.selectedFile)
    }
}

@Composable
private fun DesktopDetail(modifier: Modifier, file: File?) {
    file?.also { FileContent(modifier = modifier, file = it) } ?: Empty(modifier)
}

@Composable
private fun FileContent(
    file: File,
    modifier: Modifier = Modifier,
    contentConverter: ContentConverter = rememberContentConverter(),
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    val scrollState = rememberScrollState()
    Box(modifier) {
        ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
            val maxMod = Modifier.fillMaxSize()
            remember(file) { contentConverter(file) }
                .onSuccess { content ->
                    Box(modifier = maxMod.verticalScroll(scrollState)) {
                        AnimatedContent(
                            targetState = content,
                            transitionSpec = {
                                slideIntoContainer(End) togetherWith slideOutOfContainer(End)
                            }
                        ) {
                            Text(
                                modifier = Modifier.fillMaxSize().padding(contentPadding),
                                text = it
                            )
                        }
                    }
                }
                .onFailure {
                    Column(
                        modifier = maxMod.padding(contentPadding),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = spacedBy(16.dp, alignment = CenterVertically)
                    ) {
                        Icon(imageVector = Icons.Warning, contentDescription = null)
                        Text(
                            text = stringResource(Res.string.converter_error),
                            textAlign = TextAlign.Center
                        )
                        Text("Reason: $it", color = LocalContentColor.current.copy(alpha = .8f))
                    }
                }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(scrollState)
            )
        }
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
