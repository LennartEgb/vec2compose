package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.ImageVector.Path.FillColor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class KeywordColorParserTest {
    @Test
    fun parse_none_to_null() {
        assertNull(KeywordColorParser().parse("none"))
    }

    @Test
    fun parse_black_to_FillColor_0xFF000000() {
        assertEquals(
            actual = KeywordColorParser().parse("black"),
            expected = FillColor(red = 0x00, green = 0x00, blue = 0x00, alpha = 0xFF)
        )
    }
}
