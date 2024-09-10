package dev.lennartegb.vec2compose.core

import kotlin.test.Test
import kotlin.test.assertEquals

internal class PathParserTest {

    @Test
    fun parse_path_to_list_of_commands_without_closing_end() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2"

        assertEquals(
            actual = parsePath(pathSample),
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                Command.CurveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f, isAbsolute = true)
            )
        )
    }

    @Test
    fun parse_path_to_list_of_commands_with_closing_end() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2Z"

        assertEquals(
            actual = parsePath(pathSample),
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                Command.CurveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f, isAbsolute = true),
                Command.Close
            )
        )
    }
}
