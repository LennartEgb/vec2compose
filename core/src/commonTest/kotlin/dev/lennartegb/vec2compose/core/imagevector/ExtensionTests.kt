package dev.lennartegb.vec2compose.core.imagevector

import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Cap
import dev.lennartegb.vec2compose.core.ImageVector.Path.Stroke.Join
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtensionTests {
    @Test
    fun join_property_Bevel_returns_StrokeJoin_Bevel() {
        assertEquals(expected = "StrokeJoin.Bevel", actual = Join.Bevel.property())
    }

    @Test
    fun join_property_Miter_returns_StrokeJoin_Miter() {
        assertEquals(expected = "StrokeJoin.Miter", actual = Join.Miter.property())
    }

    @Test
    fun join_property_Round_returns_StrokeJoin_Round() {
        assertEquals(expected = "StrokeJoin.Round", actual = Join.Round.property())
    }

    @Test
    fun cap_property_Butt_returns_StrokeJoin_Butt() {
        assertEquals(expected = "StrokeCap.Butt", actual = Cap.Butt.property())
    }

    @Test
    fun cap_property_Square_returns_StrokeJoin_Square() {
        assertEquals(expected = "StrokeCap.Square", actual = Cap.Square.property())
    }

    @Test
    fun cap_property_Round_returns_StrokeJoin_Round() {
        assertEquals(expected = "StrokeCap.Round", actual = Cap.Round.property())
    }

    @Test
    fun fillColor_null_solid_returns_null() {
        val color: ImageVector.Path.FillColor? = null
        assertEquals(expected = "null", actual = color?.solid().toString())
    }

    @Test
    fun fillColor_red_solid_returns_SolidColor() {
        val color = ImageVector.Path.FillColor(red = 0xff, green = 0x00, blue = 0x00, alpha = 0xff)
        assertEquals(expected = "SolidColor(Color(0xffff0000))", actual = color.solid())
    }
}
