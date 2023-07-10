package vectordrawable

import com.fasterxml.jackson.databind.ObjectMapper
import nl.adaptivity.xmlutil.serialization.XML

internal class VectorDrawableDeserializer(private val mapper: ObjectMapper) {

    fun deserialize(content: String): Result<VectorDrawable> {
        return runCatching { XML.decodeFromString<VectorDrawable>(content) }
    }
}
