package dev.lennartegb.vec2compose.app

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons
import kotlinx.coroutines.launch

fun interface ContentConverter : (File) -> Result<String>
fun interface Copier : suspend (String) -> Unit

@Composable
fun PreviewPane(
    files: List<File>,
    contentConverter: ContentConverter,
    copier: Copier,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val padding = 16.dp
    var detail by remember { mutableStateOf<File?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    ListDetailPane(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        list = {
            Box {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    contentPadding = PaddingValues(vertical = padding)
                ) {
                    fileListItems(
                        selected = detail,
                        files = files,
                        onClick = { detail = it },
                        modifier = Modifier.padding(horizontal = padding)
                    )
                }
                VerticalScrollbar(
                    modifier = Modifier.align(CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(state)
                )
            }
        }
    ) {
        val detailModifier = Modifier.fillMaxSize()
        detail?.let { contentConverter(it).getOrNull() }?.also { detail ->
            DetailColumn(
                modifier = detailModifier,
                content = detail,
                copy = {
                    scope.launch {
                        copier(it)
                        snackbarHostState.showSnackbar("Copied")
                    }
                }
            )
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            Text(text = "Select a file")
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailColumn(
    content: String,
    copy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        stickyHeader {
            Button(onClick = { copy(content) }) {
                Text(text = "Copy")
            }
        }
        item { Text(modifier = Modifier.animateContentSize(), text = content) }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun LazyListScope.fileListItems(
    selected: File?,
    files: List<File>,
    onClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) = items(files) { file ->
    val color: Color = MaterialTheme.colors.background
    val selectedColor: Color = contentColorFor(color).copy(alpha = .1f)
    ListItem(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable(onClick = { onClick(file) })
            .background(
                if (file == selected) selectedColor else color,
                shape = MaterialTheme.shapes.medium
            )
            .then(modifier),
        text = { Text(file.name) },
    )
}

@Composable
private fun CloseIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(color)
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = color)
            .clickable(onClick = onClick)
            .padding(8.dp),
    ) {
        Icon(
            imageVector = Icons.Close,
            contentDescription = "close",
            tint = contentColor
        )
    }
}
