package dev.lennartegb.vec2compose.core.imagevector

import kotlin.test.Test
import kotlin.test.assertEquals

class ImageVectorImportProviderTest {

    @Test
    fun `create imports without groups`() {
        val provider = ImageVectorImportProvider()
        assertEquals(
            actual = provider.getImports(hasGroup = false),
            expected = "import androidx.compose.ui.graphics.Color\n" +
                "import androidx.compose.ui.graphics.PathFillType\n" +
                "import androidx.compose.ui.graphics.SolidColor\n" +
                "import androidx.compose.ui.graphics.StrokeCap\n" +
                "import androidx.compose.ui.graphics.StrokeJoin\n" +
                "import androidx.compose.ui.graphics.vector.ImageVector\n" +
                "import androidx.compose.ui.graphics.vector.path\n" +
                "import androidx.compose.ui.unit.dp"
        )
    }

    @Test
    fun `create imports with groups`() {
        val provider = ImageVectorImportProvider()
        assertEquals(
            actual = provider.getImports(hasGroup = true),
            expected = "import androidx.compose.ui.graphics.Color\n" +
                "import androidx.compose.ui.graphics.PathFillType\n" +
                "import androidx.compose.ui.graphics.SolidColor\n" +
                "import androidx.compose.ui.graphics.StrokeCap\n" +
                "import androidx.compose.ui.graphics.StrokeJoin\n" +
                "import androidx.compose.ui.graphics.vector.ImageVector\n" +
                "import androidx.compose.ui.graphics.vector.group\n" +
                "import androidx.compose.ui.graphics.vector.path\n" +
                "import androidx.compose.ui.unit.dp"
        )
    }
}
