package vectordrawable

import com.fasterxml.jackson.databind.ObjectMapper

internal class VectorDrawableSerializer(private val mapper: ObjectMapper) {
    fun serialize(content: String): Result<VectorDrawable> {
        return runCatching { mapper.readValue(content, VectorDrawable::class.java) }
    }
}