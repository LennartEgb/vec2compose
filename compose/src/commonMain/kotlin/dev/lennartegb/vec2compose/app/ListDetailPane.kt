package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize.Min
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun ListDetailPane(
    list: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    detailContent: @Composable () -> Unit
) {
    val snackbar = @Composable { m: Modifier ->
        SnackbarHost(modifier = m, hostState = snackbarHostState)
    }
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        Row {
            Box(
                Modifier.shadow(elevation = 4.dp)
                    .background(MaterialTheme.colors.background)
                    .fillMaxHeight()
                    .widthIn(min = 200.dp, max = 300.dp)
            ) {
                list()
            }
            Box(modifier = Modifier.fillMaxSize()) {
                detailContent()
                snackbar(Modifier.align(BottomCenter))
            }
        }
    }
}
