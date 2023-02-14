internal class ColorDeserializer {
    fun deserialize(color: String): VectorSet.Path.FillColor {
        if (color.startsWith("#")) {
            return deserialize(color.drop(1))
        }

        if (color.length == 3) {
            return deserialize(color.fold(initial = "") { acc, char -> "$acc$char$char" })
        }

        if (color.length == 6) {
            return deserialize("FF$color")
        }

        // FFAABBCC
        require(color.length == 8)
        val (a, r, g, b) = color.windowed(size = 2, step = 2)
        return VectorSet.Path.FillColor(
            red = r.toInt(16),
            green = g.toInt(16),
            blue = b.toInt(16),
            alpha = a.toInt(16),
        )
    }
}