package dev.lennartegb.vec2compose.core.imagevector

fun interface ImageVectorImportProvider {
    fun getImports(hasGroup: Boolean): String
}

@Suppress("ktlint")
fun ImageVectorImportProvider() = ImageVectorImportProvider { hasGroup ->
    "import androidx.compose.ui.graphics.Color\n" +
        "import androidx.compose.ui.graphics.PathFillType\n" +
        "import androidx.compose.ui.graphics.SolidColor\n" +
        "import androidx.compose.ui.graphics.StrokeCap\n" +
        "import androidx.compose.ui.graphics.StrokeJoin\n" +
        "import androidx.compose.ui.graphics.vector.ImageVector\n" +
        "import androidx.compose.ui.graphics.vector.group\n".takeIf { hasGroup }.orEmpty() +
        "import androidx.compose.ui.graphics.vector.path\n" +
        "import androidx.compose.ui.unit.dp"
}
