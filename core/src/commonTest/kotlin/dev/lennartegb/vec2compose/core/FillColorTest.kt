package dev.lennartegb.vec2compose.core

import kotlin.test.Test
import kotlin.test.assertEquals

class FillColorTest {
    @Test
    fun white_color_to_0xffffffff() {
        val color = ImageVector.Path.FillColor(red = 0xff, green = 0xff, blue = 0xff, alpha = 0xff)
        assertEquals(expected = "Color(0xffffffff)", actual = color.toString())
    }

    @Test
    fun black_color_to_0xff000000() {
        val color = ImageVector.Path.FillColor(red = 0x00, green = 0x00, blue = 0x00, alpha = 0xff)
        assertEquals(expected = "Color(0xff000000)", actual = color.toString())
    }

    @Test
    fun red_color_to_0xffff0000() {
        val color = ImageVector.Path.FillColor(red = 0xff, green = 0x00, blue = 0x00, alpha = 0xff)
        assertEquals(expected = "Color(0xffff0000)", actual = color.toString())
    }

    @Test
    fun blue_color_to_0xff00ff00() {
        val color = ImageVector.Path.FillColor(red = 0x00, green = 0x00, blue = 0xff, alpha = 0xff)
        assertEquals(expected = "Color(0xff0000ff)", actual = color.toString())
    }

    @Test
    fun green_color_to_0xff00ff00() {
        val color = ImageVector.Path.FillColor(red = 0x00, green = 0xff, blue = 0x00, alpha = 0xff)
        assertEquals(expected = "Color(0xff00ff00)", actual = color.toString())
    }
}
