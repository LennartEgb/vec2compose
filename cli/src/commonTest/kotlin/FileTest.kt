import file.File
import kotlin.test.Test
import kotlin.test.assertEquals

class FileTest {

    @Test
    fun extension_extracted_from_name() {
        assertEquals(expected = "svg", actual = File(name = "test.svg", content = "Hello").extension)
    }
}
