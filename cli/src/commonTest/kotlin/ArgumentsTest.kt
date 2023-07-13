import kotlin.test.Test
import kotlin.test.assertEquals

class ArgumentsTest {

    @Test
    fun getInput() {
        val arguments = Arguments(arrayOf("-i", "input.svg"))
        assertEquals(expected = "input.svg", actual = arguments.input)
    }

    @Test
    fun getOutput() {
        val arguments = Arguments(arrayOf("-i", "input.svg", "-o", "Output.kt"))
        assertEquals(expected = "Output.kt", actual = arguments.output)
    }
}
