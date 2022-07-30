package parser

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


internal class PathParserTest {

    @Test
    fun `parse path to list of commands without closing end`() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2"

        assertEquals(
            actual = parsePath(pathSample),
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f),
                Command.LineTo(12.15f, 19.65f),
                Command.CurveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f)
            )
        )
    }

    @Test
    fun `parse path to list of commands with closing end`() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2Z"

        assertEquals(
            actual = parsePath(pathSample),
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f),
                Command.LineTo(12.15f, 19.65f),
                Command.CurveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f),
                Command.Close,
            )
        )
    }
}