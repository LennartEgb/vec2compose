package dev.lennartegb.vec2compose.svg

import dev.lennartegb.vec2compose.core.ImageVector
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SVGColorParserTest {
    @Test
    fun parse_none_to_null() {
        assertNull(SVGColorParser().parse("none"))
    }

    @Test
    fun parse_red_to_red_color() {
        assertEquals(
            actual = SVGColorParser().parse("red"),
            expected = ImageVector.Path.FillColor(
                alpha = 0xFF,
                red = 0xFF,
                blue = 0x00,
                green = 0x00
            )
        )
    }

    @Test
    fun parse_f00_to_red_color() {
        assertEquals(
            actual = SVGColorParser().parse("#F00"),
            expected = ImageVector.Path.FillColor(
                alpha = 0xFF,
                red = 0xFF,
                blue = 0x00,
                green = 0x00
            )
        )
    }

    @Test
    fun parse_invalid_color_returns_null() {
        assertNull(actual = SVGColorParser().parse("a color"))
    }
}
