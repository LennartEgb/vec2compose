import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import dev.lennartegb.vec2compose.app.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("vec2compose") {
        App()
    }
}