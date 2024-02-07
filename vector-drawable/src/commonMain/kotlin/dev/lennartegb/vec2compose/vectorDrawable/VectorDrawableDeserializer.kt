package dev.lennartegb.vec2compose.vectorDrawable

import nl.adaptivity.xmlutil.serialization.XML

internal class VectorDrawableDeserializer {

    fun deserialize(content: String): Result<VectorDrawable> {
        return runCatching { XML.decodeFromString<VectorDrawable>(content) }
    }
}
