package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapNotNull
import androidx.compose.ui.util.fastMaxBy

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DesktopScaffold(
    listContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val safeInsets = remember(contentWindowInsets) {
        androidx.compose.foundation.layout.MutableWindowInsets(contentWindowInsets)
    }
    Surface(
        modifier = modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = containerColor,
        contentColor = contentColor
    ) {
        DesktopScaffoldLayout(
            content = content,
            snackbar = snackbarHost,
            contentWindowInsets = safeInsets,
            fab = floatingActionButton,
            listContent = listContent
        )
    }
}

@Composable
private fun DesktopScaffoldLayout(
    content: @Composable (PaddingValues) -> Unit,
    listContent: @Composable () -> Unit,
    snackbar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    contentWindowInsets: WindowInsets
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val snackbarPlaceables =
            subcompose(DesktopScaffoldLayoutContent.Snackbar, snackbar).fastMap {
                // respect only bottom and horizontal for snackbar and fab
                val leftInset = contentWindowInsets
                    .getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset = contentWindowInsets
                    .getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                // offset the snackbar constraints by the insets values
                it.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
            }

        val snackbarHeight = snackbarPlaceables.fastMaxBy { it.height }?.height ?: 0
        val snackbarWidth = snackbarPlaceables.fastMaxBy { it.width }?.width ?: 0

        val fabPlaceables =
            subcompose(DesktopScaffoldLayoutContent.Fab, fab).fastMapNotNull { measurable ->
                // respect only bottom and horizontal for snackbar and fab
                val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                val rightInset =
                    contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
                val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)
                measurable.measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))
                    .takeIf { it.height != 0 && it.width != 0 }
            }

        val fabHeight = fabPlaceables.fastMaxBy { it.height }?.height ?: 0
        val fabWidth = fabPlaceables.fastMaxBy { it.width }?.width ?: 0

        val fabOffsetFromBottom = fabHeight + contentWindowInsets.getBottom(this@SubcomposeLayout)

        val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
            snackbarHeight + fabOffsetFromBottom
        } else {
            0
        }

        val listContentPlaceables = subcompose(DesktopScaffoldLayoutContent.List) {
            listContent()
        }.fastMap { it.measure(looseConstraints) }
        val listWidth = listContentPlaceables.fastMaxBy { it.width }!!.width

        val bodyContentPlaceables = subcompose(DesktopScaffoldLayoutContent.Content) {
            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
            val innerPadding = PaddingValues(
                top = insets.calculateTopPadding(),
                bottom = insets.calculateBottomPadding(),
                start = insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection),
                end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
            )
            content(innerPadding)
        }.fastMap { it.measure(looseConstraints.copy(maxWidth = layoutWidth - listWidth)) }
        val bodyContentWidth = bodyContentPlaceables.fastMaxBy { it.width }!!.width

        layout(width = layoutWidth, height = layoutHeight) {
            bodyContentPlaceables.fastForEach {
                it.place(x = listWidth, y = 0)
            }
            listContentPlaceables.fastForEach {
                it.place(0, 0)
            }
            fabPlaceables.fastForEach {
                it.place(x = layoutWidth - fabWidth, y = layoutHeight - fabOffsetFromBottom)
            }
            snackbarPlaceables.fastForEach {
                val padding = (bodyContentWidth - snackbarWidth) / 2
                val x = padding + listWidth +
                    contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
                it.place(x = x, y = layoutHeight - snackbarOffsetFromBottom)
            }
        }
    }
}

private enum class DesktopScaffoldLayoutContent {
    Snackbar,
    Fab,
    List,
    Content
}
