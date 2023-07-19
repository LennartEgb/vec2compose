package output

import kotlin.test.Test
import kotlin.test.assertEquals

internal class NameFormatterTest {

    private val formatter = NameFormatter

    @Test
    fun ic_close_XML_file_is_parsed_to_IcClose() {
        assertEquals(expected = "IcClose", actual = formatter.format("ic_close.xml"))
    }

    @Test
    fun account_circle_SVG_file_is_parsed_to_AccountCircle() {
        assertEquals(expected = "AccountCircle", actual = formatter.format("account-circle.svg"))
    }

    @Test
    fun where_To_Vote_XML_file_is_parsed_to_WhereToVote() {
        assertEquals(expected = "WhereToVote", actual = formatter.format("Where To Vote.xml"))
    }

    @Test
    fun where_ic_search_24_is_parsed_to_IcSearch24() {
        assertEquals(expected = "IcSearch24", actual = formatter.format("ic_search_24.xml"))
    }
}
