package imagevector

internal class ImageVectorImportProvider {
  fun createImports(): List<String> =
      listOf(
          "import androidx.compose.ui.graphics.Color",
          "import androidx.compose.ui.graphics.PathFillType",
          "import androidx.compose.ui.graphics.SolidColor",
          "import androidx.compose.ui.graphics.StrokeCap",
          "import androidx.compose.ui.graphics.StrokeJoin",
          "import androidx.compose.ui.graphics.vector.ImageVector",
          "import androidx.compose.ui.graphics.vector.path",
          "import androidx.compose.ui.unit.dp",
      )
}
