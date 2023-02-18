package vectordrawable

import HexColorParser
import Scale
import Translation
import VectorSet
import commands.CommandParser
import commands.PathParser
import org.junit.jupiter.api.Test
import utils.TestObjectMapper
import kotlin.test.assertEquals

internal class VectorDrawableParserTest {

    private val parser = VectorDrawableParser(
        deserializer = VectorDrawableDeserializer(TestObjectMapper),
        pathParser = PathParser(CommandParser()),
        colorParser = HexColorParser()
    )

    @Test
    fun `parse valid VectorDrawable xml to VectorSet`() {
        val vector = vector(
            """
                <path
                    android:fillColor="@android:color/white"
                    android:alpha="0.5"
                    android:pathData=""/>
            """.trimIndent()
        )
        assertEquals(
            expected = vectorSet(
                paths = listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.NonZero,
                        commands = emptyList(),
                        alpha = .5f
                    )
                )
            ),
            actual = parser.parse(vector).getOrThrow()
        )
    }

    @Test
    fun `parse valid VectorDrawable with fillType evenOdd to VectorSet`() {
        val v = vector(
            """
            <path
             android:fillType="evenOdd"
             android:fillColor="@android:color/white"
             android:pathData=""/>
            """.trimIndent()
        )
        assertEquals(
            expected = vectorSet(
                paths = listOf(
                    VectorSet.Path(
                        fillType = VectorSet.Path.FillType.EvenOdd,
                        commands = emptyList(),
                        alpha = 1f
                    )
                )
            ),
            actual = parser.parse(v).getOrThrow()
        )
    }

    @Test
    fun `parse drawable with group`() {
        val vector = vector(
            """
                <group 
                    android:name="hi"
                    android:pivotX="10.0"
                    android:pivotY="11.0"
                    android:rotation="15.0"
                    android:translateX="20.0"
                    android:translateY="21.0"
                    android:scaleX="30.0"
                    android:scaleY="31.0"
                />
            """.trimIndent()
        )
        val result = parser.parse(vector).getOrThrow()
        assertEquals(
            expected = listOf(
                VectorSet.Group(
                    name = "hi",
                    groups = emptyList(),
                    paths = emptyList(),
                    rotate = 15f,
                    pivot = Translation(10f, 11f),
                    translation = Translation(20f, 21f),
                    scale = Scale(30f, 31f)
                )
            ),
            actual = result.groups
        )
    }

    private fun vector(content: String): String = buildString {
        appendLine(
            """
            <vector xmlns:android="https://schemas.android.com/apk/res/android"
                    android:width="24dp"
                    android:height="24dp"
                    android:viewportWidth="24"
                    android:viewportHeight="24">
            """.trimIndent()
        )
        appendLine(content)
        appendLine("</vector>")
    }

    private fun vectorSet(
        paths: List<VectorSet.Path> = emptyList(),
        groups: List<VectorSet.Group> = emptyList()
    ): VectorSet = VectorSet(
        width = 24,
        height = 24,
        viewportWidth = 24f,
        viewportHeight = 24f,
        paths = paths,
        groups = groups
    )
}
