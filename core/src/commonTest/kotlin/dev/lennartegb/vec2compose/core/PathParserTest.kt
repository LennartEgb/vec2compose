package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.CurveTo
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PathParserTest {

    @Test
    fun `parse path to list of commands without closing end`() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2"

        assertEquals(
            actual = parsePath(pathSample),
            expected = listOf(
                MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                CurveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f, isAbsolute = true)
            )
        )
    }

    @Test
    fun `parse path to list of commands with closing end`() {
        val pathSample = "M27.05,24.55L12.15,19.65C11.85,19.35 11.483,19.2 11.05,19.2Z"

        assertEquals(
            actual = parsePath(pathSample),
            expected = listOf(
                MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                CurveTo(11.85f, 19.35f, 11.483f, 19.2f, 11.05f, 19.2f, isAbsolute = true),
                Close
            )
        )
    }
}
