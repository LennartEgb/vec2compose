import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.lennartegb.vec2compose.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "vec2compose"
    ) {
        App()
    }
}