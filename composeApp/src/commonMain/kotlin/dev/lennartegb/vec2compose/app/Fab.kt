package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Fab(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.surface,
        content = content
    )
}

@Composable
fun SmallFab(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    FloatingActionButton(
        modifier = modifier.size(48.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.surface,
        content = content
    )
}
