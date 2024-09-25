package dev.lennartegb.vec2compose.vectorDrawable

import dev.lennartegb.vec2compose.core.ImageVector
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class VectorDrawableColorParserTest {
    @Test
    fun android_color_black_returns_black_FillColor() {
        assertEquals(
            actual = VectorDrawableColorParser().parse("@android:color/black"),
            expected = ImageVector.Path.FillColor(0x00, 0x00, 0x00, 0xff)
        )
    }

    @Test
    fun unsupported_android_color_returns_null() {
        assertNull(VectorDrawableColorParser().parse("@android:color/a_random_color"))
    }

    @Test
    fun hex_color_black_returns_black_FillColor() {
        assertEquals(
            actual = VectorDrawableColorParser().parse("#000000"),
            expected = ImageVector.Path.FillColor(0x00, 0x00, 0x00, 0xff)
        )
    }

    @Test
    fun invalid_color_name_returns_null() {
        assertNull(actual = VectorDrawableColorParser().parse("some weird color"))
    }
}
