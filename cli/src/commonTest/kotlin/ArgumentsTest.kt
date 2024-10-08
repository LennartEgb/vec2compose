import kotlin.test.Test
import kotlin.test.assertEquals

class ArgumentsTest {

    @Test
    fun `get input with short name`() {
        val arguments = Arguments(arrayOf("-i", "test.svg"))
        assertEquals(expected = "test.svg", actual = arguments.input)
    }

    @Test
    fun `get input with full name`() {
        val arguments = Arguments(arrayOf("--input", "test.svg"))
        assertEquals(expected = "test.svg", actual = arguments.input)
    }

    @Test
    fun `get output with short name`() {
        val arguments = Arguments(arrayOf("-i", "input.svg", "-o", "Output.kt"))
        assertEquals(expected = "Output.kt", actual = arguments.output)
    }

    @Test
    fun `get output with full name`() {
        val arguments = Arguments(arrayOf("-i", "input.svg", "--output", "Output.kt"))
        assertEquals(expected = "Output.kt", actual = arguments.output)
    }

    @Test
    fun `get package name with short name`() {
        val arguments = Arguments(arrayOf("-i", "input.svg", "-p", "com.example.icons"))
        assertEquals(expected = "com.example.icons", actual = arguments.packageName)
    }

    @Test
    fun `get package name with full name`() {
        val arguments = Arguments(arrayOf("-i", "input.svg", "--package", "com.example.icons"))
        assertEquals(expected = "com.example.icons", actual = arguments.packageName)
    }
}
