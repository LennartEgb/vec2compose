package dev.lennartegb.vec2compose.app

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons

@Composable
fun PreviewPane(
    file: File,
    imageVectorCreator: ImageVectorCreator = rememberImageVectorCreator(),
    contentConverter: ContentConverter = rememberContentConverter(imageVectorCreator),
    copy: Copier,
    onUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = spacedBy(16.dp),
            verticalAlignment = CenterVertically
        ) {
            IconButton(modifier = Modifier.size(96.dp), onClick = onUpdate) {
                Icon(
                    modifier = Modifier.size(96.dp),
                    imageVector = Icons.Refresh,
                    contentDescription = null
                )
            }
            Text(file.name, style = MaterialTheme.typography.h1)
        }

        Row(
            modifier = Modifier.align(CenterHorizontally),
            verticalAlignment = CenterVertically,
            horizontalArrangement = spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                val imageVectorContent = contentConverter(file).getOrElse { "" }
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = imageVectorContent,
                    onValueChange = {}
                )
                Fab(
                    modifier = Modifier.align(TopEnd).padding(16.dp),
                    onClick = { copy(imageVectorContent) }
                ) {
                    Icon(imageVector = Icons.Copy, contentDescription = null)
                }
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Center) {
                var iconSize by remember { mutableStateOf(IconSize.Small) }
                remember(file) { imageVectorCreator(file).map { it.toCompose(file.name) } }
                    .onSuccess {
                        Icon(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { iconSize = !iconSize }
                                .padding(16.dp)
                                .size(iconSize),
                            imageVector = it,
                            contentDescription = null
                        )
                    }.onFailure {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Error interpreting ImageVector: $it"
                        )
                    }
            }
        }
    }
}

private enum class IconSize { Small, Large }

private operator fun IconSize.not(): IconSize = when (this) {
    IconSize.Small -> IconSize.Large
    IconSize.Large -> IconSize.Small
}

private fun Modifier.size(size: IconSize): Modifier = composed {
    val animatedSize by animateDpAsState(
        when (size) {
            IconSize.Small -> 48.dp
            IconSize.Large -> 96.dp
        }
    )
    size(animatedSize)
}
