package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.Command
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PathParserTest {

    @Test
    fun parse_path_to_list_of_commands_without_closing_end() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2"

        assertEquals(
            actual = Path(pathSample),
            expected = listOf(
                Command("M27.05,24.55"),
                Command("L12.15,19.65"),
                Command("C11.85,19.35 11.483,19.2 11.05,19.2")
            )
        )
    }

    @Test
    fun parse_path_to_list_of_commands_with_closing_end() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2Z"

        assertEquals(
            actual = Path(pathSample),
            expected = listOf(
                Command("M27.05,24.55"),
                Command("L12.15,19.65"),
                Command("C11.85,19.35 11.483,19.2 11.05,19.2"),
                Command("Z")
            )
        )
    }
}
