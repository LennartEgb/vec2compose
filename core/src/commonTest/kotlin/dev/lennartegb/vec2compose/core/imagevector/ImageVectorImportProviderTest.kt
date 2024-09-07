package dev.lennartegb.vec2compose.core.imagevector

import kotlin.test.Test
import kotlin.test.assertEquals

class ImageVectorImportProviderTest {

    @Test
    fun `create imports without groups`() {
        val provider = ImageVectorImportProvider()
        assertEquals(
            actual = provider.createImports(hasGroup = false),
            expected = listOf(
                "import androidx.compose.ui.graphics.Color",
                "import androidx.compose.ui.graphics.PathFillType",
                "import androidx.compose.ui.graphics.SolidColor",
                "import androidx.compose.ui.graphics.StrokeCap",
                "import androidx.compose.ui.graphics.StrokeJoin",
                "import androidx.compose.ui.graphics.vector.ImageVector",
                "import androidx.compose.ui.graphics.vector.path",
                "import androidx.compose.ui.unit.dp"
            )
        )
    }

    @Test
    fun `create imports with groups`() {
        val provider = ImageVectorImportProvider()
        assertEquals(
            actual = provider.createImports(hasGroup = true),
            expected = listOf(
                "import androidx.compose.ui.graphics.Color",
                "import androidx.compose.ui.graphics.PathFillType",
                "import androidx.compose.ui.graphics.SolidColor",
                "import androidx.compose.ui.graphics.StrokeCap",
                "import androidx.compose.ui.graphics.StrokeJoin",
                "import androidx.compose.ui.graphics.vector.ImageVector",
                "import androidx.compose.ui.graphics.vector.group",
                "import androidx.compose.ui.graphics.vector.path",
                "import androidx.compose.ui.unit.dp"
            )
        )
    }
}
