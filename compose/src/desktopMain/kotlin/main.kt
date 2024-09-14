@file:Suppress("ktlint")

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.lennartegb.vec2compose.app.App
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "vec2compose"
    ) {
        LaunchedEffect(Unit) {
            window.minimumSize = Dimension(400, 300)
        }
        App(modifier = Modifier.fillMaxSize())
    }
}
