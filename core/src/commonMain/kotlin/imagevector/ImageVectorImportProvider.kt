package imagevector

class ImageVectorImportProvider {
    fun createImports(hasGroup: Boolean): List<String> = listOfNotNull(
        "import androidx.compose.ui.graphics.Color",
        "import androidx.compose.ui.graphics.PathFillType",
        "import androidx.compose.ui.graphics.SolidColor",
        "import androidx.compose.ui.graphics.StrokeCap",
        "import androidx.compose.ui.graphics.StrokeJoin",
        "import androidx.compose.ui.graphics.vector.ImageVector",
        if (hasGroup) "import androidx.compose.ui.graphics.vector.group" else null,
        "import androidx.compose.ui.graphics.vector.path",
        "import androidx.compose.ui.unit.dp"
    )
}
