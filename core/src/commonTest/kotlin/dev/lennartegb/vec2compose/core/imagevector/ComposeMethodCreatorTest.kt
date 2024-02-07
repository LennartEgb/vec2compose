package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.VectorSet
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ComposeMethodCreatorTest {

    private val testVectorSet = VectorSet(
        width = 6,
        height = 12,
        viewportWidth = 24f,
        viewportHeight = 48f,
        groups = emptyList(),
        paths = emptyList(),
    )

    @Test
    fun parse_constructor_returns_builder() {
        val creator = ComposeMethodCreator(indentation = "  ")
        val actual = creator.parseConstructor(name = "hello", set = testVectorSet)
        assertEquals(
            expected = """
                ImageVector.Builder(
                  name = "hello",
                  defaultWidth = 6.dp,
                  defaultHeight = 12.dp,
                  viewportWidth = 24.0f,
                  viewportHeight = 48.0f
                )
            """.trimIndent(),
            actual = actual
        )
    }
}
