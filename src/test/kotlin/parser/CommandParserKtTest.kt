package parser

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class CommandParserKtTest {

    @Test
    fun `parse Move command`() {
        val movePath = "M27.05,24.55"
        assertEquals(
            expected = Command.MoveTo(x = 27.05f, y = 24.55f),
            actual = parseCommand(movePath),
        )
    }

    @Test
    fun `parse LineTo path`() {
        val linePath = "L12.15,19.65"
        assertEquals(
            expected = Command.LineTo(x = 12.15f, y = 19.65f),
            actual = parseCommand(linePath)
        )
    }

    @Test
    fun `parse CurveTo path`() {
        val curveToPath = "C11.85,19.35 11.483,19.2 11.05,19.2"
        assertEquals(
            expected = Command.CurveTo(x1 = 11.85f, y1 = 19.35f, x2 = 11.483f, y2 = 19.2f, x3 = 11.05f, y3 = 19.2f),
            actual = parseCommand(curveToPath)
        )
    }

    @Test
    fun `parse Close path`() {
        val closePath = "Z"
        assertEquals(
            expected = Command.Close,
            actual = parseCommand(closePath)
        )
    }

    @Test
    fun `parse HorizontalLineTo path`() {
        val horizontalLineToPath = "H20.05"
        assertEquals(
            expected = Command.HorizontalLineTo(x = 20.05f),
            actual = parseCommand(horizontalLineToPath)
        )
    }

    @Test
    fun `parse VerticalLineTo path`() {
        val horizontalLineToPath = "V20.05"
        assertEquals(
            expected = Command.VerticalLineTo(y = 20.05f),
            actual = parseCommand(horizontalLineToPath)
        )
    }
}
