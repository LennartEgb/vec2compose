package dev.lennartegb.vec2compose.core

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
            actual = ImageVector.Path.FillType.Default,
            expected = ImageVector.Path.FillType.NonZero
        )
    }
}
