package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.VectorSet.Path.FillColor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class KeywordColorParserTest {
    @Test
    fun `parse none to null`() {
        val parser = KeywordColorParser()
        assertNull(parser.parse("none"))
    }

    @Test
    fun `parse black to FillColor 0xFF000000`() {
        val parser = KeywordColorParser()
        assertEquals(
            actual = parser.parse("black"),
            expected = FillColor(red = 0x00, green = 0x00, blue = 0x00, alpha = 0xFF)
        )
    }
}
