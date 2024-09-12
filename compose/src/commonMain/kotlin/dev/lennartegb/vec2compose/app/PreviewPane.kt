package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.svg.svgImageVectorParser
import dev.lennartegb.vec2compose.vectorDrawable.xmlImageVectorParser

@Composable
fun PreviewPane(
    files: List<File>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    Surface(modifier = modifier, color = MaterialTheme.colors.background) {
        val (detail, setDetail) = remember { mutableStateOf<File?>(null) }
        val padding = 16.dp
        Row(modifier = modifier) {
            LazyColumn(
                modifier = Modifier
                    .shadow(elevation = 4.dp)
                    .background(MaterialTheme.colors.background)
                    .fillMaxHeight()
                    .weight(1f),
                state = state,
                contentPadding = PaddingValues(vertical = padding)
            ) {
                fileListItems(
                    selected = detail,
                    files = files,
                    onClick = setDetail,
                    modifier = Modifier.padding(horizontal = padding)
                )
            }
            Box(Modifier.fillMaxHeight().weight(2f).padding(16.dp)) {
                if (detail != null) {
                    DetailColumn(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        file = detail,
                        fileConverter = converter@{
                            val imageVector = when (detail.extension) {
                                "xml" -> xmlImageVectorParser()
                                "svg" -> svgImageVectorParser()
                                else -> return@converter "ERROR"
                            }.parse(detail.content)
                            val creator = ImageVectorCreator(indentation = " ".repeat(4))
                            imageVector
                                .mapCatching {
                                    creator.create(name = detail.name, imageVector = it)
                                }
                                .getOrElse { "ERROR" }
                        }
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                        Text(text = "Select a file")
                    }
                }
            }

        }
    }
}

@Composable
fun DetailColumn(
    file: File,
    modifier: Modifier = Modifier,
    fileConverter: () -> String
) {
    val clipBoardManager = LocalClipboardManager.current
    Column(modifier = modifier) {
        var content by remember(file) { mutableStateOf(file.content) }
        Text(text = content)

        Row(horizontalArrangement = spacedBy(8.dp)) {
            Button(onClick = { clipBoardManager.setText(AnnotatedString(file.content)) }) {
                Text(text = "Copy")
            }
            var enabled: Boolean by remember { mutableStateOf(true) }
            Button(
                enabled = enabled,
                onClick = { content = fileConverter(); enabled = false }
            ) {
                Text(text = "Convert")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun LazyListScope.fileListItems(
    selected: File?,
    files: List<File>,
    onClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) = items(files) { file ->
    val color: Color = MaterialTheme.colors.surface
    val selectedColor: Color = contentColorFor(color).copy(alpha = .1f)
    ListItem(
        modifier = Modifier
            .clickable(onClick = { onClick(file) })
            .background(if (file == selected) selectedColor else color)
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
