package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.ImageVector.Path.FillType
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ImageVectorTest {
    @Test
    fun create_default_Stroke_for_null_values() {
        val stroke = Stroke(
            color = null,
            alpha = null,
            width = null,
            cap = null,
            join = null,
            miter = null
        )
        assertEquals(expected = Stroke(), actual = stroke)
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

    @Test
    fun invoke_Join_bevel_returns_Join_Bevel() {
        assertEquals(actual = Stroke.Join("bevel"), expected = Stroke.Join.Bevel)
    }

    @Test
    fun invoke_Join_miter_returns_Join_Miter() {
        assertEquals(actual = Stroke.Join("miter"), expected = Stroke.Join.Miter)
    }

    @Test
    fun invoke_Join_round_returns_Join_Round() {
        assertEquals(actual = Stroke.Join("round"), expected = Stroke.Join.Round)
    }

    @Test
    fun invoke_Join_random_throws_IllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> { Stroke.Join("r4ndøm") }
    }
}
