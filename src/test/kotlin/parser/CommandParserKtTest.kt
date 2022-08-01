package parser

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class CommandParserKtTest {

    private val commandParser = CommandParser()

    @Test
    fun `parse Move command`() {
        val movePath = "M27.05,24.55"
        assertEquals(
            expected = listOf(Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true)),
            actual = commandParser.parse(movePath),
        )
    }

    @Test
    fun `parse multiple Move command`() {
        val movePath = "M27.05,24.55 27.05,24.55"
        assertEquals(
            expected = listOf(
                Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true),
                Command.MoveTo(x = 27.05f, y = 24.55f, isAbsolute = true)
            ),
            actual = commandParser.parse(movePath),
        )
    }

    @Test
    fun `parse LineTo path`() {
        val linePath = "L12.15,19.65"
        assertEquals(
            expected = listOf(Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true)),
            actual = commandParser.parse(linePath)
        )
    }

    @Test
    fun `parse multiple LineTo path`() {
        val linePath = "L12.15,19.65 12.15,19.65"
        assertEquals(
            expected = listOf(
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true),
                Command.LineTo(x = 12.15f, y = 19.65f, isAbsolute = true)
            ),
            actual = commandParser.parse(linePath)
        )
    }

    @Test
    fun `parse CurveTo path`() {
        val curveToPath = "C11.85,19.35 11.483,19.2 11.05,19.2"
        assertEquals(
            expected = listOf(
                Command.CurveTo(
                    x1 = 11.85f,
                    y1 = 19.35f,
                    x2 = 11.483f,
                    y2 = 19.2f,
                    x3 = 11.05f,
                    y3 = 19.2f,
                    isAbsolute = true
                )
            ),
            actual = commandParser.parse(curveToPath)
        )
    }

    @Test
    fun `parse Close path`() {
        val closePath = "Z"
        assertEquals(
            expected = listOf(Command.Close(isAbsolute = true)),
            actual = commandParser.parse(closePath)
        )
    }

    @Test
    fun `parse Close path relative with lowercase z`() {
        val closePath = "z"
        assertEquals(
            expected = listOf(Command.Close(isAbsolute = false)),
            actual = commandParser.parse(closePath)
        )
    }

    @Test
    fun `parse HorizontalLineTo path`() {
        val horizontalLineToPath = "H20.05"
        assertEquals(
            expected = listOf(Command.HorizontalLineTo(x = 20.05f, isAbsolute = true)),
            actual = commandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun `parse multiple HorizontalLineTo path`() {
        val horizontalLineToPath = "H20.05 20.05"
        assertEquals(
            expected = listOf(
                Command.HorizontalLineTo(x = 20.05f, isAbsolute = true),
                Command.HorizontalLineTo(x = 20.05f, isAbsolute = true),
            ),
            actual = commandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun `parse HorizontalLineTo path with relative command`() {
        val horizontalLineToPath = "h20.05"
        assertEquals(
            expected = listOf(Command.HorizontalLineTo(x = 20.05f, isAbsolute = false)),
            actual = commandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun `parse VerticalLineTo path`() {
        val horizontalLineToPath = "V20.05"
        assertEquals(
            expected = listOf(Command.VerticalLineTo(y = 20.05f, isAbsolute = true)),
            actual = commandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun `parse multiple VerticalLineTo path`() {
        val horizontalLineToPath = "V20.05 20.05"
        assertEquals(
            expected = listOf(
                Command.VerticalLineTo(y = 20.05f, isAbsolute = true),
                Command.VerticalLineTo(y = 20.05f, isAbsolute = true),
            ),
            actual = commandParser.parse(horizontalLineToPath)
        )
    }

    @Test
    fun `parse ReflectiveCurveTo path`() {
        val horizontalLineToPath = "s4.47,10 9.99,10"
        assertEquals(
            expected = listOf(
                Command.ReflectiveCurveTo(
                    x1 = 4.47f,
                    y1 = 10f,
                    x2 = 9.99f,
                    y2 = 10f,
                    isAbsolute = false
                )
            ),
            actual = commandParser.parse(horizontalLineToPath)
        )
    }
}
