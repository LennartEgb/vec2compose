package dev.lennartegb.vec2compose.vectorDrawable

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
                nodes = listOf(
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
                nodes = listOf(
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
            actual = result.nodes
        )
    }

    @Test
    fun parse_path_with_stroke() {
        val vector = vector(
            """
             <path
             android:pathData=""
             android:strokeWidth="42"
             android:strokeLineCap="butt"
             android:strokeLineJoin="bevel"
             android:strokeColor="#fff"
             android:strokeAlpha=".5"
             android:strokeMiterLimit="2"
             />
        """.trimIndent()
        )
        val result = parser.parse(vector).getOrThrow()
        assertEquals(
            actual = result,
            expected = VectorSet(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    VectorSet.Path(
                        commands = emptyList(),
                        alpha = 1f,
                        fillType = VectorSet.Path.FillType.Default,
                        stroke = VectorSet.Path.Stroke(
                            color = VectorSet.Path.FillColor(0xff, 0xff, 0xff, 0xff),
                            alpha = .5f,
                            width = 42f,
                            cap = VectorSet.Path.Stroke.Cap.Butt,
                            join = VectorSet.Path.Stroke.Join.Bevel,
                            miter = 2f,
                        ),
                    )
                ),
            )
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
        nodes: List<VectorSet.Node> = emptyList(),
    ): VectorSet = VectorSet(
        width = 24,
        height = 24,
        viewportWidth = 24f,
        viewportHeight = 24f,
        nodes = nodes,
    )
}
