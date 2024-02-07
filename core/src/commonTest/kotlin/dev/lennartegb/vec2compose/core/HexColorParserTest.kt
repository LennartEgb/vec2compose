package dev.lennartegb.vec2compose.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HexColorParserTest {

    private val parser = HexColorParser()

    @Test
    fun whiteColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF),
            actual = parser.parse(color = "#fff")
        )
    }

    @Test
    fun redColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0xFF),
            actual = parser.parse(color = "#F00")
        )
    }

    @Test
    fun greenColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0xFF, blue = 0x00, alpha = 0xFF),
            actual = parser.parse(color = "#0F0")
        )
    }

    @Test
    fun blueColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0xFF),
            actual = parser.parse(color = "#00F")
        )
    }
    @Test
    fun parseSixCharHexWhiteColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF),
            actual = parser.parse(color = "#FFFFFF")
        )
    }

    @Test
    fun parseSixCharHexRedColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0xFF),
            actual = parser.parse(color = "#FF0000")
        )
    }

    @Test
    fun parseSixCharHexGreenColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0xFF, blue = 0x00, alpha = 0xFF),
            actual = parser.parse(color = "#00FF00")
        )
    }

    @Test
    fun parseSixCharHexBlueColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0xFF),
            actual = parser.parse(color = "#0000FF")
        )
    }

    @Test
    fun parseEightCharHexWhiteColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF),
            actual = parser.parse(color = "#FFFFFFFF")
        )
    }

    @Test
    fun parseEightCharHexRedColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0xFF),
            actual = parser.parse(color = "#FFFF0000")
        )
    }

    @Test
    fun parseEightCharHexGreenColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0xFF, blue = 0x00, alpha = 0xFF),
            actual = parser.parse(color = "#FF00FF00")
        )
    }

    @Test
    fun parseEightCharHexBlueColorToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0xFF),
            actual = parser.parse(color = "#FF0000FF")
        )
    }

    @Test
    fun parseEightCharHexBlackColorWithAlphaToFillColor() {
        assertEquals(
            expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0x33),
            actual = parser.parse(color = "#330000FF")
        )
    }

    @Test
    fun resourceNameReturnsNull() {
        assertNull(actual = parser.parse(color = "R.color.white"))
    }
}
