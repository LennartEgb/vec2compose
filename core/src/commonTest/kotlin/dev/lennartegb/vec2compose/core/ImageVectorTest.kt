package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.ImageVector.Path.FillType
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageVectorTest {
    @Test
    fun create_default_Stroke_for_null_values() {
        val stroke = ImageVector.Path.Stroke(
            color = null,
            alpha = null,
            width = null,
            cap = null,
            join = null,
            miter = null
        )
        assertEquals(expected = ImageVector.Path.Stroke(), actual = stroke)
    }

    @Test
    fun default_FillType_is_NonZero() {
        assertEquals(
            actual = FillType.Default,
            expected = FillType.NonZero
        )
    }

    @Test
    fun invoke_FillType_evenodd_returns_FillType_EvenOdd() {
        assertEquals(actual = FillType("evenodd"), expected = FillType.EvenOdd)
    }

    @Test
    fun invoke_FillType_NonZero_returns_FillType_NonZero() {
        assertEquals(actual = FillType("NONZERO"), expected = FillType.NonZero)
    }

    @Test
    fun invoke_FillType_RANDOM_returns_FillType_Default() {
        assertEquals(actual = FillType("r4ndøm"), expected = FillType.Default)
    }
}
