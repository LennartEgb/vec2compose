package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons

fun interface ContentConverter : (File) -> Result<String>
fun interface Copier : (Copier.Data) -> Unit {
    sealed interface Data {
        val content: String

        data class FileContent(val extension: String, override val content: String) : Data
        data class ImageVector(override val content: String) : Data
    }
}

@Composable
fun PreviewPane(
    file: File,
    contentConverter: ContentConverter,
    copy: Copier,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) = Scaffold(scaffoldState = scaffoldState, modifier = modifier) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = spacedBy(16.dp)
    ) {
        Text(file.name, style = MaterialTheme.typography.h1)
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = spacedBy(16.dp)) {
                ContentColumn(
                    title = file.extension,
                    content = file.content,
                    modifier = Modifier.weight(1f),
                    copy = {
                        file.let { Copier.Data.FileContent(it.extension, it.content) }.also(copy)
                    }
                )
                val imageVectorContent = contentConverter(file).getOrElse { "" }
                ContentColumn(
                    title = "ImageVector",
                    content = imageVectorContent,
                    modifier = Modifier.weight(1f),
                    copy = { copy(Copier.Data.ImageVector(imageVectorContent)) }
                )
            }
        }
    }
}

@Composable
private fun ContentColumn(
    title: String,
    content: String,
    copy: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    val state: ScrollState = rememberScrollState()
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.h2)
            Fab(onClick = copy) {
                Icon(imageVector = Icons.Copy, contentDescription = null)
            }
        }
        Box {
            Text(
                modifier = Modifier.verticalScroll(state).fillMaxWidth(),
                text = content,
                style = MaterialTheme.typography.body2
            )
            VerticalScrollbar(
                modifier = Modifier.align(CenterEnd),
                adapter = rememberScrollbarAdapter(state)
            )
        }
    }
}
