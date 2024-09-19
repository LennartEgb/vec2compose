package dev.lennartegb.vec2compose.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lennartegb.vec2compose.app.icons.Icons
import org.jetbrains.compose.resources.painterResource
import vec2compose.composeapp.generated.resources.Res
import vec2compose.composeapp.generated.resources.logo

@Composable
fun UploadPane(
    onUploadFilesClick: () -> Unit,
    modifier: Modifier = Modifier
) = Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = spacedBy(32.dp, CenterVertically)
    ) {
        Image(
            modifier = Modifier.width(250.dp),
            painter = painterResource(Res.drawable.logo),
            contentDescription = null
        )

        Button(onClick = onUploadFilesClick) {
            Icon(Icons.Upload, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Upload")
        }
    }
}
