package dev.lennartegb.vec2compose.core.commands

import kotlin.test.Test
import kotlin.test.assertEquals

class CloseTest {
    @Test
    fun toString_returns_close() {
        assertEquals(expected = "close()", actual = Close.toString())
    }

    @Test
    fun invoke_returns_close_command() {
        assertEquals(expected = Command("Z"), actual = Close())
    }

    @Test
    fun name_returns_close() {
        assertEquals(expected = "close", actual = Close.name)
    }

    @Test
    fun argsCount_returns_0() {
        assertEquals(expected = 0, actual = Close.argsCount)
    }

    @Test
    fun getArguments_returns_empty_string() {
        assertEquals(expected = "", actual = Close.getArguments(listOf()))
    }
}
