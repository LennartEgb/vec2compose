import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.lennartegb.vec2compose.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "vec2compose"
    ) {
        App(modifier = Modifier.fillMaxSize())
    }
}