package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.data.content
import dev.lennartegb.vec2compose.app.icons.Icons

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PreviewPane(
    files: List<File>,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    Surface(modifier = modifier, color = MaterialTheme.colors.background) {
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(files) { file ->
                    ListItem(
                        text = { Text(file.name) },
                        secondaryText = { Text(file.content) }
                    )
                }
            }
            CloseIcon(modifier = Modifier.align(TopEnd).padding(24.dp), onClick = onClose)
        }
    }
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
