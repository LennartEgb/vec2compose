package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import kotlin.test.Test
import kotlin.test.assertEquals

internal class VectorDrawableParserTest {

    @Test
    fun `parse valid VectorDrawable xml to ImageVector`() {
        val vector = vector(
            """
                <path
                    android:fillColor="@android:color/white"
                    android:alpha="0.5"
                    android:pathData=""/>
            """.trimIndent()
        )
        assertEquals(
            expected = imageVector(
                nodes = listOf(
                    ImageVector.Path(
                        fillType = ImageVector.Path.FillType.NonZero,
                        fillColor = ImageVector.Path.FillColor(0xff, 0xff, 0xff, 0xff),
                        commands = emptyList(),
                        alpha = .5f
                    )
                )
            ),
            actual = xmlImageVectorParser().parse(vector).getOrThrow()
        )
    }

    @Test
    fun `parse valid VectorDrawable with fillType evenOdd to ImageVector`() {
        val v = vector(
            """
            <path
             android:fillType="evenOdd"
             android:fillColor="@android:color/white"
             android:pathData=""/>
            """.trimIndent()
        )
        assertEquals(
            expected = imageVector(
                nodes = listOf(
                    ImageVector.Path(
                        fillType = ImageVector.Path.FillType.EvenOdd,
                        fillColor = ImageVector.Path.FillColor(0xff, 0xff, 0xff, 0xff),
                        commands = emptyList(),
                        alpha = 1f
                    )
                )
            ),
            actual = xmlImageVectorParser().parse(v).getOrThrow()
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
        val result = xmlImageVectorParser().parse(vector).getOrThrow()
        assertEquals(
            expected = listOf(
                ImageVector.Group(
                    name = "hi",
                    nodes = emptyList(),
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
    fun `parse path with stroke`() {
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
        val result = xmlImageVectorParser().parse(vector).getOrThrow()
        assertEquals(
            actual = result,
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = listOf(
                    ImageVector.Path(
                        commands = emptyList(),
                        alpha = 1f,
                        fillType = ImageVector.Path.FillType.Default,
                        stroke = ImageVector.Path.Stroke(
                            color = ImageVector.Path.FillColor(0xff, 0xff, 0xff, 0xff),
                            alpha = .5f,
                            width = 42f,
                            cap = ImageVector.Path.Stroke.Cap.Butt,
                            join = ImageVector.Path.Stroke.Join.Bevel,
                            miter = 2f
                        )
                    )
                )
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

    private fun imageVector(nodes: List<ImageVector.Node> = emptyList()): ImageVector = ImageVector(
        width = 24,
        height = 24,
        viewportWidth = 24f,
        viewportHeight = 24f,
        nodes = nodes
    )
}
