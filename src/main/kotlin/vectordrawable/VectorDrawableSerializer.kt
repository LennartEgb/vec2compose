package vectordrawable

import com.fasterxml.jackson.databind.ObjectMapper

internal class VectorDrawableSerializer(private val mapper: ObjectMapper) {
    fun serialize(xml: XML): Result<VectorDrawable> {
        return runCatching { mapper.readValue(xml.content, VectorDrawable::class.java) }
    }
}