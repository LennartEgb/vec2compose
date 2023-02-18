import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HexColorParserTest {

    private val parser = HexColorParser()

    @Nested
    inner class ParseThreeCharHex {
        @Test
        fun `white color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF),
                actual = parser.parse(color = "#fff")
            )
        }

        @Test
        fun `red color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0xFF),
                actual = parser.parse(color = "#F00")
            )
        }

        @Test
        fun `green color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0xFF, blue = 0x00, alpha = 0xFF),
                actual = parser.parse(color = "#0F0")
            )
        }

        @Test
        fun `blue color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0xFF),
                actual = parser.parse(color = "#00F")
            )
        }
    }

    @Nested
    inner class ParseSixCharHex {
        @Test
        fun `white color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF),
                actual = parser.parse(color = "#FFFFFF")
            )
        }

        @Test
        fun `red color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0xFF),
                actual = parser.parse(color = "#FF0000")
            )
        }

        @Test
        fun `green color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0xFF, blue = 0x00, alpha = 0xFF),
                actual = parser.parse(color = "#00FF00")
            )
        }

        @Test
        fun `blue color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0xFF),
                actual = parser.parse(color = "#0000FF")
            )
        }
    }

    @Nested
    inner class ParseEightCharHex {
        @Test
        fun `white color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF),
                actual = parser.parse(color = "#FFFFFFFF")
            )
        }

        @Test
        fun `red color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0xFF),
                actual = parser.parse(color = "#FFFF0000")
            )
        }

        @Test
        fun `green color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0xFF, blue = 0x00, alpha = 0xFF),
                actual = parser.parse(color = "#FF00FF00")
            )
        }

        @Test
        fun `blue color to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0xFF),
                actual = parser.parse(color = "#FF0000FF")
            )
        }

        @Test
        fun `black color with alpha to FillColor`() {
            assertEquals(
                expected = VectorSet.Path.FillColor(red = 0x00, green = 0x00, blue = 0xFF, alpha = 0x33),
                actual = parser.parse(color = "#330000FF")
            )
        }
    }

    @Nested
    inner class ParseNoHex {
        @Test
        fun `returns no color`() {
            assertNull(actual = parser.parse(color = "R.color.white"))
        }
    }
}
