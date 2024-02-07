package vectordrawable

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.commands.CommandParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import kotlin.test.Test
import kotlin.test.assertEquals

internal class VectorDrawableParserTest {

    private val parser = VectorDrawableParser(
        deserializer = VectorDrawableDeserializer(),
        pathParser = PathParser(CommandParser()),
        colorParser = HexColorParser()
    )

    @Test
    fun parse_valid_VectorDrawable_xml_to_VectorSet() {
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
    fun parse_valid_VectorDrawable_with_fillType_evenOdd_to_VectorSet() {
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
    fun parse_drawable_with_group() {
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
            <vector xmlns:android="http://schemas.android.com/apk/res/android"
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
