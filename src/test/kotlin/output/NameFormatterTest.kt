package output

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NameFormatterTest {
    private val formatter = NameFormatter()

    @Test
    fun `ic_close XML file is parsed to IcClose`() {
        assertEquals(expected = "IcClose", actual = formatter.format("ic_close.xml"))
    }

    @Test
    fun `account-circle SVG file is parsed to AccountCircle`() {
        assertEquals(expected = "AccountCircle", actual = formatter.format("account-circle.svg"))
    }

    @Test
    fun `Where To Vote XML file is parsed to WhereToVote`() {
        assertEquals(expected = "WhereToVote", actual = formatter.format("Where To Vote.xml"))
    }
}