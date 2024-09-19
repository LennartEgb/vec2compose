package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FileItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    selected: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val stroke: BorderStroke? = if (selected) {
        BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
    } else {
        null
    }
    Surface(
        modifier = modifier.width(IntrinsicSize.Min),
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        border = stroke
    ) {
        ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(contentPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
        }
    }
}
