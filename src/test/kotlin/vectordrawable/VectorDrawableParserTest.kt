package vectordrawable

import commands.Command
import commands.CommandParser
import commands.PathParser
import VectorSet
import org.junit.jupiter.api.Test
import utils.TestObjectMapper
import kotlin.test.assertEquals

internal class VectorDrawableParserTest {

    private val parser = VectorDrawableParser(
        serializer = VectorDrawableDeserializer(TestObjectMapper),
        pathParser = PathParser(CommandParser())
    )

    @Test
    fun `parse valid VectorDrawable xml to VectorSet`() {
        val vector = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:viewportWidth="24"
            android:viewportHeight="24"
            android:tint="?attr/colorControlNormal">
          <path
              android:fillColor="@android:color/white"
              android:pathData="M11.99,2C6.47,2 2,6.48 2,12s4.47,10 9.99,10C17.52,22 22,17.52 22,12S17.52,2 11.99,2z"/>
        </vector>
        """.trimIndent()
        assertEquals(
            expected = VectorSet(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                paths = listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.NonZero,
                        commands = listOf(
                            Command.MoveTo(x = 11.99f, y = 2f, isAbsolute = true),
                            Command.CurveTo(6.47f, 2f, 2f, 6.48f, 2f, 12f, isAbsolute = true),
                            Command.ReflectiveCurveTo(4.47f, 10f, 9.99f, 10f, isAbsolute = false),
                            Command.CurveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f, isAbsolute = true),
                            Command.ReflectiveCurveTo(17.52f, 2f, 11.99f, 2f, isAbsolute = true),
                            Command.Close
                        ),
                    ),
                ),
                groups = emptyList(),
            ),
            actual = parser.parse(vector).getOrThrow(),
        )
    }

    @Test
    fun `parse valid VectorDrawable with fillType evenOdd to VectorSet`() {
        val vector = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:viewportWidth="24"
            android:viewportHeight="24"
            android:tint="?attr/colorControlNormal">
          <path
              android:fillType="evenOdd"
              android:fillColor="@android:color/white"
              android:pathData="M11.99,2C6.47,2 2,6.48 2,12s4.47,10 9.99,10C17.52,22 22,17.52 22,12S17.52,2 11.99,2z"/>
        </vector>
        """.trimIndent()
        assertEquals(
            expected = VectorSet(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                paths = listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.EvenOdd,
                        commands = listOf(
                            Command.MoveTo(x = 11.99f, y = 2f, isAbsolute = true),
                            Command.CurveTo(6.47f, 2f, 2f, 6.48f, 2f, 12f, isAbsolute = true),
                            Command.ReflectiveCurveTo(4.47f, 10f, 9.99f, 10f, isAbsolute = false),
                            Command.CurveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f, isAbsolute = true),
                            Command.ReflectiveCurveTo(17.52f, 2f, 11.99f, 2f, isAbsolute = true),
                            Command.Close
                        ),
                        fillColor = VectorSet.Path.FillColor(red = 0xFF, green = 0xFF, blue = 0xFF, alpha = 0xFF)
                    )
                ),
                groups = emptyList(),
            ),
            actual = parser.parse(vector).getOrThrow(),
        )
    }
}