package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.Scale
import dev.lennartegb.vec2compose.core.Translation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class VectorDrawableParserTest {

    @Test
    fun parse_valid_VectorDrawable_xml_to_ImageVector() {
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
    fun parse_valid_VectorDrawable_with_fillType_evenOdd_to_ImageVector() {
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

    @Test
    fun parse_int_width_vector() {
        assertEquals(
            actual = xmlImageVectorParser().parse(vector("", width = "24")).getOrThrow(),
            expected = ImageVector(
                width = 24,
                height = 24,
                viewportWidth = 24f,
                viewportHeight = 24f,
                nodes = emptyList()
            )
        )
    }

    @Test
    fun parse_invalid_width_vector_throws_IllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            xmlImageVectorParser().parse(vector("", width = "r4nd0m")).getOrThrow()
        }
    }

    private fun vector(
        content: String,
        width: String = "24dp",
        height: String = "24dp"
    ): String = buildString {
        appendLine(
            """
            <vector xmlns:android="http://schemas.android.com/apk/res/android"
                    android:width="$width"
                    android:height="$height"
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
