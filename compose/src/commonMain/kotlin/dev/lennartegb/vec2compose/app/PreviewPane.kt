package dev.lennartegb.vec2compose.app

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.app.icons.Icons
import org.jetbrains.compose.ui.tooling.preview.Preview

fun interface ContentConverter : (File) -> Result<String>
fun interface Copier : suspend (String) -> Unit

@Composable
fun PreviewPane(
    file: File,
    contentConverter: ContentConverter,
    copy: (String) -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) = Scaffold(scaffoldState = scaffoldState, modifier = modifier) {
    contentConverter(file)
        .onSuccess {
            DetailColumn(
                content = it,
                copy = copy
            )
        }
        .onFailure {
            Error(modifier = Modifier.fillMaxSize())
        }
}

@Preview
@Composable
private fun Error(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(16.dp, CenterVertically),
        horizontalAlignment = CenterHorizontally
    ) {
        Icon(imageVector = Icons.Warning, contentDescription = null)
        Text("Something went wrong!")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailColumn(
    content: String,
    copy: (String) -> Unit,
    modifier: Modifier = Modifier
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
