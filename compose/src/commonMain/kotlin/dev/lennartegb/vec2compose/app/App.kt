package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.data.content
import dev.lennartegb.vec2compose.app.dragdrop.onExternalDrag
import dev.lennartegb.vec2compose.app.icons.Icons
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType

@Composable
fun App(modifier: Modifier = Modifier) {
    val (files, setFiles) = remember { mutableStateOf(emptyList<File>()) }
    val launcher = rememberFilePickerLauncher(
        type = PickerType.File(extensions = listOf("xml", "svg")),
        mode = PickerMode.Multiple()
    ) { platformFiles ->
        platformFiles?.also { pFiles ->
            setFiles(pFiles.map { File(name = it.name, path = it.file.path) })
        }
    }
    if (files.isEmpty()) {
        UploadPane(
            modifier = modifier.fillMaxSize(),
            onUploadedFiles = setFiles,
            onUploadFilesClick = launcher::launch
        )
    } else {
        PreviewPane(
            modifier = modifier.fillMaxSize(),
            files = files,
            onClose = { setFiles(emptyList()) })
    }
}

@Composable
private fun UploadPane(
    onUploadedFiles: (List<File>) -> Unit,
    onUploadFilesClick: () -> Unit,
    modifier: Modifier = Modifier
) = Surface(modifier = modifier) {
    var isDragging by remember { mutableStateOf(value = false) }
    val borderAlpha = if (isDragging) ContentAlpha.high else ContentAlpha.disabled
    val borderColor = MaterialTheme.colors.onBackground.copy(alpha = borderAlpha)
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Center
    ) {
        val maxWidth = 1_000.dp
        UploadColumn(
            modifier = Modifier
                .sizeIn(
                    minWidth = maxWidth / 3,
                    maxWidth = maxWidth,
                    minHeight = maxWidth * .25f,
                    maxHeight = maxWidth * .75f
                )
                .dottedBorder(color = borderColor, radius = 8.dp)
                .onExternalDrag(
                    onDragStart = { isDragging = true },
                    onDragExit = { isDragging = false },
                    onDrop = { files ->
                        val regex = """.*\.(svg|xml)$""".toRegex()
                        onUploadedFiles(files.filter { regex.matches(it.name) })
                        isDragging = false
                    }
                ),
            onUploadFilesClick = onUploadFilesClick
        )
    }
}

@Composable
private fun UploadColumn(onUploadFilesClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onUploadFilesClick)
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = spacedBy(8.dp, CenterVertically)
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Upload,
            contentDescription = "download",
            tint = MaterialTheme.colors.onBackground
        )
        Text(
            text = "Drag and drop files",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PreviewPane(
    files: List<File>,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    Surface(modifier = modifier) {
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
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colors.primary)
                    .clickable(onClick = onClose)
                    .padding(8.dp)
                    .align(Alignment.TopStart),
            ) {
                Icon(
                    imageVector = Icons.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

private fun Modifier.dottedBorder(
    color: Color,
    radius: Dp = 0.dp,
    width: Dp = 2.dp,
    intervals: FloatArray = floatArrayOf(10f, 10f)
): Modifier = drawBehind {
    val style = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(intervals, 0f)
    )
    drawRoundRect(color = color, style = style, cornerRadius = CornerRadius(radius.toPx()))
}