package vectordrawable

import com.fasterxml.jackson.databind.ObjectMapper

internal class VectorDrawableDeserializer(private val mapper: ObjectMapper) {
  fun deserialize(content: String): Result<VectorDrawable> {
    return runCatching { mapper.readValue(content, VectorDrawable::class.java) }
  }
}
